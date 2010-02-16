
package net.geant2.jra3.gui;

import javax.xml.ws.Endpoint;

import net.geant.edugain.WSSecurity;

/**
 * This class was generated by Apache CXF (incubator) 2.0.4-incubator
 * Mon Mar 02 13:20:38 CET 2009
 * Generated source version: 2.0.4-incubator
 * 
 */
 
public class Gui_GuiPort_Server{

    protected Gui_GuiPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new GuiImpl();
        String address = "http://localhost:9090/hello";
        Endpoint point = Endpoint.publish(address, implementor);

    }
    
    public static void main(String args[]) throws Exception { 
        new Gui_GuiPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exitting");
        System.exit(0);
    }
}
