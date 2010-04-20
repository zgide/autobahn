
package net.geant.autobahn.useraccesspoint.callback;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Mon Aug 25 16:57:28 CEST 2008
 * Generated source version: 2.0.3-incubator
 * 
 */
 
public class UapCallback_UapCallbackPort_Server{

    protected UapCallback_UapCallbackPort_Server(String port) throws Exception {
        System.out.println("Starting Server");
        Object implementor = new UapCallbackImpl();
        String address = "http://localhost:" + port + "/callback";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws Exception {
    	System.out.println("Using port: " + args[0]);
    	
        new UapCallback_UapCallbackPort_Server(args[0]);
        System.out.println("Server ready..."); 
        
        Thread.sleep(Long.MAX_VALUE);
        
        //Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exitting");
        System.exit(0);
    }
}
