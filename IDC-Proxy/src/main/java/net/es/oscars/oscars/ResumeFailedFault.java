
package net.es.oscars.oscars;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebFault(name = "ResumeFailedFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")

public class ResumeFailedFault extends Exception {
    public static final long serialVersionUID = 20100212154406L;
    
    private org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType resumeFailedFault;

    public ResumeFailedFault() {
        super();
    }
    
    public ResumeFailedFault(String message) {
        super(message);
    }
    
    public ResumeFailedFault(String message, Throwable cause) {
        super(message, cause);
    }

    public ResumeFailedFault(String message, org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType resumeFailedFault) {
        super(message);
        this.resumeFailedFault = resumeFailedFault;
    }

    public ResumeFailedFault(String message, org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType resumeFailedFault, Throwable cause) {
        super(message, cause);
        this.resumeFailedFault = resumeFailedFault;
    }

    public org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType getFaultInfo() {
        return this.resumeFailedFault;
    }
}
