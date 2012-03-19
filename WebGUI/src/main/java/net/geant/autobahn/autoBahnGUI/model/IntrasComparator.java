package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Comparator;

import net.geant.autobahn.intradomain.IntradomainReservation;

/**
 * Comparator for sorting IntradomainReservation lists
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 * 
 */
public class IntrasComparator implements Comparator<IntradomainReservation>,
        Serializable {
    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(IntradomainReservation o1, IntradomainReservation o2) {
        String str1 = o1.getReservationId();
        String str2 = o2.getReservationId();

        return str2.compareTo(str1);
    }
}
