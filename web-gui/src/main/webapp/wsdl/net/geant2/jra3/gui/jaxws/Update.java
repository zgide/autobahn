
package net.geant2.jra3.gui.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.3-incubator
 * Fri Apr 11 14:47:27 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

@XmlRootElement(namespace = "http://gui.jra3.geant2.net/", name = "update")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://gui.jra3.geant2.net/", name = "update")

public class Update {

@XmlElement(namespace = "", name = "idm")
    private java.lang.String idm;
@XmlElement(namespace = "", name = "event")
    private net.geant2.jra3.gui.EventType event;
@XmlElement(namespace = "", name = "properties")
    private java.util.List properties;

    public java.lang.String getIdm ()     {
	           return this.idm;
        }

    public void setIdm (   java.lang.String newIdm  )     {
	           this.idm = newIdm;
        }

    public net.geant2.jra3.gui.EventType getEvent ()     {
	           return this.event;
        }

    public void setEvent (   net.geant2.jra3.gui.EventType newEvent  )     {
	           this.event = newEvent;
        }

    public java.util.List getProperties ()     {
	           return this.properties;
        }

    public void setProperties (   java.util.List newProperties  )     {
	           this.properties = newProperties;
        }

}
