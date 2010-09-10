
package net.geant.autobahn.idcp.notify;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.6
 * Fri Sep 10 08:55:55 CEST 2010
 * Generated source version: 2.2.6
 * 
 */

@WebFault(name = "PauseFailedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class PauseFailedFault extends Exception {
    public static final long serialVersionUID = 20100910085555L;
    
    private org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault;

    public PauseFailedFault() {
        super();
    }
    
    public PauseFailedFault(String message) {
        super(message);
    }
    
    public PauseFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public PauseFailedFault(String message, org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault) {
        super(message);
        this.pauseFailedFault = pauseFailedFault;
    }

    public PauseFailedFault(String message, org.oasis_open.docs.wsn.b_2.PauseFailedFaultType pauseFailedFault, Throwable cause) {
        super(message, cause);
        this.pauseFailedFault = pauseFailedFault;
    }

    public org.oasis_open.docs.wsn.b_2.PauseFailedFaultType getFaultInfo() {
        return this.pauseFailedFault;
    }
}
