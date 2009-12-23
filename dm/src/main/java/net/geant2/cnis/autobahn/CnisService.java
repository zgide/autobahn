
package net.geant2.cnis.autobahn;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Thu Feb 26 14:25:11 CET 2009
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebServiceClient(name = "Autobahn", targetNamespace = "http://autobahn.cnis.geant2.net", wsdlLocation = "file:wsdl/cnis.wsdl")
public class CnisService {

	private Service service;
	
    private final static QName SERVICE = new QName("http://autobahn.cnis.geant2.net", "Autobahn");
    private final static QName AutobahnHttpPort = new QName("http://autobahn.cnis.geant2.net", "AutobahnHttpPort");

    public CnisService(String endPoint) {
    	service = Service.create(SERVICE);
    	service.addPort(AutobahnHttpPort, SOAPBinding.SOAP11HTTP_BINDING, endPoint);
    }

    /**
     * 
     * @return
     *     returns AutobahnHttpPort
     */
    @WebEndpoint(name = "AutobahnHttpPort")
    public AutobahnPortType getCnisHttpPort() {
        return service.getPort(AutobahnHttpPort, AutobahnPortType.class);
    }

}