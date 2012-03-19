package net.geant.autobahn.idm2dm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.geant.autobahn.idm2dm.IntradomainReservationParamsType.StringReservation;
import net.geant.autobahn.intradomain.IntradomainReservation;

/**
* @author Akis Kalligeros
*
*/
public class IntradomainReservationParamsAdapter extends XmlAdapter<IntradomainReservationParamsType, Map<String, IntradomainReservation> > {
	
	@Override
	public IntradomainReservationParamsType marshal(Map<String, IntradomainReservation> map) throws Exception {
	  return new IntradomainReservationParamsType(map);
	}

	@Override
	public Map<String, IntradomainReservation> unmarshal(IntradomainReservationParamsType type) throws Exception {
	  Map<String, IntradomainReservation> map = new HashMap<String, IntradomainReservation>();
	  List<StringReservation> paths = type.getReservations();
	  for (StringReservation path : paths) {
	     map.put(path.getString(), path.getIntraReservation());
	  }
	  return map;
	}
}
