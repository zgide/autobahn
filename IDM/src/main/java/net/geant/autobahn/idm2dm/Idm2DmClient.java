package net.geant.autobahn.idm2dm;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;

import net.geant.autobahn.aai.AAIException;
import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.constraints.DomainConstraints;
import net.geant.autobahn.idm2dm.ConstraintsAlreadyUsedException;
import net.geant.autobahn.idm2dm.Idm2Dm;
import net.geant.autobahn.idm2dm.OversubscribedException;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.network.LinkIdentifiers;
import net.geant.autobahn.reservation.ReservationParams;
import net.geant.autobahn.reservation.TimeRange;

import org.apache.log4j.Logger;

/**
 * Client for sending messages to DM
 * @author Michal
 */

public class Idm2DmClient implements Idm2Dm {

	private static final long serialVersionUID = 2502655943878207313L;

	static private Logger log = Logger.getLogger(Idm2DmClient.class);
	
	private Idm2Dm idm2dm;
	
	public Idm2DmClient(String endPoint) {
        if("none".equalsIgnoreCase(endPoint)) {
            log.info("DM location was specified as none, IDM->DM communication impossible");
            return;
        }
        
        try {
            new URL(endPoint);
            log.debug("DM location seems a valid URL, trying to connect to it");
        } catch (MalformedURLException e) {
            log.error("No valid DM location ("+ endPoint +") could be found, IDM->DM communication impossible");
            return;
        }
        
		Idm2DmService service = new Idm2DmService(endPoint);
		idm2dm = service.getIdm2DmPort();
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#addReservation(java.lang.String, net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public void addReservation(String resId, Link[] links, ReservationParams arg2)
			throws ConstraintsAlreadyUsedException, OversubscribedException {
		log.info("IDM -> DM: add reservation " + resId + " start");
		idm2dm.addReservation(resId, links, arg2);
		log.info("IDM -> DM: add reservation " + resId + "end");
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#checkResources(net.geant.autobahn.network.Link[], net.geant.autobahn.reservation.ReservationParams)
	 */
	public DomainConstraints[] checkResources(Link[] links, ReservationParams arg1)
			throws OversubscribedException, AAIException {
		log.info("IDM -> DM: checking resources");
		return idm2dm.checkResources(links, arg1);
	}

	/* (non-Javadoc)
	 * @see net.geant.autobahn.idm2dm.Idm2Dm#removeReservation(java.lang.String)
	 */
	public void removeReservation(String resId) {
		log.info("IDM -> DM: removing reservation " + resId + " start");
		idm2dm.removeReservation(resId);
		log.info("IDM -> DM: removing reservation " + resId + " end");
	}

	public boolean checkModification(String resId, TimeRange time) {
		return idm2dm.checkModification(resId, time);
	}

	public void modifyReservation(String resId, TimeRange time) {
		idm2dm.modifyReservation(resId, time);
	}
	
	public void prepareTopology(String idmAddress) {
		idm2dm.prepareTopology(idmAddress);
	}

	public LinkIdentifiers getIdentifiers(String portName, String bodId) {
		return idm2dm.getIdentifiers(portName, bodId);
	}

	public void restart() {
		idm2dm.restart();
	}

    public AccessPolicy getAccessPolicy() {
        return idm2dm.getAccessPolicy();
    }

    public void setAccessPolicy(AccessPolicy accessPolicy) {
        idm2dm.setAccessPolicy(accessPolicy);
    }
    
    public HashMap<String, IntradomainPath> getIntradomainPaths() {
    	return idm2dm.getIntradomainPaths();
	}
	
	public HashMap<String, IntradomainReservation> getIntradomainReservationParams() {
        return idm2dm.getIntradomainReservationParams();
	}
	
	public HashMap<GenericLink, TreeMap<Calendar, Long>> getIntradomainCalendarsUsage(IntradomainPath path) {
		return idm2dm.getIntradomainCalendarsUsage(path);
	}
}