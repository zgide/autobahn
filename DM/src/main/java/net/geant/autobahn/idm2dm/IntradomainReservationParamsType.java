package net.geant.autobahn.idm2dm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.intradomain.IntradomainReservation;

/**
 * @author RRached
 *
 */
@XmlType(name="StringReservationType")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntradomainReservationParamsType {
	// produce a wrapper XML element around this collection
	@XmlElementWrapper(name="reservationList")
	// map each member of this list to an XML element named appointment
	@XmlElement(name="reservations")
	private List<StringReservation> reservations;
 
	public IntradomainReservationParamsType() {}
 
	public IntradomainReservationParamsType(Map<String, IntradomainReservation> map) {
		reservations = new ArrayList<StringReservation>();
		Set<Entry<String, IntradomainReservation>> set = map.entrySet();
		
		for (Entry<String, IntradomainReservation> idUserEntry : set) {
			reservations.add(new StringReservation(idUserEntry.getKey(), idUserEntry.getValue()));
		}
	}
 
	public List<StringReservation> getReservations() {
		return reservations;
	}

	protected static class StringReservation{
		@XmlElement(name="string")
		private String str;
		@XmlElement(name="reservation")
		private IntradomainReservation intraReservation;
		   
		protected StringReservation() {}
		   
		protected StringReservation(String strArg, IntradomainReservation rsvArg) {
			this.str = strArg;
			this.intraReservation= rsvArg;
		}
		
		protected String getString() {
			return str;
		}
		   
		protected IntradomainReservation getIntraReservation() {
			return intraReservation;
		}
	}
}
