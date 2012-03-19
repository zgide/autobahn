package net.geant.autobahn.administration;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.idm.AccessPoint;
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

@WebService(name = "Administration", serviceName = "AdministrationService",
        portName = "AdministrationPort",
        targetNamespace = "http://administration.autobahn.geant.net/", 
        endpointInterface = "net.geant.autobahn.administration.Administration")
public class AdministrationImpl implements Administration {
	
	@XmlJavaTypeAdapter(value = IntradomainPathsAdapter.class)
	@XmlElement(name = "paths")
	HashMap<String, IntradomainPath> intraPaths = new LinkedHashMap<String, IntradomainPath>();
	
	@XmlJavaTypeAdapter(value = IntradomainReservationParamsAdapter.class)
	@XmlElement(name = "reservations")
	HashMap<String, IntradomainReservation> intraReservations = new LinkedHashMap<String, IntradomainReservation>();
	
	@XmlJavaTypeAdapter(value = GenericLinkCalendarAdapter.class)
	@XmlElement(name = "linksCalendar")
	HashMap<GenericLink, TreeMap<Calendar, Long>> linksCalendar = new LinkedHashMap<GenericLink, TreeMap<Calendar,Long>>();
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getLog(boolean)
	 */
	public String getLog(boolean all) {

		return AccessPoint.getInstance().getLog(all);
	}

    /* (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getStatistics(boolean)
     */
    public StatisticsType getStatistics(boolean all) {

        return AccessPoint.getInstance().getStatistics(all);
    }

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getProperties()
	 */
	public List<KeyValue> getProperties() {
		
		return AccessPoint.getInstance().getProperties();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
	 */
	public ReservationType getReservation(String resID) {
	
		return AccessPoint.getInstance().getReservation(resID);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getTopology()
	 */
	public List<Link> getTopology() {

		return AccessPoint.getInstance().getTopology();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setTopology(java.util.List)
	 */
	public void setTopology(List<Link> links) {
		
		AccessPoint.getInstance().setTopology(links);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#setProperties(java.util.Properties)
	 */
	public void setProperties(List<KeyValue> properties) {

		AccessPoint.getInstance().setProperties(properties);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getServices()
	 */
	public List<ServiceType> getServices() {
		return AccessPoint.getInstance().getServices();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getService(java.lang.String)
	 */
	public ServiceType getService(String serviceId) {
		return AccessPoint.getInstance().getService(serviceId); 
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#getStatus()
	 */
	public Status getStatus() {

		return AccessPoint.getInstance().getStatus();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.administration.Administration#cancelAllServices()
	 */
	public void cancelAllServices() {
		
		AccessPoint.getInstance().cancelAllServices();
	}

    /* (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getAccessPolicy()
     */
    public AccessPolicy getAccessPolicy() {
        return AccessPoint.getInstance().getAccessPolicy();
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#setAccessPolicy(net.geant.autobahn.aai.AccessPolicy)
     */
    public void setAccessPolicy(AccessPolicy accessPolicy) {
        AccessPoint.getInstance().setAccessPolicy(accessPolicy);
    }
    
    public HashMap<String, IntradomainPath> getIntradomainPaths(){
    	intraPaths = AccessPoint.getInstance().getIntradomainPaths();
    
    	return intraPaths; 
    }
    
    public HashMap<String, IntradomainReservation> getIntradomainReservationParams() {
        intraReservations = AccessPoint.getInstance().getIntradomainReservationParams();
    	
        return intraReservations;
    }
    
    public HashMap<GenericLink, TreeMap<Calendar, Long>> getIntradomainCalendarsUsage(IntradomainPath path) {
    	linksCalendar = AccessPoint.getInstance().getIntradomainCalendarsUsage(path);
        
        return linksCalendar;
    }

	public String getReservationLog(String resId) {
		return AccessPoint.getInstance().getReservationLog(resId);
	}

    @Override
    public void restart() {

        AccessPoint.getInstance().restart();
    }

    @Override
    public void handleTopologyChange(boolean deleteReservations, boolean update) throws AdministrationException {
        AccessPoint.getInstance().handleTopologyChange(deleteReservations, update);
    }    
}
