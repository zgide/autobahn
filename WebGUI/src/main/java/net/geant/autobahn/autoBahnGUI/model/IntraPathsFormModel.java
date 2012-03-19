package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Data model for Intradomain Paths
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 * 
 */
public class IntraPathsFormModel implements Serializable {

    /**
     * List of registered IDMs
     */
    private List<String> idms;

    /**
     * Chosen IDM
     */
    private String currentIdm;

    /**
     * Identifies Intradomain Paths at selected interdomain manager
     */
    HashMap<String, IntradomainPath> intraPaths;
    
    /**
     * Identifies comparator for ordering intradomain reservations list
     */
    //Comparator<IntradomainReservation> comparator;

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

    public HashMap<String, IntradomainPath> getIntraPaths() {
        return intraPaths;
    }

    public void setIntraPaths(HashMap<String, IntradomainPath> intraPaths) {
        this.intraPaths = intraPaths;
        /*if (intras != null && comparator != null) {
            Collections.sort(intras, comparator);
        }*/
    }
/*
    public Comparator<IntradomainReservation> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<IntradomainReservation> comparator) {
        this.comparator = comparator;
        if (intras != null && comparator != null) {
            Collections.sort(intras, comparator);
        }
    }
*/

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (intraPaths == null) {
            return null;
        }
        
        String res = "";

        Iterator<String> iterator = intraPaths.keySet().iterator();
        while (iterator.hasNext()) {
            String res_id = iterator.next();
            IntradomainPath path = intraPaths.get(res_id);
            res += "Reservation " + res_id + " uses path:<br/>" + path + "<br/><br/>";
        }
        return res;
    }
}