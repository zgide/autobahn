
/*
 * 
 */

package net.geant.autobahn.idcp;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 2.2.6
 * Fri Sep 10 08:53:29 CEST 2010
 * Generated source version: 2.2.6
 * 
 */


@WebServiceClient(name = "OSCARS", 
                  wsdlLocation = "file:etc/wsdl/IDCP/OSCARS.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS") 
public class OSCARS_Service extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS", "OSCARS");
    public final static QName OSCARS = new QName("http://oscars.es.net/OSCARS", "OSCARS");
    static {
        URL url = null;
        try {
            url = new URL("file:etc/wsdl/IDCP/OSCARS.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:etc/wsdl/IDCP/OSCARS.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public OSCARS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OSCARS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    /**
     * if endpoint is null, the OSCARS location will be retrieved from WSDL
     */
    public OSCARS_Service(String endpoint) {
        super(WSDL_LOCATION, SERVICE);
        if (endpoint!=null) {
            super.addPort(OSCARS, SOAPBinding.SOAP11HTTP_BINDING, endpoint);
        }
    }

    /**
     * 
     * @return
     *     returns OSCARS
     */
    @WebEndpoint(name = "OSCARS")
    public OSCARS getOSCARS() {
        return super.getPort(OSCARS, OSCARS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OSCARS
     */
    @WebEndpoint(name = "OSCARS")
    public OSCARS getOSCARS(WebServiceFeature... features) {
        return super.getPort(OSCARS, OSCARS.class, features);
    }

}
