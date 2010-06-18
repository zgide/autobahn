
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant.autobahn.tool;

import net.geant.autobahn.tool.pionier.*;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.5
 * Fri Jun 18 15:05:42 CEST 2010
 * Generated source version: 2.2.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "Tool",
                      portName = "ToolPort",
                      targetNamespace = "http://tool.autobahn.geant.net/",
                      wsdlLocation = "file:src/main/resources/wsdl/tool.wsdl",
                      endpointInterface = "net.geant.autobahn.tool.Tool")
                      
public class ToolImpl implements Tool {

    private static final Logger LOG = Logger.getLogger(ToolImpl.class.getName());

    /* (non-Javadoc)
     * @see net.geant.autobahn.tool.Tool#addReservation(java.lang.String  resID ,)java.util.List<net.geant.autobahn.intradomain.common.GenericLink>  links ,)net.geant.autobahn.reservation.ReservationParams  params )*
     */
    public void addReservation(java.lang.String resID,java.util.List<net.geant.autobahn.intradomain.common.GenericLink> links,net.geant.autobahn.reservation.ReservationParams params) throws ResourceNotFoundException_Exception , RequestException_Exception , AAIException_Exception , SystemException_Exception    { 
        LOG.info("Executing operation addReservation");
        System.out.println(resID);
        
        try {
        	
        	ReservationParameters rp = DemoReservation.GetReservationParametersSC07();
        	// create VLL
        	FoundryXMRConfigurator configurator = new FoundryXMRConfigurator();
        	configurator.addReservation(rp);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException_Exception(ex.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.tool.Tool#removeReservation(java.lang.String  resID ,)java.util.List<net.geant.autobahn.intradomain.common.GenericLink>  links ,)net.geant.autobahn.reservation.ReservationParams  params )*
     */
    public void removeReservation(java.lang.String resID,java.util.List<net.geant.autobahn.intradomain.common.GenericLink> links,net.geant.autobahn.reservation.ReservationParams params) throws ReservationNotFoundException_Exception , RequestException_Exception , AAIException_Exception , SystemException_Exception    { 
        LOG.info("Executing operation removeReservation");
        System.out.println(resID);
        
        try {
        	
        	ReservationParameters rp = DemoReservation.GetReservationParametersSC07();
        	// remove VLL
        	FoundryXMRConfigurator configurator = new FoundryXMRConfigurator();
        	configurator.remReservation(rp);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SystemException_Exception(ex.getMessage());
        }
    }
}
