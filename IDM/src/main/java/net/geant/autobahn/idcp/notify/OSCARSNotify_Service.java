
/*
 * 
 */

package net.geant.autobahn.idcp.notify;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.6
 * Mon Jul 19 11:08:25 CEST 2010
 * Generated source version: 2.2.6
 * 
 */


@WebServiceClient(name = "OSCARSNotify", 
                  wsdlLocation = "file:src/main/resources/wsdl/IDCP/OSCARS-Notify.wsdl",
                  targetNamespace = "http://oscars.es.net/OSCARS") 
public class OSCARSNotify_Service extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://oscars.es.net/OSCARS", "OSCARSNotify");
    public final static QName OSCARSNotify = new QName("http://oscars.es.net/OSCARS", "OSCARSNotify");
    static {
        URL url = null;
        try {
            url = new URL("file:src/main/resources/wsdl/IDCP/OSCARS-Notify.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:src/main/resources/wsdl/IDCP/OSCARS-Notify.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public OSCARSNotify_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OSCARSNotify_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OSCARSNotify_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns OSCARSNotify
     */
    @WebEndpoint(name = "OSCARSNotify")
    public OSCARSNotify getOSCARSNotify() {
        return super.getPort(OSCARSNotify, OSCARSNotify.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OSCARSNotify
     */
    @WebEndpoint(name = "OSCARSNotify")
    public OSCARSNotify getOSCARSNotify(WebServiceFeature... features) {
        return super.getPort(OSCARSNotify, OSCARSNotify.class, features);
    }

}