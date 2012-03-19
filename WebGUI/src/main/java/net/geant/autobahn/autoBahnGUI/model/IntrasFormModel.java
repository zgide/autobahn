package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.geant.autobahn.intradomain.IntradomainReservation;

/**
 * Data model for Intradomain Reservations
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 * 
 */
public class IntrasFormModel implements Serializable {

    /**
     * List of registered IDMs
     */
    private List<String> idms;

    /**
     * Chosen IDM
     */
    private String currentIdm;

    /**
     * Identifies Intradomain Reservations at selected interdomain manager
     */
    List<IntradomainReservation> intras;
    
    /**
     * Identifies comparator for ordering intradomain reservations list
     */
    Comparator<IntradomainReservation> comparator;

    public List<String> getIdms() {
        return idms;
    }

    public void setIdms(List<String> idms) {
        this.idms = idms;
    }

    public String getCurrentIdm() {
        return currentIdm;
    }

    public void setCurrentIdm(String currentIdm) {
        this.currentIdm = currentIdm;
    }

    public List<IntradomainReservation> getIntras() {
        return intras;
    }

    public void setIntras(List<IntradomainReservation> intras) {
        this.intras = intras;
        if (intras != null && comparator != null) {
            Collections.sort(intras, comparator);
        }
    }

    public Comparator<IntradomainReservation> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<IntradomainReservation> comparator) {
        this.comparator = comparator;
        if (intras != null && comparator != null) {
            Collections.sort(intras, comparator);
        }
    }

}