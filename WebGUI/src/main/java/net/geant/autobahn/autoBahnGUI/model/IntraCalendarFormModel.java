package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * Data model for Intradomain Paths
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 * 
 */
public class IntraCalendarFormModel implements Serializable {

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
    HashMap<GenericLink, TreeMap<Calendar, Long>> intraCalendar;
    
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

    public HashMap<GenericLink, TreeMap<Calendar, Long>> getIntraCalendar() {
        return intraCalendar;
    }

    public void setIntraCalendar(HashMap<GenericLink, TreeMap<Calendar, Long>> intraCalendar) {
        this.intraCalendar = intraCalendar;
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
        if (intraCalendar == null) {
            return null;
        }
        
        String res = "";

        Iterator<GenericLink> iterator = intraCalendar.keySet().iterator();
        while (iterator.hasNext()) {
            GenericLink gl = iterator.next();
            TreeMap<Calendar, Long> gl_cal = intraCalendar.get(gl);
            res += "<br/>Generic Link " + gl + " resource usage:</br>";

            Iterator<Calendar> iterator2 = gl_cal.keySet().iterator();
            while (iterator2.hasNext()) {
                Calendar cal = iterator2.next();
                Long usage = gl_cal.get(cal);
                res += "\tAt moment " + cal.getTime() + " reserved capacity becomes " + usage + "bps.</br>";
            }
        }
        return res;
    }
    
}