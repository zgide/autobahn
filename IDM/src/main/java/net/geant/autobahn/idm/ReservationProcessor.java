package net.geant.autobahn.idm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.geant.autobahn.administration.Translator;
import net.geant.autobahn.constraints.GlobalConstraints;
import net.geant.autobahn.dao.hibernate.HibernateIdmDAOFactory;
import net.geant.autobahn.dao.hibernate.HibernateUtil;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.interdomain.Interdomain;
import net.geant.autobahn.interdomain.InterdomainClient;
import net.geant.autobahn.interdomain.NoSuchReservationException;
import net.geant.autobahn.reservation.AutobahnReservation;
import net.geant.autobahn.reservation.ExternalReservation;
import net.geant.autobahn.reservation.LastDomainReservation;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.ReservationStatusListener;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.dao.ReservationHistoryDAO;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;


public class ReservationProcessor {

	private static final Logger log = Logger.getLogger(ReservationProcessor.class);
	
	private Map<String, BlockingQueue<AutobahnCommand>> events = new HashMap<String, BlockingQueue<AutobahnCommand>>();
	private Map<String, AutobahnReservation> reservations = new HashMap<String, AutobahnReservation>();
	private Map<String, String> previousDomains = new HashMap<String, String>();
	
	private ReservationDAO rdao = null;
	private ReservationHistoryDAO hdao = null;
	private TopologyMerge topologyMerge = new TopologyMerge();
	
	private Idm2Dm domainManager = null;
	private String domainID;
    private boolean restorationMode = false;
    
	private String timestamp;
	private String milliseconds;
	
	private static Object rsvRunnerLock = new Object();

	public ReservationProcessor(String domainID, Idm2Dm domainManager) {
		this.domainManager = domainManager;
		this.domainID = domainID;
		this.rdao = HibernateIdmDAOFactory.getInstance().getReservationDAO();
		this.hdao = HibernateIdmDAOFactory.getInstance().getReservationHistoryDAO();
	}
	
	public void scheduleReservation(Reservation src) {
		final String resID = src.getBodID();

		AutobahnReservation res = reservations.get(resID);
		if(res == null) {
			// When first time in an external domain
			res = AutobahnReservation.createReservation(src, domainID);
		} else {
			// Another attempt - another path
            res.setPath(src.getPath());
            res.setGlobalConstraints(src.getGlobalConstraints());
            res.gotoInitialState();
		}

		// Used to merge same objects - needed by Hibernate to work
		res = topologyMerge.merge(res);
		
		// Inject resources
		res.setResourcesReservation(domainManager);
		
		runReservation(res);
	}
	
	public void runReservation(final AutobahnReservation res) {

        AutobahnCommand command = new AutobahnCommand () {
            public void run() {
                rdao.update(res);
                res.run();

                hdao.update(Translator.convertHistory(res));
                
                // Delete fake reservation after processing
                if(res instanceof LastDomainReservation && res.isFake()) {
                	rdao.delete(res);
                }
            }

			@Override
			public String getInfo() {
				return "Run: " + res.getBodID();
			}
        };

        addReservation(res, command);
	}
	
	private void addReservation(AutobahnReservation res, AutobahnCommand command) {
		final String resID = res.getBodID();
		
		reservations.put(resID, res);

        BlockingQueue<AutobahnCommand> queue = new LinkedBlockingQueue<AutobahnCommand>();
        events.put(resID, queue);
        
        if(command != null)
        	queue.add(new TransactionTask(command));
        
        ReservationRunner runner = new ReservationRunner(resID);
        res.addStatusListener(runner);
        
        //ThreadExecutor.getInstance().execute(runner);
        new Thread(runner).start();
	}
	
	public void recoverReservation(final AutobahnReservation res) {

		// Inject resources
		res.setResourcesReservation(domainManager);
		res.setLocalDomainID(domainID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.recover();

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Recover: " + res.getBodID();
			}
        };
		
		addReservation(res, command);
	}
	
	public void reportSchedule(final String resID, final int msgCode,
			final String arguments, final boolean success,
			final GlobalConstraints global) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationScheduled(msgCode, arguments, success, global);

                hdao.update(Translator.convertHistory(res));
                
                // Delete if it's fake
                if(res.isFake())
                	rdao.delete(res);
            }

			@Override
			public String getInfo() {
				return "Report Schedule: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
    public void cancelReservation(final String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.cancel();

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Cancel: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
    }
	
	public void reportCancellation(final String resID, final String message,
			final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationCancelled(message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report cancel: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void modifyReservation(final String resID, final Calendar startTime,
			final Calendar endTime) {
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
                res.modify(startTime, endTime);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Modify: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void withdrawReservation(final String resID) throws NoSuchReservationException {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
                res.withdraw();
            
                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Withdraw: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportWithdraw(final String resID, final String message,
			final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
			public void run() {
				rdao.update(res);
				res.reservationWithdrawn(message, success);

				hdao.update(Translator.convertHistory(res));
			}

			@Override
			public String getInfo() {
				return "Report withdraw: " + resID;
			}
		};

		BlockingQueue<AutobahnCommand> queue = events.get(resID);
		queue.add(new TransactionTask(command));
	}

	public void reportModify(final String resID, final Calendar startTime,
			final Calendar endTime, final String message, final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);

		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationModified(startTime, endTime, message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report modify: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	public void reportActive(final String resID, final String message,
			final boolean success) throws NoSuchReservationException {
		
		final AutobahnReservation res = reservations.get(resID);

		if(res == null)
			throw new NoSuchReservationException(resID);
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
                res.reservationActivated(message, success);

                hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Report active: " + resID;
			}
        };

        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void reportFinished(final String resID, final String message,
			final boolean success) {
		
		final AutobahnReservation res = reservations.get(resID);
		
		if(res != null) {
			if (res instanceof ExternalReservation) {
				// Just forward the message
	        	Interdomain client;
                try {
                    client = new InterdomainClient(res.getPrevDomainAddress());
                    client.reportFinished(resID, message, success);
                } catch (Exception e) {
                    log.error(this + " reportFinished: " + e.getMessage(), e);
                }
					
				return;
			}
			
			AutobahnCommand command = new AutobahnCommand() {
	            public void run() {
	                rdao.update(res);
	                res.reservationFinished(message, success);

	                hdao.update(Translator.convertHistory(res));
	            }

				@Override
				public String getInfo() {
					return "Report finished: " + resID;
				}
	        };

	        BlockingQueue<AutobahnCommand> queue = events.get(resID);
	        queue.add(new TransactionTask(command));
		} else {
			// Means that the reservation might be already finished in this domain
			String prevDomain = previousDomains.get(resID);
			
			if(prevDomain != null) {
	        	Interdomain client;
                try {
                    client = new InterdomainClient(prevDomain);
                    client.reportFinished(resID, message, success);
                } catch (Exception e) {
                    log.error(this + " reportFinished: " + e.getMessage(), e);
                }
			} else {
				log.info(resID + " Report finish received: Reservation not found in the processor.");
			}
		}
	}

	public void activate(final String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Activate: Reservation " + resID + " already finished");
			return;
		}
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
                rdao.update(res);
        		res.activate(success);

        		hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Activate: " + resID;
			}
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}

	public void finish(final String resID, final boolean success) {
		final AutobahnReservation res = reservations.get(resID);

		if(res == null) {
			log.info("Finish: Reservation " + resID + " already finished");
			return;
		}
		
		AutobahnCommand command = new AutobahnCommand() {
            public void run() {
            	rdao.update(res);
        		res.finish(success);
        		
        		hdao.update(Translator.convertHistory(res));
            }

			@Override
			public String getInfo() {
				return "Finish: " + resID;
			}
        };

        if(restorationMode) {
        	command.run();
        	return;
        }
        
        BlockingQueue<AutobahnCommand> queue = events.get(resID);
        queue.add(new TransactionTask(command));
	}
	
	abstract class AutobahnCommand implements Runnable {
		public abstract String getInfo();
	}
	
	class TransactionTask extends AutobahnCommand {
		private AutobahnCommand command = null;
		
		TransactionTask(AutobahnCommand command) {
			this.command = command;
		}
		
		public void run() {
			HibernateUtil hbm = IdmHibernateUtil.getInstance();
			Transaction t = hbm.beginTransaction();
			
			String start = "Processor: command " + command.getInfo() + " start";
			String end = "Processor: command " + command.getInfo() + " end";
			
			log.info(start);
			command.run();
			log.info(end);
			
			
			if (!t.wasCommitted()) {
				t.commit();
			}
            hbm.closeSession();
            
            String[] str = start.split(" ");
    	    String resId = str[str.length-2];
            try {
				createReservationLog(resId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public String getInfo() {
			return command.getInfo();
		}
	}
	
	class ReservationRunner implements Runnable, ReservationStatusListener {
		private String resID;
		private boolean end = false;
		
		public ReservationRunner(String resID) {
			this.resID = resID;
		}

		public void run() {
			BlockingQueue<AutobahnCommand> queue = events.get(resID);
			
			Runnable command = null;
			try {
				while(!end && (command = queue.take()) != null) {
					//Make sure that only a single reservation is 
					//processed each time 
					synchronized (rsvRunnerLock) {
						command.run();
					}
				}
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			} catch(Exception e) {
				log.error("Unexpected erro in Reservation Runner: ", e);
				AutobahnReservation rsv = reservations.get(resID);
				if (rsv != null) {
					List<ReservationStatusListener> list = rsv.getStatusListeners();
					if (list != null) {
						for (ReservationStatusListener listener : list) {
							if (listener != null) {
								listener.reservationProcessingFailed(resID, "Exception in ReservationRunner: " + e.getMessage());								
							}
						}
					}
				}
			}
		}

		public void reservationCancelled(String reservationId) {
			end = true;
			reservations.remove(reservationId);
		}

		public void reservationFinished(String reservationId) {
			end = true;
			AutobahnReservation res = reservations.remove(reservationId);
			
			if(res instanceof ExternalReservation)
				previousDomains.put(reservationId, res.getPrevDomainAddress());
		}

		public void reservationProcessingFailed(String reservationId,
				String cause) {
			end = true;
			reservations.remove(reservationId);
		}

		public void reservationScheduled(String reservationId) {
			// Do nothing
		}
		
		public void reservationActive(String reservationId) {
			// Do nothing
		}

		public void reservationModified(String reservationId, boolean success) {
			// Do nothing
		}
	}

	public void setRestorationMode(boolean mode) {
		this.restorationMode = mode;
	}
	
	public void addStatusListenerToAllReservations(ReservationStatusListener listener) {
		for (AutobahnReservation res : reservations.values()) {
			if(!res.getStatusListeners().contains(listener)) {
				res.addStatusListener(listener);
			}
		}
	}
	
	private void createReservationLog(String resId) throws Exception {
		
		Scanner sc = null;
	    
	    File folder = new File("logs/reservations");
		File filename = new File("logs/reservations/" + resId + ".log");
	    FileWriter fileWriter = null;
	    Boolean fileExists = null;
	    Date startDate = null;
	    
	    if(!folder.isDirectory()) {
	    	folder.mkdir();
	    }
	    
	    if(!filename.exists()) {
	    	try {
	    		filename.createNewFile();
	    		fileExists = false;
			} catch (IOException e) {
				log.debug(e.getMessage());
			}
	    } else {
	    	fileExists = true;
	    	startDate = parseDate(tail(filename));
	    }	    
	    	    
	    try {
	        sc = new Scanner(new BufferedReader(new FileReader("logs/debug.log")));
	        fileWriter = new FileWriter(filename, true);
	        
	        while (sc.hasNext()) {
	            String line = sc.nextLine();
	            if (fileExists.equals(true) && line.contains(resId) && parseDate(line).compareTo(startDate) > 0) {
					fileWriter.append(line + "\n");
				} else if (fileExists.equals(false) && line.contains(resId)) {
					fileWriter.append(line + "\n");
				}
	        }
	        
	        fileWriter.flush();	        
	    } catch (FileNotFoundException e) {
	    	log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		} finally {
	        if (sc != null) {
	            sc.close();
	        }
	    }
	}
	
	private Date parseDate(String line) {
		
		String re1=".*?";	
	    String re2="((?:2|1)\\d{3}(?:-|\\/)(?:(?:0[1-9])|(?:1[0-2]))(?:-|\\/)(?:(?:0[1-9])|(?:[1-2][0-9])|(?:3[0-1]))(?:T|\\s)(?:(?:[0-1][0-9])|(?:2[0-3])):(?:[0-5][0-9]):(?:[0-5][0-9]))";
	    String re3=".*?";	
	    String re4=".";	
	    String re5=".*?";	
	    String re6="(.)";	
	    String re7="(.)";	
	    String re8="(.)";	

	    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	    Matcher m = p.matcher(line);
	    if (m.find())
	    {
	        timestamp = m.group(1);
	        String c1=m.group(2);
	        String c2=m.group(3);
	        String c3=m.group(4);
	        milliseconds = c1.concat(c2).concat(c3);
	    }
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	    Date date = null;
	    
	    try {
			date = dateFormat.parse(timestamp + "," + milliseconds);
		} catch (ParseException e) {
			log.debug("Error in parsing Date Foermat: " + e.getMessage());
		}
		
	    return date;		
	}
	
	private String tail(File file) {
	    try {
	        RandomAccessFile fileHandler = new RandomAccessFile(file, "r");
	        long fileLength = file.length() - 1;
	        StringBuilder sb = new StringBuilder();

	        for(long filePointer = fileLength; filePointer != -1; filePointer--) {
	            fileHandler.seek(filePointer);
	            int readByte = fileHandler.readByte();

	            if(readByte == 0xA) {
	                if(filePointer == fileLength) {
	                    continue;
	                } else {
	                    break;
	                }
	            } else if(readByte == 0xD) {
	                if(filePointer == fileLength - 1) {
	                    continue;
	                } else {
	                    break;
	                }
	            }

	            sb.append((char) readByte);
	        }

	        String lastLine = sb.reverse().toString();
	        fileHandler.close();
	        
	        return lastLine;
	        
	    } catch(java.io.FileNotFoundException e) {
	        log.debug(e.getMessage());
	        return null;
	    } catch(java.io.IOException e) {
	        log.debug(e.getMessage());
	        return null;
	    }
	}
}