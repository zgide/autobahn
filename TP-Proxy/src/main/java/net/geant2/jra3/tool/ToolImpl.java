
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant2.jra3.tool;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.9
 * Mon Feb 21 17:26:54 EET 2011
 * Generated source version: 2.2.9
 * 
 */

@javax.jws.WebService(
                      serviceName = "ToolService",
                      portName = "ToolPort",
                      targetNamespace = "http://tool.jra3.geant2.net/",
                      wsdlLocation = "file:/D:/EclipseWorkspaces_mvn_new/autobahn/TP-Proxy/src/main/resources/wsdl/heanet.wsdl",
                      endpointInterface = "net.geant2.jra3.tool.Tool")
                      
public class ToolImpl implements Tool {

    private static final Logger LOG = Logger.getLogger(ToolImpl.class.getName());

    /* (non-Javadoc)
     * @see net.geant2.jra3.tool.Tool#removeReservation(java.lang.String  resID ,)java.util.List<net.geant2.jra3.intradomain.common.GenericLink>  links ,)net.geant2.jra3.reservation.ReservationParams  params )*
     */
    public void removeReservation(java.lang.String resID,java.util.List<net.geant2.jra3.intradomain.common.GenericLink> links,net.geant2.jra3.reservation.ReservationParams params) throws RequestException_Exception , SystemException_Exception , ReservationNotFoundException_Exception , AAIException_Exception    { 
        LOG.info("Executing operation removeReservation");
        System.out.println(resID);
        System.out.println(links);
        System.out.println(params);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RequestException_Exception("RequestException...");
        //throw new SystemException_Exception("SystemException...");
        //throw new ReservationNotFoundException_Exception("ReservationNotFoundException...");
        //throw new AAIException_Exception("AAIException...");
    }

    /* (non-Javadoc)
     * @see net.geant2.jra3.tool.Tool#addReservation(java.lang.String  resID ,)java.util.List<net.geant2.jra3.intradomain.common.GenericLink>  links ,)net.geant2.jra3.reservation.ReservationParams  params )*
     */
    public void addReservation(java.lang.String resID,java.util.List<net.geant2.jra3.intradomain.common.GenericLink> links,net.geant2.jra3.reservation.ReservationParams params) throws RequestException_Exception , SystemException_Exception , ResourceNotFoundException_Exception , AAIException_Exception    { 
        LOG.info("Executing operation addReservation");
        System.out.println(resID);
        System.out.println(links);
        System.out.println(params);
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        //throw new RequestException_Exception("RequestException...");
        //throw new SystemException_Exception("SystemException...");
        //throw new ResourceNotFoundException_Exception("ResourceNotFoundException...");
        //throw new AAIException_Exception("AAIException...");
    }

}