
package net.es.oscars.oscars;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebFault(name = "TopicExpressionDialectUnknownFault", targetNamespace = "http://docs.oasis-open.org/wsn/b-2")

public class TopicExpressionDialectUnknownFault extends Exception {
    public static final long serialVersionUID = 20100212154406L;
    
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
