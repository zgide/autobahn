
/**
 * InvalidTopicExpressionFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package net.geant2.jra3.oscars.notify;

public class InvalidTopicExpressionFault extends java.lang.Exception{
    
    private org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault faultMessage;
    
    public InvalidTopicExpressionFault() {
        super("InvalidTopicExpressionFault");
    }
           
    public InvalidTopicExpressionFault(java.lang.String s) {
       super(s);
    }
    
    public InvalidTopicExpressionFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault msg){
       faultMessage = msg;
    }
    
    public org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFault getFaultMessage(){
       return faultMessage;
    }
}
    