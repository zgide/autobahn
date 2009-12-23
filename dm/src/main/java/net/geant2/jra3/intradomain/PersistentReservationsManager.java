package net.geant2.jra3.intradomain;

import java.util.HashMap;
import java.util.Map;

import net.geant2.jra3.dao.hibernate.HibernateDmDAOFactory;
import net.geant2.jra3.dao.hibernate.HibernateUtil;
import net.geant2.jra3.intradomain.dao.IntradomainReservationDAO;

import org.hibernate.Transaction;

/**
 * Helper class allowing persisting intradomain reservations.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class PersistentReservationsManager {

	private HibernateUtil hbm = null;
	
	/**
	 * Default constructor - no database
	 */
	public PersistentReservationsManager() {
		
	}
	
	public PersistentReservationsManager(HibernateUtil hbm) {
		this.hbm = hbm;
	}
	
	/**
	 * Saves the intradomain reservation in the database.
	 * 
	 * @param intraRes Intradomain reservation to be saved
	 */
	public void save(IntradomainReservation intraRes) {
		if(hbm == null)
			return;
		
		IntradomainReservationDAO dao = HibernateDmDAOFactory.getInstance()
				.getIntradomainReservationDAO();
		
		Transaction t = hbm.beginTransaction();
		dao.update(intraRes);
		t.commit();
		
		hbm.closeSession();
	}

	/**
	 * Attached reservation object to the current session.
	 * 
	 * @param intraRes
	 */
	public void attach(IntradomainReservation intraRes) {
		if(hbm == null)
			return;
		
		hbm.currentSession().saveOrUpdate(intraRes);
	}
	
	/**
	 * Removes the intradomain reservation from the database.
	 * 
	 * @param intraRes Intradomain reservation to be removed
	 */
	public void delete(IntradomainReservation intraRes) {
		if(hbm == null)
			return;

		IntradomainReservationDAO dao = HibernateDmDAOFactory.getInstance()
				.getIntradomainReservationDAO();
		
		IntradomainReservation res = dao.get(intraRes.getReservationId());
		if(res != null) {
			Transaction t = hbm.beginTransaction();
		
			dao.delete(res);
		
			t.commit();
		}
		
		hbm.closeSession();
	}
	
	/**
	 * Loads all intradomain reservation as a map, string identifiers are the keys.
	 * 
	 * @return Map of the reservations
	 */
	public Map<String, IntradomainReservation> loadReservations() {
		if(hbm == null)
			return new HashMap<String, IntradomainReservation>();

		IntradomainReservationDAO dao = HibernateDmDAOFactory.getInstance()
				.getIntradomainReservationDAO();
		
		return dao.loadReservations();
	}
}
