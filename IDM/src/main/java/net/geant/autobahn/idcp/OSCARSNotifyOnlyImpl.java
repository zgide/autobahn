
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant.autobahn.idcp;

import org.apache.log4j.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-10-04T15:26:52.874+02:00
 * Generated source version: 2.4.2
 * 
 */

@javax.jws.WebService(
                      serviceName = "OSCARSNotifyOnlyService",
                      portName = "OSCARSNotifyOnly",
                      targetNamespace = "http://oscars.es.net/OSCARS",
                      wsdlLocation = "file:etc/wsdl/IDCP/OSCARS-NotifyOnly.wsdl",
                      endpointInterface = "net.geant.autobahn.idcp.OSCARSNotifyOnly")
                      
public class OSCARSNotifyOnlyImpl implements OSCARSNotifyOnly {

    private static Logger log = Logger.getLogger(OSCARSNotifyOnlyImpl.class);

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARSNotifyOnly#notify(org.oasis_open.docs.wsn.b_2.Notify  notify )*
     */
    public void notify(org.oasis_open.docs.wsn.b_2.Notify notify) {
    	
    	try {
        	
        	EventContent event = notify.getNotificationMessage().get(0).getMessage().getEvent().get(0);
        	if (event == null) {
        		log.info("notify message without event content");
        		return;
        	}
        	if (event.getResDetails() == null) {
        		log.info("event content without reservation details");
        		return;
        	}
        	final String resId = event.getResDetails().getGlobalReservationId();
        	final String message = event.getType();
        	IdcpReservation res = IdcpReservation.getReservation(resId);
        	if (res == null) {
        		log.info("notification - reservation " + resId + " not found");
        	} else {
        		log.info("notification - setting message " + message + " for reservation " + resId);
        		res.setMessage(message);
        	}
        } catch (Exception e) {
        	log.info("notification error - " + e.getMessage());
        }
    }
}
