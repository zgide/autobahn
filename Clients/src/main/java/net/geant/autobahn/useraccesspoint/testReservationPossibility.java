package net.geant.autobahn.useraccesspoint;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;

public class testReservationPossibility {

	private static final QName SERVICE_NAME = new QName(
	        "http://useraccesspoint.autobahn.geant.net/",
	        "UserAccessPointService");
	private final static QName UserAccessPointPort = new QName(
	        "http://useraccesspoint.autobahn.geant.net/", "UserAccessPointPort");
	private Properties properties;
	private String propFile = "etc/properties";
	private UserAccessPoint uap = null;

	public Properties loadProperties(String filename) throws Exception {
		Properties properties = new Properties();
		InputStream fis = this.getClass().getClassLoader()
		        .getResourceAsStream(filename);
		properties.load(fis);
		fis.close();

		return properties;
	}

	public testReservationPossibility() throws Exception {

		try {
			properties = loadProperties(propFile);
		} catch (Exception e) {
			throw new Exception("property file not found");
		}

		String target = properties.getProperty("idm");
		if (target == null || target.length() == 0) {
			throw new Exception("no idm found on property file");
		}
		if (!target.startsWith("http://")) {
			target = "http://" + target;
		}
		if (!target.endsWith("/autobahn/uap")) {
			target = target + "/autobahn/uap";
		}

		Service service = Service.create(SERVICE_NAME);
		service.addPort(UserAccessPointPort, SOAPBinding.SOAP11HTTP_BINDING,
		        target);

		uap = service.getPort(UserAccessPointPort, UserAccessPoint.class);
	}

	private ReservationRequest newReservation() throws Exception {
		ReservationRequest r1 = new ReservationRequest();

		String s = properties.getProperty("capacity");// in bps = 1Gbs
		if (s == null || s.length() == 0) {
			throw new Exception("no capacity found on property file");
		}
		r1.setCapacity(Integer.parseInt(s));

		s = properties.getProperty("description");
		if (s == null || s.length() == 0) {
			throw new Exception("no discription found on property file");
		}
		r1.setDescription(s);

		s = properties.getProperty("startport");
		if (s == null || s.length() == 0) {
			throw new Exception("no startport found on property file");
		}
		r1.setStartPort(new PortType(s));

		s = properties.getProperty("endport");
		if (s == null || s.length() == 0) {
			throw new Exception("no endport found on property file");
		}
		r1.setEndPort(new PortType(s));

		int delay = 0;
		s = properties.getProperty("delay");
		if (s != null && s.length() > 0) {
			delay = Integer.parseInt(s);
		}
		r1.setMaxDelay(delay);

		s = properties.getProperty("domain");
		if (s == null || s.length() == 0) {
			throw new Exception("no domain found on property file");
		}

		s = properties.getProperty("bidirectional");
		if (s != null && s.length() > 0) {
			if (s.equals("true")) {
				r1.setBidirectional(true);
			} else if (s.equals("false")) {
				r1.setBidirectional(false);
			} else {
				throw new Exception("wrong bidirectional value");
			}
		}

		PathInfo exl = new PathInfo();
		exl.addDomain(s);
		r1.setUserExclude(exl);

		GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
		start.add(Calendar.HOUR, 10);
		GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
		end.add(Calendar.DATE, 10);

		r1.setStartTime(start);
		r1.setEndTime(end);
		r1.setPriority(Priority.NORMAL);
		r1.setResiliency(Resiliency.NONE);

		return r1;
	}

	public boolean makeReservation() throws Exception {
		ReservationRequest res = newReservation();
		boolean ret = false;
		ret = uap.checkReservationPossibility(res);

		return ret;
	}

	public static void main(String args[]) {
		testReservationPossibility instance = null;
		try {
			instance = new testReservationPossibility();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Test reservation Failed");
			System.exit(-1);
		}

		boolean b = false;
		try {
			b = instance.makeReservation();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			System.out.println("Test reservation Faild");
			System.exit(-1);
		}

		if (b) {
			System.out.println("Test reservation succeed");
			System.err.println("Test reservation succeed");
		} else {
			System.out.println("Test reservation Failed");
		}
		System.exit(0);
	}
}
