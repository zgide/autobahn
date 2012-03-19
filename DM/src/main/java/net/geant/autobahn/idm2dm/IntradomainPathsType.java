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

//import net.geant.autobahn.idm2dm.StringPathType.StringPath;
import net.geant.autobahn.intradomain.IntradomainPath;

/**
 * @author RRached
 *
 */
@XmlType(name="StringPathType")
@XmlAccessorType(XmlAccessType.FIELD)
public class IntradomainPathsType {
	// produce a wrapper XML element around this collection
	@XmlElementWrapper(name="pathList")
	// map each member of this list to an XML element named appointment
	@XmlElement(name="paths")
	private List<StringPath> paths;
 
	public IntradomainPathsType() {}
 
	public IntradomainPathsType(Map<String, IntradomainPath> map) {
		paths = new ArrayList<StringPath>();
		Set<Entry<String, IntradomainPath>> set = map.entrySet();
		
		for (Entry<String, IntradomainPath> idUserEntry : set) {
			paths.add(new StringPath(idUserEntry.getKey(), idUserEntry.getValue()));
		}
	}
 
	public List<StringPath> getPaths() {
		return paths;
	}

	protected static class StringPath{
		@XmlElement(name="string")
		private String str;
		@XmlElement(name="path")
		private IntradomainPath intraPath;
		   
		protected StringPath() {}
		   
		protected StringPath(String strArg, IntradomainPath path) {
			this.str = strArg;
			this.intraPath = path;
		}
		
		protected String getString() {
			return str;
		}
		   
		protected IntradomainPath getIntraPath() {
			return intraPath;
		}
	}
}
