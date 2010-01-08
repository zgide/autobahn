
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant2.jra3.proxy;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Jan 08 10:34:45 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@javax.jws.WebService(name = "Proxy", serviceName = "ProxyService",
                      portName = "ProxyPort",
                      targetNamespace = "http://proxy.jra3.geant2.net/", 
                      wsdlLocation = "file:./src/main/resources/wsdl/proxy.wsdl" ,
		      endpointInterface = "net.geant2.jra3.proxy.Proxy")
                      
public class ProxyImpl implements Proxy {

    private static final Logger LOG = Logger.getLogger(ProxyImpl.class.getName());

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#createReservation(net.geant2.jra3.proxy.ProxyInfo  proxyInfo ,)net.geant2.jra3.proxy.ReservationParams  reservation )*
     */
    public void createReservation(net.geant2.jra3.proxy.ProxyInfo proxyInfo,net.geant2.jra3.proxy.ReservationParams reservation) throws ProxyException_Exception    { 
        LOG.info("Executing operation createReservation");
        System.out.println(proxyInfo);
        System.out.println(reservation);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ProxyException_Exception("ProxyException_Exception...");
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#queryReservation(java.lang.String  resID )*
     */
    public net.geant2.jra3.proxy.Reservation queryReservation(java.lang.String resID) throws ProxyException_Exception    { 
        LOG.info("Executing operation queryReservation");
        System.out.println(resID);
        try {
            net.geant2.jra3.proxy.Reservation _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ProxyException_Exception("ProxyException_Exception...");
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#cancelReservation(net.geant2.jra3.proxy.ProxyInfo  proxyInfo ,)java.lang.String  resID )*
     */
    public void cancelReservation(net.geant2.jra3.proxy.ProxyInfo proxyInfo,java.lang.String resID) throws ProxyException_Exception    { 
        LOG.info("Executing operation cancelReservation");
        System.out.println(proxyInfo);
        System.out.println(resID);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ProxyException_Exception("ProxyException_Exception...");
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#getProxyInfo(*
     */
    public net.geant2.jra3.proxy.ProxyInfo getProxyInfo() { 
        LOG.info("Executing operation getProxyInfo");
        try {
            net.geant2.jra3.proxy.ProxyInfo _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#getTopology(net.geant2.jra3.proxy.ProxyInfo  proxyInfo )*
     */
    public java.util.List<net.geant2.jra3.proxy.Link> getTopology(net.geant2.jra3.proxy.ProxyInfo proxyInfo) { 
        LOG.info("Executing operation getTopology");
        System.out.println(proxyInfo);
        try {
            java.util.List<net.geant2.jra3.proxy.Link> _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#listReservations(*
     */
    public java.util.List<net.geant2.jra3.proxy.Reservation> listReservations() { 
        LOG.info("Executing operation listReservations");
        try {
            java.util.List<net.geant2.jra3.proxy.Reservation> _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.proxy.Proxy#forward(net.geant2.jra3.proxy.Reservation  reservation )*
     */
    public void forward(net.geant2.jra3.proxy.Reservation reservation) throws ProxyException_Exception    { 
        LOG.info("Executing operation forward");
        System.out.println(reservation);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new ProxyException_Exception("ProxyException_Exception...");
    }

}
