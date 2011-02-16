
package net.geant.autobahn.idm2dm;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

import net.geant.autobahn.aai.AAIException;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 15 15:30:51 CET 2008
 * Generated source version: 2.0.3-incubator
 * 
 */

public final class Idm2Dm_Idm2DmPort_Client {

    private static final QName SERVICE_NAME = new QName("http://idm2dm.jra3.geant2.net/", "Idm2DmService");

    private Idm2Dm_Idm2DmPort_Client() {
    }

    public static void main(String args[]) throws Exception {

        if (args.length == 0) { 
            System.out.println("please specify wsdl");
            System.exit(1); 
        }
        URL wsdlURL = null;
        File wsdlFile = new File(args[0]);
        try {
            if (wsdlFile.exists()) {
                wsdlURL = wsdlFile.toURL();
            } else {
                wsdlURL = new URL(args[0]);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
      
        Idm2DmService ss = new Idm2DmService(wsdlURL, SERVICE_NAME);
        Idm2Dm port = ss.getIdm2DmPort();  
        
        {
        System.out.println("Invoking addReservation...");
        java.lang.String _addReservation_resID = "";
        net.geant.autobahn.network.Link[] _addReservation_links = null;
        net.geant.autobahn.reservation.ReservationParams _addReservation_params = null;
        try {
            port.addReservation(_addReservation_resID, _addReservation_links, _addReservation_params);

        } catch (ConstraintsAlreadyUsedException e) { 
            System.out.println("Expected exception: ConstraintsAlreadyUsedException_Exception has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking checkResources...");
        net.geant.autobahn.network.Link[] _checkResources_arg0 = null;
        net.geant.autobahn.reservation.ReservationParams _checkResources_arg1 = null;
        try {
            net.geant.autobahn.constraints.DomainConstraints[] _checkResources__return = port.checkResources(_checkResources_arg0, _checkResources_arg1);
            System.out.println("checkResources.result=" + _checkResources__return);

        } catch (OversubscribedException e) { 
            System.out.println("Expected exception: OversubscribedException_Exception has occurred.");
            System.out.println(e.toString());
        }
        catch (AAIException e) { 
            System.out.println("Expected exception: AAIException has occurred.");
            System.out.println(e.toString());
        }
            }
        {
/*        System.out.println("Invoking getAbstractLinks...");
        net.geant.autobahn.network.Link[] _getAbstractLinks__return = port.getAbstractLinks();
        System.out.println("getAbstractLinks.result=" + _getAbstractLinks__return);
*/

        }
        {
        System.out.println("Invoking removeReservation...");
        java.lang.String _removeReservation_resID = "";
        port.removeReservation(_removeReservation_resID);


        }

        System.exit(0);
    }

}
