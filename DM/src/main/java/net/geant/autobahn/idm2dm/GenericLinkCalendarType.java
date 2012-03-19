package net.geant.autobahn.idm2dm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.geant.autobahn.intradomain.common.GenericLink;

/**
 * @author RRached
 *
 */
@XmlType(name="GenericLinkCalendarType")
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericLinkCalendarType {
	@XmlElementWrapper(name="linkCalendar")
	@XmlElement(name="links")
	private List<GenericLinkCalendar> links;
 
	public GenericLinkCalendarType() {}
 
	public GenericLinkCalendarType(Map<GenericLink, TreeMap<Calendar, Long>> map) {
	
		links = new ArrayList<GenericLinkCalendar>();
		Set<Entry<GenericLink, TreeMap<Calendar, Long>>> set = map.entrySet();
		
		for (Entry<GenericLink, TreeMap<Calendar, Long>> idUserEntry : set) {
			links.add(new GenericLinkCalendar(idUserEntry.getKey(), idUserEntry.getValue()));
		}
	}
 
	public List<GenericLinkCalendar> getLinks() {
		return links;
	}

	protected static class GenericLinkCalendar{
		
		@XmlElement(name="link")
		private GenericLink link;
		
		@XmlJavaTypeAdapter(CalendarLongAdapter.class)
		@XmlElement(name="calendar")
		private TreeMap<Calendar, Long> calendar;
		   
		protected GenericLinkCalendar() {}
		   
		protected GenericLinkCalendar(GenericLink linkArg, TreeMap<Calendar, Long> calendarArg) {
			this.link = linkArg;
			this.calendar = calendarArg;
		}
		
		protected GenericLink getLink() {
			return link;
		}
		   
		protected TreeMap<Calendar, Long> getCalendar() {
			return calendar;
		}
	}
}
