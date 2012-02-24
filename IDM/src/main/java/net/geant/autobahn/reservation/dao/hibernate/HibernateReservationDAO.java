/**
 * HibernateReservationDAO.java
 *
 * 2007-01-19
 */
package net.geant.autobahn.reservation.dao.hibernate;

import java.util.Collections;
import java.util.List;

import net.geant.autobahn.dao.hibernate.HibernateGenericDAO;
import net.geant.autobahn.dao.hibernate.IdmHibernateUtil;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.Reservation;
import net.geant.autobahn.reservation.dao.ReservationDAO;
import net.geant.autobahn.reservation.states.ed.ExternalDomainState;
import net.geant.autobahn.reservation.states.hd.HomeDomainState;
import net.geant.autobahn.reservation.states.ld.LastDomainState;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

/**
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class HibernateReservationDAO extends
        HibernateGenericDAO<Reservation, String> implements ReservationDAO {

    public HibernateReservationDAO(IdmHibernateUtil hbm) {
    	super(hbm);
	}

	public List<Reservation> getRunningReservations() {
        List<Reservation> reservations = findByCriteria(
        		Restrictions.in("state", new Object[] {
        					HomeDomainState.ACTIVE.getCode(), 
        					HomeDomainState.SCHEDULED.getCode(),
        					HomeDomainState.SCHEDULING.getCode()        					
        				})); 

        return reservations;
    }
	
	public List<Reservation> getActiveReservations() {
        List<Reservation> reservations = findByCriteria(
        		Restrictions.in("state", new Object[] {
        					HomeDomainState.ACTIVE.getCode(), 
        					HomeDomainState.ACTIVATING.getCode(),
        					HomeDomainState.WITHDRAWING.getCode(),
        					HomeDomainState.CANCELLING.getCode(),
        					HomeDomainState.FINISHING.getCode(),
        					HomeDomainState.DEFERRED_CANCEL.getCode(),
        					ExternalDomainState.CANCELLING.getCode(),
        					ExternalDomainState.WITHDRAWING.getCode(),
        					ExternalDomainState.ACTIVE.getCode(),
        					LastDomainState.ACTIVE.getCode()
        				})); 

        return reservations;
    }
	
	/**
	 * This function assumes that states have the same int value regardless 
	 * if they are Home, Last or External Reservations.
	 */
	public List<Reservation> getFinishedReservations() {
        List<Reservation> reservations = findByCriteria(
        		Restrictions.in("state", new Object[] {
        					HomeDomainState.FINISHED.getCode(),
        					HomeDomainState.FAILED.getCode(),
        					HomeDomainState.CANCELLED.getCode()
        				})); 

        return reservations;
    }

    public List<Reservation> getReservationsThroughDomain(String domainID) {
        // TODO Auto-generated method stub
        return null;
    }

    public Reservation getByBodID(String bodID) {
        List<Reservation> reservations = findByCriteria(Expression.eq("bodID", bodID));

        if (reservations == null || reservations.size() < 1) {
            return null;
        }

        return reservations.get(0);
    }

    @SuppressWarnings("unchecked")
	public List<Reservation> getReservationsThroughLink(String linkBodID) {
        
        Criteria crit = getSession().createCriteria(Link.class);
        crit.add(Expression.eq("bodID", linkBodID));
        
        Link l = (Link) crit.uniqueResult(); 
        
        if(l == null) {
            return Collections.EMPTY_LIST;
        }
        
        Query q = getSession().createQuery("from Reservation r " +
                "where :link in elements(r.path.links)");
        
        q.setEntity("link", l);
        
        return q.list();
    }
}
