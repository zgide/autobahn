
package net.geant.autobahn.idcp.notify;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.6
 * Fri Sep 10 08:55:55 CEST 2010
 * Generated source version: 2.2.6
 * 
 */

@WebFault(name = "AAAFault", targetNamespace = "http://oscars.es.net/OSCARS")
public class AAAFaultMessage extends Exception {
    public static final long serialVersionUID = 20100910085555L;
    
    private net.geant.autobahn.idcp.notify.AAAFault aaaFault;

    public AAAFaultMessage() {
        super();
    }
    
    public AAAFaultMessage(String message) {
        super(message);
    }
    
    public AAAFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public AAAFaultMessage(String message, net.geant.autobahn.idcp.notify.AAAFault aaaFault) {
        super(message);
        this.aaaFault = aaaFault;
    }

    public AAAFaultMessage(String message, net.geant.autobahn.idcp.notify.AAAFault aaaFault, Throwable cause) {
        super(message, cause);
        this.aaaFault = aaaFault;
    }

    public net.geant.autobahn.idcp.notify.AAAFault getFaultInfo() {
        return this.aaaFault;
    }
}
