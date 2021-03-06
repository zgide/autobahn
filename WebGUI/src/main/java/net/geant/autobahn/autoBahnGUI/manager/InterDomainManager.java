package net.geant.autobahn.autoBahnGUI.manager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.administration.Administration;
import net.geant.autobahn.administration.AdministrationException;
import net.geant.autobahn.administration.AdministrationService;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.StatisticsType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.intradomain.IntradomainPath;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.useraccesspoint.ModifyRequest;
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.ServiceResponse;
import net.geant.autobahn.useraccesspoint.UserAccessPoint;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
import net.geant.autobahn.useraccesspoint.UserAccessPointService;

import org.apache.log4j.Logger;

/**
 * Models AutoBAHN InterDomainManager (IDM) 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class InterDomainManager implements UserAccessPoint, Administration {

    /**
     * Identifier
     */
    private String identifier;

    /**
     * Web services address prefix
     */
    private String url;

    /**
     * UserAccessPoint web service interface
     */
    private UserAccessPoint userAccessPoint;

    /**
     * Administration web service interface
     */
    private Administration administration;

    /**
     * Services submitted in IDM
     */
    public Map<String, ServiceType> services = Collections.synchronizedMap(new HashMap<String, ServiceType>());

    /**
     * IDM status
     */
    private Status status;

    /**
     * Time of last update of IDM status
     */
    private long lastStatusUpdateInMillis;

    /**
     * IDM managed port types
     */
    private List<PortType> ports;

    /**
     * IDM logs
     */
    private String logs;

    /**
     * IDM statistical data
     */
    private StatisticsType statistics;
    
    /**
     * Triggers for enabling/disabling the use of cached objects for intradomain information (NOCPanel) 
     */
    private boolean intraRsvCacheIsUptodate = false;
    private boolean intraPathsCacheIsUptodate = false;
    private boolean intraCalendarsCacheIsUptodate = false;

    /**
     * Caching object for storing intradomain calendar information for NOC Panel
     */
    public Map<GenericLink, TreeMap<Calendar, Long>> intraCalendarsUsage = Collections.synchronizedMap(new HashMap<GenericLink, TreeMap<Calendar, Long>>());

    /**
     * Caching object for storing intradomain paths information for NOC Panel
     */
    public Map<String, IntradomainPath> intradomainPaths = Collections.synchronizedMap(new HashMap<String, IntradomainPath>());

    /**
     * Caching object for storing intradomain reservation parameters for NOC Panel
     */
    public Map<String, IntradomainReservation> intraRsvParams = Collections.synchronizedMap(new HashMap<String, IntradomainReservation>());

    /**
     * Logs information
     */
    public static final Logger logger = Logger.getLogger("IDMsManager");

    /**
     * Creates InterDomainManager object
     * 
     * @param indentifier
     *            identifier of the InterDomainManager
     * @param url
     *            web services address prefix
     */
    public InterDomainManager(String identifier, String url) {
        this.identifier = identifier;
        this.url = url;
        connect(url);
        getServices(true);
    }

    /**
     * Establish connections with IDM UserAccessPoint and Administration web
     * service interface
     * 
     * @param url
     *            web services address prefix
     */
    public void connect(String url) {
        userAccessPoint = connectUserAccessPoint(url);
        administration = connectAdministration(url);
    }

    /**
     * Establish connection with IDM Administration web service interface
     * 
     * @param url
     *            web services address prefix
     * @return connected Administration web service interface
     */
    public static Administration connectAdministration(String url) {
        String address = url + "/administration";
        AdministrationService service = new AdministrationService(address);
        Administration admin = service.getAdministrationPort();
        return admin;
    }

    /**
     * Establish connection with IDM UserAccessPoint web service interface
     * 
     * @param url
     *            web services address prefix
     * @return connected UserAccessPoint web service interface
     */
    public static UserAccessPoint connectUserAccessPoint(String url) {
        String address = url + "/uap";
        UserAccessPointService service = new UserAccessPointService(address);
        UserAccessPoint admin = service.getUserAccessPointPort();
        return admin;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#cancelService(java.lang.String)
     */
    @Override
    public void cancelService(String serviceID) throws UserAccessPointException {
        if (isUserAccessPointConnected()) {
            userAccessPoint.cancelService(serviceID);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#queryService(java.lang.String)
     */
    @Override
    public ServiceResponse queryService(String serviceID)
            throws UserAccessPointException {
        if (isUserAccessPointConnected()) {
            return userAccessPoint.queryService(serviceID);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#submitService(net.geant.autobahn.useraccesspoint.ServiceRequest)
     */
    @Override
    public String submitService(ServiceRequest request)
            throws UserAccessPointException {
        if (isUserAccessPointConnected()) {
            return userAccessPoint.submitService(request);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getLog(boolean)
     */
    @Override
    public String getLog(boolean all) {
        if (isAdmnistrationConnected()) {
            try {
                return administration.getLog(all);
            } catch (Exception e) {
                logger.error("Cannot reservation from idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getStatistics(boolean)
     */
    @Override
    public StatisticsType getStatistics(boolean all) {
        if (isAdmnistrationConnected()) {
            try {
                return administration.getStatistics(all);
            } catch (Exception e) {
                logger.error("Cannot retrieve statistics from idm:" + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getProperties()
     */
    @Override
    public List<KeyValue> getProperties() {
        if (isAdmnistrationConnected()) {
            try {
                return administration.getProperties();
            } catch (Exception e) {
                logger.error("Cannot reservation from idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.geant.autobahn.administration.Administration#getAccessPolicy()
     */
    public AccessPolicy getAccessPolicy() {
        if (isAdmnistrationConnected()) {
            try {
                return administration.getAccessPolicy();
            } catch (Exception e) {
                logger.error("Cannot get access policy from idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getReservation(java.lang.String)
     */
    @Override
    public ReservationType getReservation(String resID) {
        if (isAdmnistrationConnected()) {
            try {
                return administration.getReservation(resID);
            } catch (Exception e) {
                logger.error("Cannot reservation from idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getTopology()
     */
    @Override
    public List<Link> getTopology() {

        if (isAdmnistrationConnected()) {
            try {
                return administration.getTopology();
            } catch (Exception e) {
                logger.error("Cannot get topology from idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#setProperties(java.util.List)
     */
    @Override
    public void setProperties(List<KeyValue> properties) {
        if (isAdmnistrationConnected())
            try {
                administration.setProperties(properties);
            } catch (Exception e) {
                logger.error("Cannot set properties for idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.geant.autobahn.administration.Administration#setAccessPolicy(net.
     * geant.autobahn.aai.AccessPolicy )
     */
    public void setAccessPolicy(AccessPolicy acp) {
        if (isAdmnistrationConnected()) {
            try {
                administration.setAccessPolicy(acp);
            } catch (Exception e) {
                logger.error("Cannot set access policy for idm:"
                        + e.getClass().getName() + ":" + e.getMessage());
            }
        }
    }

    /**
     * Gets identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier
     * 
     * @param identifier
     *            identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gets web service address prefix
     * 
     * @return web service address prefix
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets web service address prefix
     * 
     * @param web
     *            service address prefix
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets IDM status
     * 
     * @return web IDM status
     */
    public void setStatus(Status status) {
        if (status != null) {
            this.status = status;
            lastStatusUpdateInMillis = System.currentTimeMillis();
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getService(java.lang.String)
     */
    @Override
    public ServiceType getService(String name) {
        ServiceType service = services.get(name);
        if (service != null)
            return service;
        try {
            if (isAdmnistrationConnected()) {
                service = administration.getService(name);
                if (service != null) {
                    services.put(service.getBodID(), service);
                    return service;
                }
            }
        } catch (Exception e) {
            logger.error("Cannot get service from idm:"
                    + e.getClass().getName() + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * Get service submitted in IDM
     * 
     * @param clearOld
     *            identifies if cached list should be clear before get new list
     * @return Service object lists
     */
    public List<ServiceType> getServices(boolean clearOld) {
        List<ServiceType> serv = new ArrayList<ServiceType>();
        if (clearOld) {
            if (services == null)
                services = new HashMap<String, ServiceType>();
            else
                services.clear();
            try {
                List<ServiceType> servicesIdm = administration.getServices();
                if (servicesIdm == null)
                    return serv;
                for (ServiceType service : servicesIdm) {
                    services.put(service.getBodID(), service);
                }
            } catch (Exception e) {
                logger.error("Problem with getting services from idm:"
                        + identifier);
            }
        }

        ServiceType service;
        for (String name : services.keySet()) {
            service = services.get(name);
            if (service == null) {
                services.remove(name);
            }
            serv.add(service);
        }

        return serv;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getServices()
     */
    @Override
    public List<ServiceType> getServices() {
        if (!isAdmnistrationConnected())
            return null;
        List<ServiceType> servicesIdm = null;
        try {
            servicesIdm = administration.getServices();
        } catch (Exception e) {
            logger.error("Cannot get services from:" + identifier + ":"
                    + e.getClass().getName() + ":" + e.getMessage());
        }
        return servicesIdm;
    }

    /**
     * Gets logs from IDM
     * 
     * @param refresh
     * @param all
     * @return
     */
    public String getLog(boolean refresh, boolean all) {
        if (refresh) {
            if (isAdmnistrationConnected())
                try {
                    logs = administration.getLog(all);
                } catch (Exception e) {
                    return "Cannot retrieve logs. Cannot connect to IDM.";
                }
            else
                return null;
        }
        return logs;
    }

    /**
     * Gets statistics from IDM
     * 
     * @param refresh
     * @param all
     * @return
     */
    public StatisticsType getStatistics(boolean refresh, boolean all){
        if (refresh) {
            if (isAdmnistrationConnected()) {
                try {
                    statistics = administration.getStatistics(all);
                } catch (Exception e) {
                    logger.error("Cannot retrieve statistical data. Cannot connect to IDM " + 
                        identifier + " at url " + url);
                    return null;
                }
            }
            else {
                return null;
            }
        }
        return statistics;
    }

    /**
     * Check if UserAccessPoint web service interface is connected IDM
     * 
     * @return true if yes, if no tries to establish connection
     */
    public boolean isUserAccessPointConnected() {
        if (userAccessPoint == null) {
            connectUserAccessPoint(url + "/uap");
        }
        return userAccessPoint != null;
    }

    /**
     * Check if Administration web service interface is connected IDM
     * 
     * @return true if yes, if no tries to establish connection
     */
    public boolean isAdmnistrationConnected() {
        if (administration == null) {
            connectAdministration(url + "/administration");
        }
        return administration != null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.administration.Administration#getStatus()
     */
    @Override
    public Status getStatus() {
        return status;
    }

    /**
     * Gets the time in mili second when last update was received
     * 
     * @return last update time in mili second
     */
    public long getLastStatusUpdateInMillis() {
        return lastStatusUpdateInMillis;
    }

    /**
     * Sets the time in mili seconds
     * 
     * @param last
     *            update time in mili seconds
     */
    public void setLastStatusUpdateInMillis(long lastStatusUpdateInMillis) {
        this.lastStatusUpdateInMillis = lastStatusUpdateInMillis;
    }

    /**
     * Gets logs from IDM
     * 
     * @return logs form IDM
     */
    public String getLogs() {
        return logs;
    }

    /**
     * Sets logs from IDM
     * 
     * @param logs
     *            form IDM
     */
    public void setLogs(String logs) {
        this.logs = logs;
    }

    /**
     * Gets statistics from IDM
     * 
     * @return statistics from IDM
     */
    public StatisticsType getStatistics() {
        return statistics;
    }

    /**
     * Sets statistics from IDM
     * @param statistics  from IDM
     */
    public void setStatistics(StatisticsType statistics) {
        this.statistics = statistics;
    }

    /**
     * Sets ports managed by IDM
     * 
     * @ports
     */
    public void setPorts(List<PortType> ports) {
        this.ports = ports;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllDomains()
     */
    @Override
    public String[] getAllDomains() {
        try {
            if (isUserAccessPointConnected())
                return userAccessPoint.getAllDomains();
        } catch (Exception e ){
            logger.error ("Problem with getting domains:" + e.getClass().getName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllDomains_NonClient()
     */
    @Override
    public String[] getAllDomains_NonClient() {
        try {
            if (isUserAccessPointConnected())
                return userAccessPoint.getAllDomains_NonClient();
        } catch (Exception e ){
            logger.error ("Problem with getting non-client domains:" + e.getClass().getName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllLinks()
     */
    @Override
    public String[] getAllLinks() {
        try {
            if (isUserAccessPointConnected()) {
                return userAccessPoint.getAllLinks();
            }
        } catch (Exception e ){
            logger.error ("Problem with getting links:" + e.getClass().getName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllLinks_NonClient()
     */
    @Override
    public String[] getAllLinks_NonClient() {
        try {
            if (isUserAccessPointConnected())
                return userAccessPoint.getAllLinks_NonClient();
        } catch (Exception e ){
            logger.error ("Problem with getting non-client links:" + e.getClass().getName() + ":" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getAllClientPorts()
     */
    @Override
    public List<PortType> getAllClientPorts() throws UserAccessPointException {
        try {
            if (isUserAccessPointConnected()) {
                List<PortType> ptypes = userAccessPoint.getAllClientPorts();
                Collections.sort(ptypes);
                return ptypes;
            }
        } catch (Exception e) {
            logger.error("getAllClientPorts error:", e);
            // TODO: convert to spring strings
            throw new UserAccessPointException("errors.uap.notconnected");
        }

        throw new UserAccessPointException("errors.uap.notconnected");
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getIdcpPorts()
     */
    @Override
    public List<PortType> getIdcpPorts() {
        try {
            if (isUserAccessPointConnected()) {
                return userAccessPoint.getIdcpPorts();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#getDomainClientPorts()
     */
    @Override
    public HashMap<String, IntradomainPath> getIntradomainPaths() {
        if (!intraPathsCacheIsUptodate) {
            if (!isAdmnistrationConnected()) {
                return null;
            }
            
            try {
                intradomainPaths = administration.getIntradomainPaths();
                setIntraPathsCacheIsUptodate(true);
                
                return new HashMap<String, IntradomainPath>(intradomainPaths);
            } catch (Exception e) {
                logger.error("Cannot get intradomain paths from:" + identifier
                        + ":" + e.getClass().getName() + ":" + e.getMessage());
            }            
        } else {
            return new HashMap<String, IntradomainPath>(intradomainPaths);
        }
        
        return null;
    }

    @Override
    public HashMap<String, IntradomainReservation> getIntradomainReservationParams() {
        if (!intraRsvCacheIsUptodate) {
            if (!isAdmnistrationConnected()) {
                return null;
            }
            try {
                intraRsvParams = administration.getIntradomainReservationParams();
                setIntraRsvCacheIsUptodate(true);

                return new HashMap<String, IntradomainReservation>(intraRsvParams);
            } catch (Exception e) {
                logger.error("Cannot get intradomain reservations from:"
                        + identifier + ":" + e.getClass().getName() + ":"
                        + e.getMessage());
            }            
        } else {
            return new HashMap<String, IntradomainReservation>(intraRsvParams);
        }

        return null;
    }

    public HashMap<GenericLink, TreeMap<Calendar, Long>> getIntradomainCalendarsUsage(IntradomainPath path) {
        if (!intraCalendarsCacheIsUptodate) {
            if (!isAdmnistrationConnected()) {
                return null;
            }
            
            try {
                intraCalendarsUsage = administration.getIntradomainCalendarsUsage(path);
                setIntraCalendarsCacheIsUptodate(true);                  
                
                return new HashMap<GenericLink, TreeMap<Calendar,Long>>(intraCalendarsUsage);
            } catch (Exception e) {
                logger.error("Cannot get intradomain calendar from:"
                        + identifier + ":" + e.getClass().getName() + ":"
                        + e.getMessage());
            }
            
        } else {
            return new HashMap<GenericLink, TreeMap<Calendar,Long>>(intraCalendarsUsage);
        }
        
        return null;
    }

    @Override
    public String getReservationLog(String resId) {
        if (!isAdmnistrationConnected()) {
            return null;
        }

        try {
            return administration.getReservationLog(resId);
        } catch (Exception e) {
            logger.error("Cannot get log for reservation with id - " + resId + " : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<PortType> getDomainClientPorts() {
        // logger.info("Getting ports from idm:"+identifier+":"+ports);
        if (ports != null)
            return ports;
        try {
            if (isUserAccessPointConnected()) {
                ports = userAccessPoint.getDomainClientPorts();
                if (ports == null) {
                    ports = new ArrayList<PortType>();
                }

                logger.info(ports);
                return ports;
            }
        } catch (Exception e) {
            logger.error("Problem with getting domain ports:"
                    + e.getClass().getName() + ":" + e.getMessage());
        }
        return null;
    }

    /**
     * In normal way service is cached in InterDomainManager services collection
     * This method force to download service state from IDM manager directly
     */
    public void forceUpdateService(String serviceID) {
        if (!isAdmnistrationConnected()) {
            return;
        }
        ServiceType service = administration.getService(serviceID);
        if (service == null) {
            return;
        }
        ServiceType storeService = services.get(serviceID);
        if (storeService != null) {
            storeService.setJustification(service.getJustification());
            storeService.getReservations().clear();
            if (service.getReservations() != null) {
                int length = service.getReservations().size();
                for (int i = 0; i < length; i++) {
                    storeService.getReservations().add(service.getReservations().get(i));
                }
            }
        } else {
            services.put(serviceID, service);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.useraccesspoint.UserAccessPoint#modifyReservation(net.geant.autobahn.useraccesspoint.ModifyRequest)
     */
    @Override
    public void modifyReservation(ModifyRequest request) {
        try {
            if (isUserAccessPointConnected())
                userAccessPoint.modifyReservation(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkReservationPossibility(ReservationRequest request)
            throws UserAccessPointException {
        if (isUserAccessPointConnected()) {
            return userAccessPoint.checkReservationPossibility(request);
        }
        return false;
    }

    @Override
    public void registerCallback(String serviceID, String url) {
        // TODO Auto-generated method stub
    }

    @Override
    public String submitServiceAndRegister(ServiceRequest request, String url)
            throws UserAccessPointException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void cancelAllServices() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setTopology(List<Link> links) {
        // TODO Auto-generated method stub
    }

    @Override
    public void restart() {
        administration.restart();
    }

    /**
     * Methods that change the state of caching flags for NOCPanel. If they are set "true", then the 
     * appropriate method invokes the service to retrieve the information.  
     */    
    public synchronized void setIntraRsvCacheIsUptodate(boolean intraRsvCacheIsUptodate) {
        this.intraRsvCacheIsUptodate = intraRsvCacheIsUptodate;
    }
    public synchronized void setIntraPathsCacheIsUptodate(boolean intraPathsCacheIsUptodate) {
        this.intraPathsCacheIsUptodate = intraPathsCacheIsUptodate;
    }
    public synchronized void setIntraCalendarsCacheIsUptodate(boolean intraCalendarsCacheIsUptodate) {
        this.intraCalendarsCacheIsUptodate = intraCalendarsCacheIsUptodate;
    }    

    @Override
    public void handleTopologyChange(boolean deleteReservations, boolean update) throws AdministrationException {
        administration.handleTopologyChange(deleteReservations, update);
    }
}
