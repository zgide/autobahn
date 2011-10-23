
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-09-05T13:54:31.291+02:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "UnableToDestroySubscriptionFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class UnableToDestroySubscriptionFault extends Exception {
    
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
