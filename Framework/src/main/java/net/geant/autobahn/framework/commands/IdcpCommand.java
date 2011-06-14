package net.geant.autobahn.framework.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;

import net.geant.autobahn.framework.Framework;
import net.geant.autobahn.idcp.OscarsClient;
import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.idcp.notify.OscarsNotifyClient;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;

/**
 * Allows for calling some methods on IdcpClient
 * @author PCSS
 **/
public class IdcpCommand implements AutobahnCommand {
	
	private final static String NL = "\r\n";
	private final static String invalid = "specified arguments were invalid";
	private static HashMap<String, String> friends; // key - alias for address, value - address
	
	static {
		
		friends = new HashMap<String, String>();
		friends.put("local", "https://localhost:8090/autobahn/oscars");
		friends.put("localNotify", "https://localhost:8090/autobahn/oscarsnotify");
		friends.put("esnet", "https://oscars-dev.es.net/axis2/services/OSCARS");
		friends.put("esnetNotify", "https://oscars-dev.es.net/axis2/services/OSCARSNotify");
		// add others or retrieve from idm.properties
	}
	
	private String dumpResDetails(ResDetails res) {
		
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(res.getStartTime());
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(res.getStartTime());
		String st = new Date(res.getStartTime() * 1000).toString();
		String en = new Date(res.getEndTime() * 1000).toString();
				
		StringBuffer sb = new StringBuffer();
		sb.append("reservation id - " + res.getGlobalReservationId() + NL);
		sb.append("status - " + res.getStatus() + NL);
		sb.append("bandwidth - " + res.getBandwidth() + NL);
		sb.append("start time - " + st + NL);
		sb.append("end time - " + en + NL);
		sb.append("source - " + res.getPathInfo().getLayer2Info().getSrcEndpoint() + NL);
		sb.append("dest - " + res.getPathInfo().getLayer2Info().getDestEndpoint() + NL);
		// print hops
		sb.append("path:" + NL);
		List<CtrlPlaneHopContent> hops = res.getPathInfo().getPath().getHop();
		for (CtrlPlaneHopContent hop : hops)
			sb.append(hop.getLinkIdRef() + " - " + hop.getId() + NL);
		return sb.toString();
	}
	
	@Override
	public String execute(Framework autobahn, String[] args) {
		
		if (args.length == 2 && args[1].equalsIgnoreCase("friends")) {
			StringBuffer sb = new StringBuffer();
			for (String key : friends.keySet()) 
				sb.append(key + " - " + friends.get(key) + NL);
			return sb.toString();
		}
					
		// at least 3 args required - idcp address operation 
		if (args.length < 4)
			return invalid;
		
		final String address = friends.containsKey(args[1]) ? friends.get(args[1]) : args[1];
		final String operation = args[2];
		
		if (operation.equalsIgnoreCase("topology")) {
			String format, filename;
			if (args.length == 4) {
				format = args[3];
				filename = null;
			} else if (args.length == 5) {
				format = args[3];
				filename = args[4];
			} else
				return "too many arguments specified";
				
			if (!format.equalsIgnoreCase("autobahn") && !format.equalsIgnoreCase("idcp"))
				return "topology format can be either in autobahn or idcp format";
			
			try {
				
				StringBuffer sb = new StringBuffer();
				OscarsClient idcp = new OscarsClient(address);
				
				if (format.equalsIgnoreCase("autobahn")) {
					List<Link> links = idcp.getTopology();
					for (Link l : links)
						sb.append(l + "\r\n");
				} else if (format.equalsIgnoreCase("idcp")) {
					List<String> links = idcp.getIdcpTopology();
					for (String l : links)
						sb.append(l + "\r\n");
				}
				if (filename != null) {
					Writer output = new BufferedWriter(new FileWriter(filename));
					try {
						output.write(sb.toString());
					} finally {
						output.close();
					}
				}
				return sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return "topology could not be obtained - " + e.getMessage();
			}
		} else if (operation.equalsIgnoreCase("schedule")) {
			
			if (args.length != 7)
				return invalid;
			
			String resId = args[3];
			String source = args[4];
			String dest = args[5];
			int bandwidth = Integer.parseInt(args[6]);
			
			Reservation r = new Reservation();
			r.setBodID(resId);
			r.setDescription("autbahn test reservation - " + System.currentTimeMillis());
			r.setCapacity(bandwidth);
			Calendar start = Calendar.getInstance();
			start.add(Calendar.MINUTE, 1);
			Calendar end = Calendar.getInstance();
			end.add(Calendar.MINUTE, 2);
			r.setStartTime(start);
			r.setEndTime(end);
			
			try {
				OscarsClient idcp = new OscarsClient(address);
				idcp.scheduleReservation(r, source, dest, "any");
				return resId + " has been submitted";
			} catch (Exception e) {
				e.printStackTrace();
				return resId + " failed to schedule - " + e.getMessage();
			}
		} else if (operation.equalsIgnoreCase("cancel")) {
			
			if (args.length != 4)
				return invalid;
			
			String resId = args[3];
			try {
				OscarsClient idcp = new OscarsClient(address);
				idcp.cancelReservation(resId);
				return resId + " has been cancelled";
			} catch (Exception e) {
				e.printStackTrace();
				return resId + " failed to cancel - " + e.getMessage();
			}
		} else if (operation.equalsIgnoreCase("query")) {
			
			if (args.length != 4)
				return invalid;
			
			String resId = args[3];
			try {
				OscarsClient idcp = new OscarsClient(address);
				ResDetails res = idcp.queryReservation(resId);
				return dumpResDetails(res);
			} catch (Exception e) {
				return resId + " failed to query - " + e.getMessage();
			}
		} else if (operation.equalsIgnoreCase("subscribe")) {
			
			String consumer = friends.containsKey(args[3]) ? friends.get(args[3]) : args[3];
			String producer = friends.containsKey(args[4]) ? friends.get(args[4]) : args[4];
			
			try {
				OscarsNotifyClient idcpNotify = new OscarsNotifyClient(address);
				idcpNotify.subscribe(consumer, producer);
				return "subscribed to " + producer;
			} catch (Exception e) { 
				return "failed to subscribe";
			}
		} else if (operation.equalsIgnoreCase("unsubscribe")) {
			
			String consumer = friends.containsKey(args[3]) ? friends.get(args[3]) : args[3];
			try {
				OscarsNotifyClient idcpNotify = new OscarsNotifyClient(address);
				idcpNotify.unsubscribe(consumer);
				return "unsubscribed";
			} catch (Exception e) { 
				return "failed to unsubscribe";
			}
		} else
			return operation + " is not supported";
	}

	@Override
	public String commandInfo() {

		StringBuffer sb = new StringBuffer();
		sb.append("allows creating, cancelling and querying resources in idcp domains" + NL);
		sb.append("\t'friends' - returns a list of known aliases that can be used instead of full address" + NL);
		sb.append("\t'address topology autobahn|idcp [filename]' - gets topology either in autobahn or idcp format," +
				"optionally saves it to a file" + NL);
		sb.append("\t'address schedule resId source dest bandwidth' - reserves resources" + NL);
		sb.append("\t'address cancel resId' - cancels reservation indicated by resId" + NL);
		sb.append("\t'address query resId' - queries for reservation indicated by resId" + NL);
		sb.append("\t'address subscribe consumer producer' - subscribes for asynchronous event notifications" + NL);
		sb.append("\t'address unsubscribe consumer' - unsubscribes to stop receiving event notifications" + NL);
				
		return sb.toString();
	}
}