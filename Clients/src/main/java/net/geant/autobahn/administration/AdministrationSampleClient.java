package net.geant.autobahn.administration;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
import net.geant.autobahn.administration.Administration;

import org.apache.cxf.annotations.DataBinding;

import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.intradomain.common.GenericLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

@DataBinding(org.apache.cxf.aegis.databinding.AegisDatabinding.class)
public class AdministrationSampleClient {

    private static final QName SERVICE_NAME = new QName("http://administration.autobahn.geant.net/", "AdministrationService");
    private final static QName AdministrationPort = new QName("http://administration.autobahn.geant.net/", "AdministrationPort");
    
    private Administration adm = null;
    
    public AdministrationSampleClient(String target) {
        Service service = Service.create(SERVICE_NAME);
        service.addPort(AdministrationPort, SOAPBinding.SOAP11HTTP_BINDING, target);

        adm = service.getPort(AdministrationPort, Administration.class);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {

        System.out.println("Give IDM to connect to:");
        byte byteStr[] = new byte[50];
        System.in.read(byteStr);
        String idm = (new String(byteStr).trim());

        System.out.println("Give IDM port:");
        byteStr = new byte[50];
        System.in.read(byteStr);
        String port = (new String(byteStr).trim());
        
        if (idm == null || idm.equals("")) {
            idm = "150.140.8.13";
        }
        if (port == null || port.equals("")) {
            port = "8080";
        }
        
        AdministrationSampleClient instance = new AdministrationSampleClient("http://"+idm+":"+port+"/autobahn/administration");
        System.out.println("Connecting to "+idm+":"+port);
        /*
        System.out.println("\n---getLog():");
        String ad_log = instance.adm.getLog(true);
        System.out.println(ad_log);
        
        System.out.println("\n---getServices():");
        List<ServiceType> serv = instance.adm.getServices();
        if (serv != null) {
            for (int i=0; i<serv.size(); i++) {
                if (serv.get(i) != null) {
                    System.out.println(i+": "+serv.get(i).getBodID());
                }
            }
        }
                
        System.out.println("\n---getProperties():");
        List<KeyValue> props = instance.adm.getProperties();
        if (props != null) {
            for (int i=0; i<props.size(); i++) {
                if (props.get(i) != null) {
                    System.out.println(i+": "+props.get(i).getKey()+"="+props.get(i).getValue());
                }
            }
        }
        
        System.out.println("\n---getStatistics():");
        StatisticsType stype = instance.adm.getStatistics(true);
        if (stype != null) {
            System.out.println("getAverageInter:"+stype.getAverageInter()+
                    "\ngetAverageIntra:"+stype.getAverageIntra());
            if (stype.getInter() != null) {
                System.out.println("Inter length:"+stype.getInter().size());
            }
            if (stype.getIntra() != null) {
                System.out.println("Intra length:"+stype.getIntra().size());
            }
        }
        
        System.out.println("\n---getTopology():");
        List<Link> topo = instance.adm.getTopology();
        if (topo != null) {
            for (int i=0; i<topo.size(); i++) {
                System.out.println(i+": "+topo.get(i));
            }
        }
        
        System.out.println("\n---getStatus():");
        Status stat = instance.adm.getStatus();
        if (stat != null) {
            System.out.println("getDomain:"+stat.getDomain()+
                    "\ngetLatitude:"+stat.getLatitude()+
                    "\ngetLongitude:"+stat.getLongitude());
            if (stat.getNeighbors() != null) {
                System.out.println("getNeighbors length:"+stat.getNeighbors().size());
            }
        }
        */
        
        System.out.println("\n---getProperties():");
        List<KeyValue> props = instance.adm.getProperties();
        if (props != null) {
            for (int i=0; i<props.size(); i++) {
                if (props.get(i) != null) {
                    System.out.println(i+": "+props.get(i).getKey()+"="+props.get(i).getValue());
                }
            }
        }
        
        System.out.println("\n---NOC PANEL WEB SERVICES---");
        
        System.out.println("\n---getIntradomainPaths");
        HashMap<String, IntradomainPath> intraPaths = instance.adm.getIntradomainPaths();
        //Test intraPaths = instance.adm.getIntradomainPaths();
        //for(Entry<String, IntradomainPath> entry : intraPaths.getTest().entrySet()){
        for(Entry<String, IntradomainPath> entry : intraPaths.entrySet()){
            System.out.println("Key: " + entry.getKey() + " --- " + "Value: " + entry.getValue().getEgressConstraints().getConstraintID());
        }
        
        
        System.out.println("\n---getIntradomainReservationParams");
        HashMap<String, IntradomainReservation> resvParams = instance.adm.getIntradomainReservationParams();
        for(Entry<String, IntradomainReservation> entry : resvParams.entrySet()){
            System.out.println("Key: " + entry.getKey() + " --- " + "Value: " + entry.getValue().getReservedPath().getPathId());
        }
        
        
        System.out.println("\n---getIntradomainCalendarsUsage");
        //HashMap<GenericLink, HashMap<Calendar, Long>> calendarsUsage = instance.adm.getIntradomainCalendarsUsage(null);
        IntradomainPath path = new IntradomainPath();
        List<GenericLink> links = new ArrayList<GenericLink>();
        GenericLink link = new GenericLink();
        
        link.setLinkId(666);
        links.add(link);
        path.setLinks(links);
        
        //HashMap<GenericLink, HashMap<Calendar, Long>> calendarsUsage = instance.adm.getIntradomainCalendarsUsage(path);
        HashMap<GenericLink, TreeMap<Calendar, Long>> calendarsUsage = instance.adm.getIntradomainCalendarsUsage(null);
        //HashMap<GenericLink, Long> calendarsUsage = instance.adm.getIntradomainCalendarsUsage(null);
        
        for(Entry<GenericLink, TreeMap<Calendar, Long>> entry : calendarsUsage.entrySet()){
        //for(Entry<GenericLink, Long> entry : calendarsUsage.entrySet()){
            for(Entry<Calendar, Long> entry2 : entry.getValue().entrySet()) {
                System.out.println("Key: " + entry.getKey().getLinkId() + " --- " + "Value: " + entry2.getValue());
            }            
        }
              
        System.out.println("\n---restart():");
        instance.adm.restart();
        
        System.out.println("\n---setProperties():");
        instance.adm.setProperties(null);
        
        System.exit(0);
    }  
    
}

