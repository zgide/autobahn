
package net.geant.autobahn.idcp;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.4.2
 * 2011-08-30T10:58:32.426+02:00
 * Generated source version: 2.4.2
 */

@WebFault(name = "TopicExpressionDialectUnknownFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")
public class TopicExpressionDialectUnknownFault extends Exception {
    
    private org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault;

    public TopicExpressionDialectUnknownFault() {
        super();
    }
    
    public TopicExpressionDialectUnknownFault(String message) {
        super(message);
    }
    
    public TopicExpressionDialectUnknownFault(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicExpressionDialectUnknownFault(String message, org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault) {
        super(message);
        this.topicExpressionDialectUnknownFault = topicExpressionDialectUnknownFault;
    }

    public TopicExpressionDialectUnknownFault(String message, org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType topicExpressionDialectUnknownFault, Throwable cause) {
        super(message, cause);
        this.topicExpressionDialectUnknownFault = topicExpressionDialectUnknownFault;
    }

    public org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType getFaultInfo() {
        return this.topicExpressionDialectUnknownFault;
    }
}