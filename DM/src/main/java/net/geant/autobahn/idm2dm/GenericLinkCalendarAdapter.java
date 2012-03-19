package net.geant.autobahn.idm2dm;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.geant.autobahn.intradomain.common.GenericLink;

/**
* @author Akis Kalligeros
*
*/
public class GenericLinkCalendarAdapter extends XmlAdapter<GenericLinkCalendarType, Map<GenericLink, TreeMap<Calendar, Long>> > {
	
	@Override
	public GenericLinkCalendarType marshal(Map<GenericLink, TreeMap<Calendar, Long>> map) throws Exception {
		return new GenericLinkCalendarType(map);
	}

	@Override
	public Map<GenericLink, TreeMap<Calendar, Long>> unmarshal(GenericLinkCalendarType type) throws Exception {
		Map<GenericLink, TreeMap<Calendar, Long>> map = new HashMap<GenericLink, TreeMap<Calendar,Long>>();

		List<GenericLinkCalendarType.GenericLinkCalendar> links = type.getLinks();
		for (GenericLinkCalendarType.GenericLinkCalendar link : links) {
			map.put(link.getLink(), link.getCalendar());
		}
		
	  return map;
	}
}
