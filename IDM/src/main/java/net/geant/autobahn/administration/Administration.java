package net.geant.autobahn.administration;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.idm2dm.GenericLinkCalendarAdapter;
import net.geant.autobahn.idm2dm.IntradomainPathsAdapter;
import net.geant.autobahn.idm2dm.IntradomainReservationParamsAdapter;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.network.Link;

/**
 * @author Michal
 */

@WebService(targetNamespace = "http://administration.autobahn.geant.net/", name = "Administration")
public interface Administration {
	
	@WebResult(name="Properties")
	List<KeyValue> getProperties();
	
	void setProperties(@WebParam(name="properties")List<KeyValue> properties);
		
	@WebResult(name="Reservation")
	ReservationType getReservation(@WebParam(name="resID")String resID);
	
	@WebResult(name="log")
	String getLog(@WebParam(name="all")boolean all);
		
    @WebResult(name="statistics")
    StatisticsType getStatistics(@WebParam(name="all")boolean all);
    
	@WebResult(name="links")
	List<Link> getTopology();
	
	void setTopology(@WebParam(name="links")List<Link> links);
	
	@WebResult(name="serivces")
	List<ServiceType> getServices();
	
	@WebResult(name="service")
	ServiceType getService(String serviceId);
	
	@WebResult(name="status")
	Status getStatus();
	
	@WebMethod
	void cancelAllServices();
	
    @WebMethod
    public AccessPolicy getAccessPolicy();

    @WebMethod
    public void setAccessPolicy(AccessPolicy accessPolicy);
    
    @WebResult(name="intradomainPaths")
    @XmlJavaTypeAdapter(IntradomainPathsAdapter.class)
	HashMap<String, IntradomainPath> getIntradomainPaths();
    
    @WebResult(name="reservationParams")
    @XmlJavaTypeAdapter(IntradomainReservationParamsAdapter.class)
    HashMap<String, IntradomainReservation> getIntradomainReservationParams();
    
    @WebResult (name="usageCalendars")
    @XmlJavaTypeAdapter(GenericLinkCalendarAdapter.class)
    HashMap<GenericLink, TreeMap<Calendar, Long>> getIntradomainCalendarsUsage(IntradomainPath path);
    
    @WebResult(name="rsvLog")
    String getReservationLog(@WebParam(name="resId")String resId);
	
	@WebMethod
	void restart();

    @WebMethod
    void handleTopologyChange(boolean deleteReservations, boolean update) throws AdministrationException;
}
