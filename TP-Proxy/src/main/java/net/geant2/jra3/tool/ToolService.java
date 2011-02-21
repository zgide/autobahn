
/*
 * 
 */

package net.geant2.jra3.tool;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.9
 * Mon Feb 21 17:26:54 EET 2011
 * Generated source version: 2.2.9
 * 
 */


@WebServiceClient(name = "ToolService", 
                  wsdlLocation = "file:/D:/EclipseWorkspaces_mvn_new/autobahn/TP-Proxy/src/main/resources/wsdl/heanet.wsdl",
                  targetNamespace = "http://tool.jra3.geant2.net/") 
public class ToolService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://tool.jra3.geant2.net/", "ToolService");
    public final static QName ToolPort = new QName("http://tool.jra3.geant2.net/", "ToolPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/EclipseWorkspaces_mvn_new/autobahn/TP-Proxy/src/main/resources/wsdl/heanet.wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from file:/D:/EclipseWorkspaces_mvn_new/autobahn/TP-Proxy/src/main/resources/wsdl/heanet.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ToolService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ToolService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ToolService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns Tool
     */
    @WebEndpoint(name = "ToolPort")
    public Tool getToolPort() {
        return super.getPort(ToolPort, Tool.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Tool
     */
    @WebEndpoint(name = "ToolPort")
    public Tool getToolPort(WebServiceFeature... features) {
        return super.getPort(ToolPort, Tool.class, features);
    }

}