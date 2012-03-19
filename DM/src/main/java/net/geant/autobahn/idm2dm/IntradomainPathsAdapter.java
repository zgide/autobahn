package net.geant.autobahn.idm2dm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.geant.autobahn.idm2dm.IntradomainPathsType.StringPath;
import net.geant.autobahn.intradomain.IntradomainPath;

/**
* @author Akis Kalligeros
*
*/
public class IntradomainPathsAdapter extends XmlAdapter<IntradomainPathsType, Map<String, IntradomainPath> > {
	
	@Override
	public IntradomainPathsType marshal(Map<String, IntradomainPath> map) throws Exception {
	  return new IntradomainPathsType(map);
	}

	@Override
	public Map<String, IntradomainPath> unmarshal(IntradomainPathsType type) throws Exception {
	  Map<String, IntradomainPath> map = new HashMap<String, IntradomainPath>();
	  List<StringPath> paths = type.getPaths();
	  for (IntradomainPathsType.StringPath path : paths) {
	     map.put(path.getString(), path.getIntraPath());
	  }
	  return map;
	}
}
