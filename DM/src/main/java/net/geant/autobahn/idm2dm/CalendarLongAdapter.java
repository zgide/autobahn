package net.geant.autobahn.idm2dm;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.geant.autobahn.idm2dm.CalendarLongType.CalendarLong;

/**
* @author Akis Kalligeros
*
*/
public class CalendarLongAdapter extends XmlAdapter<CalendarLongType, Map<Calendar, Long>> {
	
	@Override
	public CalendarLongType marshal(Map<Calendar, Long> map) throws Exception {
	  return new CalendarLongType(map);
	}

	@Override
	public Map<Calendar, Long> unmarshal(CalendarLongType type) throws Exception {
	  Map<Calendar, Long> map = new TreeMap<Calendar,Long>();
	  List<CalendarLong> calendars = type.getCalendars();
	  for (CalendarLongType.CalendarLong calendar : calendars) {
	     map.put(calendar.getCalendar(), calendar.getLong());
	  }
	  return map;
	}
}
