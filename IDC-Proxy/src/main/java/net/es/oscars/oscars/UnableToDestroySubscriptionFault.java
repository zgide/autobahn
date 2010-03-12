
package net.es.oscars.oscars;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebFault(name = "UnableToDestroySubscriptionFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")

public class UnableToDestroySubscriptionFault extends Exception {
    public static final long serialVersionUID = 20100212154406L;
    
    private org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType unableToDestroySubscriptionFault;

    public UnableToDestroySubscriptionFault() {
        super();
    }
    
    public UnableToDestroySubscriptionFault(String message) {
        super(message);
    }
    
    public UnableToDestroySubscriptionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToDestroySubscriptionFault(String message, org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType unableToDestroySubscriptionFault) {
        super(message);
        this.unableToDestroySubscriptionFault = unableToDestroySubscriptionFault;
    }

    public UnableToDestroySubscriptionFault(String message, org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType unableToDestroySubscriptionFault, Throwable cause) {
        super(message, cause);
        this.unableToDestroySubscriptionFault = unableToDestroySubscriptionFault;
    }

    public org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType getFaultInfo() {
        return this.unableToDestroySubscriptionFault;
    }
}