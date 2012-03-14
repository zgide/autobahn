package net.geant.autobahn.useraccesspoint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * The VLAN mode for a start or end port.
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public enum Mode {
	
	VLAN, UNTAGGED, TRANSPARENT
}