package net.geant.autobahn.idm2dm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * @author RRached
 *
 */
@XmlType(name="CalendarLongType")
@XmlAccessorType(XmlAccessType.FIELD)
public class CalendarLongType {
	// produce a wrapper XML element around this collection
	@XmlElementWrapper(name="calendarLong")
	// map each member of this list to an XML element named appointment
	@XmlElement(name="calendar")
	private List<CalendarLong> calendars;
 
	public CalendarLongType() {}
 
	public CalendarLongType(Map<Calendar, Long> map) {
		calendars = new ArrayList<CalendarLong>();
		Set<Entry<Calendar, Long>> set = map.entrySet();
		
		for (Entry<Calendar, Long> idUserEntry : set) {
			calendars.add(new CalendarLong(idUserEntry.getKey(), idUserEntry.getValue()));
		}
	}
 
	public List<CalendarLong> getCalendars() {
		return calendars;
	}

	protected static class CalendarLong{
		@XmlElement(name="calendar")
		private Calendar calendar;
		@XmlElement(name="long")
		private Long longVar;
		   
		protected CalendarLong() {}
		   
		protected CalendarLong(Calendar calendarArg, Long longArg) {
			this.calendar = calendarArg;
			this.longVar = longArg;
		}
		
		protected Calendar getCalendar() {
			return calendar;
		}
		   
		protected Long getLong() {
			return longVar;
		}
	}
}
