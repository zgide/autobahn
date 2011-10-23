
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-09-05T13:54:30.996+02:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "InvalidTopicExpressionFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class InvalidTopicExpressionFault extends Exception {
    
    private org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType invalidTopicExpressionFault;

    public InvalidTopicExpressionFault() {
        super();
    }
    
    public InvalidTopicExpressionFault(String message) {
        super(message);
    }
    
    public InvalidTopicExpressionFault(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTopicExpressionFault(String message, org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType invalidTopicExpressionFault) {
        super(message);
        this.invalidTopicExpressionFault = invalidTopicExpressionFault;
    }

    public InvalidTopicExpressionFault(String message, org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType invalidTopicExpressionFault, Throwable cause) {
        super(message, cause);
        this.invalidTopicExpressionFault = invalidTopicExpressionFault;
    }

    public org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType getFaultInfo() {
        return this.invalidTopicExpressionFault;
    }
}
