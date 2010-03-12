
package net.es.oscars.oscars;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebFault(name = "AAAFault", targetNamespace = "http://oscars.es.net/OSCARS")

public class AAAFaultMessage extends Exception {
    public static final long serialVersionUID = 20100212154406L;
    
    private net.es.oscars.oscars.AAAFault aaaFault;

    public AAAFaultMessage() {
        super();
    }
    
    public AAAFaultMessage(String message) {
        super(message);
    }
    
    public AAAFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public AAAFaultMessage(String message, net.es.oscars.oscars.AAAFault aaaFault) {
        super(message);
        this.aaaFault = aaaFault;
    }

    public AAAFaultMessage(String message, net.es.oscars.oscars.AAAFault aaaFault, Throwable cause) {
        super(message, cause);
        this.aaaFault = aaaFault;
    }

    public net.es.oscars.oscars.AAAFault getFaultInfo() {
        return this.aaaFault;
    }
}
