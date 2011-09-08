
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-08-30T10:58:32.421+02:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "InvalidFilterFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class InvalidFilterFault extends Exception {
    
    private org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType invalidFilterFault;

    public InvalidFilterFault() {
        super();
    }
    
    public InvalidFilterFault(String message) {
        super(message);
    }
    
    public InvalidFilterFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFilterFault(String message, org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType invalidFilterFault) {
        super(message);
        this.invalidFilterFault = invalidFilterFault;
    }

    public InvalidFilterFault(String message, org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType invalidFilterFault, Throwable cause) {
        super(message, cause);
        this.invalidFilterFault = invalidFilterFault;
    }

    public org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType getFaultInfo() {
        return this.invalidFilterFault;
    }
}