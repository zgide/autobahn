
package net.geant.autobahn.useraccesspoint;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 29 14:54:08 CET 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

public final class TestUap {

	private UserAccessPoint uap = null;
	
	private static final String domain1 = "http://localhost:8080/autobahn/uap";
	private static final String domain2 = "http://localhost:8080/autobahn/uap";
	private static final String domain3 = "http://localhost:8080/autobahn/uap";

	private static final String callback_url = "http://poznan.autobahn.psnc.pl:8443/callback";
	
    private static final QName SERVICE_NAME = new QName("http://useraccesspoint.jra3.geant2.net/", "UserAccessPointService");

    public TestUap(String target) {
        UserAccessPointService ss = new UserAccessPointService(target);
        uap = ss.getUserAccessPointPort();  
    }

    private void reservation() throws Exception {
        ServiceRequest sreq = new ServiceRequest();
        sreq.setUserName("user1");
        
        ReservationRequest r1 = new ReservationRequest();
        r1.setCapacity(1000000000);
        r1.setDescription("res1");
        
        r1.setStartPort("10.10.32.5");
        r1.setEndPort("10.11.32.10");
        
        
        GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
        start.add(Calendar.MINUTE, 2);
        GregorianCalendar end = (GregorianCalendar) Calendar.getInstance();
        end.add(Calendar.MINUTE, 4);
        
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setPriority(Priority.NORMAL);
        r1.setResiliency(Resiliency.NONE);
        //r1.setProcessNow(true);

        //createCalendar("2008-05-19T17:12:00+02:00")
        
        sreq.getReservations().add(r1);
        
        System.out.println("ServiceID:\n" + uap.submitService(sreq));
    }
    
    private void cancel(String srvID) throws UserAccessPointException {
    	
        uap.cancelService(srvID);
    }
    
    private void queryPorts() {

        String[] cports = uap.getDomainClientPorts();
        System.out.println("Domain client ports found: " + cports.length);
        for(String cport : cports) {
        	System.out.println(cport);
        }
        
        cports = uap.getAllClientPorts();
        System.out.println("All client ports found: " + cports.length);
        for(String cport : cports) {
        	System.out.println(cport);
        }
    }
    
    private void queryService(String srvID) throws UserAccessPointException {
    	ServiceResponse resp = uap.queryService(srvID);
    	
    	System.out.println("User: " + resp.getUserName());
    	
    	for(ReservationResponse resv : resp.getReservations()) {
    		System.out.println("Reservation:");
    		System.out.println(resv.getStartPort() + " " + resv.getEndPort());
    		System.out.println(resv.getState());
    	}
    }
    
    public static void main(String args[]) throws Exception {

    	TestUap instance = new TestUap(domain1);
        
    	//instance.reservation();
    	//instance.service4();
    	//instance.service2();
    	
    	//instance.modify("poznan.autobahn.psnc.pl:8080@1214833280501_res_1");
    	instance.cancel("localhost:8080@1228405982546");
        //instance.queryPorts();
    	//instance.queryService("poznan.autobahn.psnc.pl:8080@1218104551787");
    	
        System.exit(0);
    }
    
    private static XMLGregorianCalendar cal(String sdate) throws ParseException, DatatypeConfigurationException {
        XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(sdate);
        
        return result;
    }
    
    private static XMLGregorianCalendar cal(GregorianCalendar cal) throws DatatypeConfigurationException {
    	return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }
}
