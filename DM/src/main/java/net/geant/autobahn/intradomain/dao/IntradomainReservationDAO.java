package net.geant.autobahn.intradomain.dao;

import java.util.Map;

import net.geant.autobahn.dao.GenericDAO;
import net.geant.autobahn.intradomain.IntradomainReservation;

/**
 * DAO class used for operations on IntradomainReservation instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface IntradomainReservationDAO extends GenericDAO<IntradomainReservation, String> {

	/**
	 * Loads a map of intradomain reservation. String idenitfiers are the keys.
	 * 
	 * @return Map with intradomain reservation
	 */
	public Map<String, IntradomainReservation> loadReservations();
}
