
package net.geant.autobahn.administration.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by the CXF 2.0.3-incubator
 * Fri Apr 11 14:47:18 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

@XmlRootElement(namespace = "http://administration.autobahn.geant.net/", name = "getServicesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "http://administration.autobahn.geant.net/", name = "getServicesResponse")

public class GetServicesResponse {

@XmlElement(namespace = "", name = "serivces")
    private java.util.List serivces;

    public java.util.List getSerivces ()     {
	           return this.serivces;
        }

    public void setSerivces (   java.util.List newSerivces  )     {
	           this.serivces = newSerivces;
        }

}

