package net.geant.autobahn.autoBahnGUI.manager;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.aai.AccessRule;
import net.geant.autobahn.aai.UserAuthParameters;
import net.geant.autobahn.administration.AdministrationException;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.administration.Status;
import net.geant.autobahn.autoBahnGUI.model.AccessPolicyFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntraCalendarFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntrasComparator;
import net.geant.autobahn.autoBahnGUI.model.IntrasFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntraPathsFormModel;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.MapKeySetComparator;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServiceRequestModel;
import net.geant.autobahn.autoBahnGUI.model.ServicesComparator;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.model.StatisticsFormModel;
import net.geant.autobahn.autoBahnGUI.topology.TopologyFinderNotifier;
import net.geant.autobahn.gui.EventType;
import net.geant.autobahn.gui.ReservationChangedType;
import net.geant.autobahn.intradomain.IntradomainReservation;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.lookup.LookupServiceException;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.User;
import net.geant.autobahn.useraccesspoint.Mode;
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.Priority;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.Resiliency;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.util.AuthorityUtils;

/**
 * Manager is responsible for communication and managing of IDMs registered in WEB GUI
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class ManagerImpl implements Manager, ManagerNotifier {

    /**
     * Default time zone for WEB GUI
     */
    private  String timezone ="UTC";

    /**
     * List of all timezones for WEB GUI
     */
    private List<String> timezones = Arrays.asList(TimeZone.getAvailableIDs());

    /**
     * Topology notifier
     */
    private TopologyFinderNotifier notifier;

    /**
     * Service states name
     */
    public static final String[] serviceStates = {"unknown","accepted", "in progress", "scheduled",
        "active", "finished", "failed", "cancelled" }; 

    /**
     * Reservation states
     */
    public static final int UNKNOWN = 0;
    public static final int ACCEPTED = 1;
    public static final int PATHFINDING = 2;
    public static final int LOCAL_CHECK = 3;
    public static final int SCHEDULING =4;
    public static final int SCHEDULED = 5;
    public static final int CANCELLING = 6;
    public static final int DEFERRED_CANCEL = 7;
    public static final int WITHDRAWING = 8;
    public static final int ACTIVATING = 9;
    public static final int ACTIVE = 10;
    public static final int FINISHING = 11;
    public static final int FINISHED = 21;
    public static final int CANCELLED = 22;
    public static final int FAILED = 23; 

    /**
     * Reservations states names
     */
    public static final String[] reservationStates = {"UNKNOWN", "ACCEPTED", "PATHFINDING", "LOCAL_CHECK",
            "SCHEDULING", "SCHEDULED", "CANCELLING", "DEFERRED_CANCEL","WITHDRAWING","ACTIVATING",
            "ACTIVE", "FINISHING", 
            "UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN","UNKNOWN",
            "FINISHED","CANCELLED", "FAILED"};

    public static final String[] reservationDescriptions = {
            "Invalid state", // 0 UNKNOWN
            "Reservation has been put in processing queue", // 1 ACCEPTED
            "Looking for a suitable inter-domain path", // 2 PATHFINDING
            "Looking for a suitable intra-domain path", // 3 LOCAL_CHECK
            "A suitable intra-domain path has been found in some domains, waiting for the rest", // 4 SCHEDULING
            "Waiting for reservation start time in order to activate the circuit", // 5 SCHEDULED
            "User requested cancellation, some domains have cancelled, waiting for the rest", // 6 CANCELLING
            "User requested cancellation, some processing needs to finish and then reservation will be cancelled", // 7 DEFERRED_CANCEL
            "Circuit creation failed in some domain(s) and will be withdrawn from all", // 8 WITHDRAWING
            "Some domains have created the circuit, waiting for the rest", // 9 ACTIVATING
            "Circuit is up in all domains, end host connectivity is available", // 10 ACTIVE
            "Some domains have taken down the circuit, waiting for the rest", // 11 FINISHING
            "Invalid state", // 12 UNKNOWN
            "Invalid state", // 13 UNKNOWN
            "Invalid state", // 14 UNKNOWN
            "Invalid state", // 15 UNKNOWN
            "Invalid state", // 16 UNKNOWN
            "Invalid state", // 17 UNKNOWN
            "Invalid state", // 18 UNKNOWN
            "Invalid state", // 19 UNKNOWN
            "Invalid state", // 20 UNKNOWN
            "All domains have taken down the circuit, reservation finished successfully", // 21 FINISHED
            "User requested reservation cancellation, all domains have cancelled successfully", // 22 CANCELLED
            "Reservation failed, all domains have ceased processing it", // 23 FAILED
    };
    
    public static final String[] priorities = {};

    /**
     * Map with IDM registered in the system
     */
    private  Map<String, InterDomainManager> idms = Collections.synchronizedMap(new HashMap<String, InterDomainManager>());

    /**
     * Map with IDM registration times
     */
    private ConcurrentMap<String, Long> idmsTime = new ConcurrentHashMap<String, Long>();

    /**
     * Identifies period of time when IDM register in WEB GUI is mark as down
     */
    private long tearDownTime = 60000;

    /**
     * Logs information
     */
    private static Logger logger = Logger.getLogger("ManagerImpl");

    /**
     * List of all client ports
     */
    private List<PortType> ports = new ArrayList<PortType>();

    private LookupService lookupService;

    private String[] comparedLinks;

    private String[] comparedDomains;

    private String lookupHost;

    private List<ReservationHelper> reservaionHelpers = new ArrayList<ReservationHelper>();

    public ManagerImpl() {
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(
                        "../etc/webgui.properties");
            properties.load(is);
            is.close();
            logger.debug(properties.size() + " properties loaded");
        } catch (IOException e) {
            logger.info("Could not load lookuphost properties: " + e.getMessage());
        }
        lookupHost = properties.getProperty("lookuphost");

        // Build user-friendly list of timezones
        for (int i =0; i<timezones.size(); i++) {
            String tzStr = timezones.get(i);
            TimeZone tz = TimeZone.getTimeZone(tzStr);
            int offset = tz.getRawOffset() / (60*1000);
            int offset_hrs = offset / 60;
            int offset_min = offset % 60;
            if (offset_hrs < 0) {
                timezones.set(i, String.format("(GMT%03d:%02d) %s", offset_hrs, -offset_min, tzStr));
            } else {
                timezones.set(i, String.format("(GMT+%02d:%02d) %s", offset_hrs, offset_min, tzStr));
            }
        }
        Collections.sort(timezones);
    }

    @Override
    public LookupService getLookupService() {
        if (lookupService == null) {
            if (LookupService.isLSavailable(lookupHost)) {
                lookupService = new LookupService(lookupHost);
            } else {
                lookupService = null;
            }
        }
        return lookupService;
    }

    /**
     * Creates the InterDomainManager
     * 
     * @param name
     *            name of new InterDomainManagert
     * @return InterDomainManager
     */
    public InterDomainManager createIdm(String name) {
        InterDomainManager interdomain = new InterDomainManager(name, name);
        refreshPorts();
        return interdomain;
    }

    /**
     * Registers InterDomainManager
     * 
     * @param idm
     *            InterDomainManager
     */
    public void addIdm(InterDomainManager idm) {
        if (idm == null)
            return;
        idms.put(idm.getIdentifier(), idm);
    }

    /**
     * Removes registered InterDomainManager
     * 
     * @param identifier
     *            InterDomainManager identifier
     * @return removed InterDomainManager
     */
    public InterDomainManager remove(String identifier) {
        InterDomainManager manager = idms.remove(identifier);
        return manager;
    }

    /**
     * Removes registered InterDomainManager
     * 
     * @param name
     *            InterDomain manager
     * @return removed InterDomainManager
     */
    public InterDomainManager remove(InterDomainManager idm) {
        if (idm == null)
            return null;
        return idms.remove(idm);
    }

    /**
     * Removes all registered InterDomainManager
     */
    public void removeAll() {
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager idm = null;
        while (iterator.hasNext()) {
            idm = (InterDomainManager) idms.get(iterator.next());
            remove(idm);
            idm = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagers()
     */
    @Override
    public List<InterDomainManager> getInterDomainManagers() {
        List<InterDomainManager> managers = new ArrayList<InterDomainManager>();
        Iterator<String> keyIterator = idms.keySet().iterator();
        InterDomainManager manager;
        while (keyIterator.hasNext()) {
            manager = idms.get(keyIterator.next());
            if (manager != null)
                managers.add(manager);
        }
        return managers;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllIdcpPorts(java.lang.String)
     */
    @Override
    public List<PortType> getAllIdcpPorts (String idmIdentifier) {
        if (idmIdentifier != null) {
            InterDomainManager manager = idms.get(idmIdentifier);
            if (manager == null) {
                logger.info("getAllIdcpPorts() could not find idm manager for " 
                        + idmIdentifier + ", will try with any other registered IDM");
                return getAllIdcpPorts();
            }

            List<PortType> ports = manager.getIdcpPorts();

            if (ports != null) {
                return ports;
            } else {
                return getAllIdcpPorts();
            }
        }

        return getAllIdcpPorts();
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllIdcpPorts()
     */
    @Override
    public List<PortType> getAllIdcpPorts (){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
            logger.info("Getting IDCP ports from " + idm);
            InterDomainManager manager = idms.get(idm);
            List<PortType> pTypes = manager.getIdcpPorts();
            
            if (pTypes != null) {
                return pTypes;
            }
        }
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllClientPorts(java.lang.String)
     */
    @Override
    public List<PortType> getAllClientPorts(String idmIdentifier) throws UserAccessPointException {
        if (idmIdentifier != null) {
            InterDomainManager manager = idms.get(idmIdentifier);
            if (manager == null) {
                logger.info("getAllClientPorts() could not find idm manager for " + idmIdentifier
                        + ", will try with any other registered IDM");
                return getAllClientPorts();
            }

            List<PortType> pTypes = manager.getAllClientPorts();

            if (pTypes != null) {
                return pTypes;
            } else {
                return getAllClientPorts();
            }
        }

        // idmIndentifier is null
        return getAllClientPorts();
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllClientPorts()
     */
    @Override
    public List<PortType> getAllClientPorts() throws UserAccessPointException {
        // Parse through IDMs and get the first non-null result
        for (String idm : idms.keySet()) {
            logger.info("Getting client ports from " + idm);
            InterDomainManager manager = idms.get(idm);
            ports = manager.getAllClientPorts();

            if (ports != null) {
                return ports;
            }
        }

        return null;
    }

    public List<PortType> getAllClientPorts_filtered(String idmIdentifier)
            throws UserAccessPointException {
        return filterUserAuthorized(getAllClientPorts(idmIdentifier));
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllFriendlyAndIdcpPorts(java.lang.String)
     */
    @Override
    public List<PortType> getAllFriendlyAndIdcpPorts (String idm) throws UserAccessPointException {
        List<PortType> friendlyPorts = getAllClientPorts(idm);
        List<PortType> idcpPorts = this.getAllIdcpPorts(idm);
        
        if (friendlyPorts == null) {
            friendlyPorts = new ArrayList<PortType>();
        }
        
        if (idcpPorts == null) {
            return friendlyPorts;
        }
        
        for (PortType idcpP : idcpPorts) {
            friendlyPorts.add(idcpP);
        }

        return friendlyPorts;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains()
     */
    @Override
    public List<String> getAllDomains(){
        // Parse through IDMs and get the first non-null result
        for(String idm : idms.keySet()) {
            InterDomainManager manager = idms.get(idm);
            String[] pTypes = manager.getAllDomains();

            List<String> domains = new ArrayList<String>();

            if (pTypes != null) {
                for (String link : pTypes) {
                    domains.add(link);
                }

                return domains;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllDomains_NonClient()
     */
    @Override
    public List<String> getAllDomains_NonClient(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager = null;

        while (iterator.hasNext()) {
            manager = idms.get(iterator.next());
            if (manager != null) {
                List<String> domains = new ArrayList<String>();

                String[] pTypes = manager.getAllDomains_NonClient();
                if (pTypes != null) {
                    for (String domain : pTypes) {
                        domains.add(domain);
                    }
                }

                return domains;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllLinks()
     */
    @Override
    public List<String> getAllLinks(){
        // Parse through IDMs and get the first non-null result
        for (String idm : idms.keySet()) {
            InterDomainManager manager = idms.get(idm);

            String[] pTypes = manager.getAllLinks();

            if (pTypes != null) {
                List<String> links = new ArrayList<String>();

                for (String link : pTypes) {
                    links.add(link);
                }

                return links;
            }
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllLinks_NonClient()
     */
    @Override
    public List<String> getAllLinks_NonClient(){
        // Parse through IDMs and get the first non-null result
        Iterator<String> iterator = idms.keySet().iterator();
        InterDomainManager manager = null;
        while (iterator.hasNext()) {
            manager = idms.get(iterator.next());
            if (manager != null) {
                String[] pTypes = manager.getAllLinks_NonClient();

                if (pTypes != null) {
                    List<String> links = new ArrayList<String>();

                    for (String link : pTypes) {
                        links.add(link);
                    }

                    return links;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManagerPorts(java.lang.String)
     */
    @Override
    public List<PortType> getInterDomainManagerPorts(String idmIdentifier) {
        InterDomainManager manager = idms.get(idmIdentifier);
        if (manager == null) {
            logger.info("idms.get manager is NULL");
            return null;
        }

        List<PortType> pTypes = manager.getDomainClientPorts();
        if (pTypes == null) {
            logger.info("idms manager is" + manager.getIdentifier());
            logger.info("idms.get client ports is NULL");
            return null;
        }
        return pTypes;
    }

    public List<PortType> getInterDomainManagerPorts_filtered(String idmIdentifier) {
        return filterUserAuthorized(getInterDomainManagerPorts(idmIdentifier));
    }

    @Override
    public Map<String, String> getServicesForAllInterDomainManagers() {

        List<ServiceType> list = new ArrayList<ServiceType>();
        InterDomainManager manager = null;
        List<ServiceType> managerServices = null;

        for (String idm : idms.keySet()) {
            manager = idms.get(idm);
            if (manager == null)
                continue;
            managerServices = manager.getServices(false);
            if (managerServices == null)
                continue;
            list.addAll(managerServices);
        }
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < list.size(); i++) {

            List<ReservationType> res = list.get(i).getReservations();
            for (int j = 0; j < res.size(); j++) {

                if (res.get(j).getPath() == null) {
                    continue;
                }
                String bodyID = res.get(j).getBodID();
                String homeID = res.get(j).getPath().getHomeDomainID();
                map.put(bodyID, homeID);
            }
        }
        if (map.size() == 0) {
            return null;
        } else {
            return sortMapByKey(map);
        }
    }

    public LinkedHashMap<String, String> sortMapByKey(final Map<String, String> map) {

        TreeSet<String> treeset = new TreeSet<String>(new MapKeySetComparator());
        treeset.addAll(map.keySet());

        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

        for (String str : treeset) {
            result.put(str, map.get(str));
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getAllInterdomainManagers()
     */
    @Override
    public List<String> getAllInterdomainManagers() {
        List<String> names = new ArrayList<String>();
        for (String name : idms.keySet())
            names.add(name);
        return names;
    }

    /**
     * Make actions needed when  new IDM appear for first time
     * 
     * @param idmName - Unique Name of the IDM domain, e.g. Geant
     * @param idmUrl - URL where the IDM is listening
     * @return
     */
    public InterDomainManager newInterDomainManagerConnected(String idmName, String idmUrl) {
        int index = idmUrl.indexOf("/interdomain");
        String url;
        if (index > 0) {
            url = idmUrl.substring(0, index);
        } else {
            url = idmUrl;
        }
        InterDomainManager manager = new InterDomainManager(idmName, url);
        refreshPorts();
        if (manager != null) {
            idms.put(idmName, manager);
        }
        return manager;
    }

    private void refreshPorts() {
        ports.clear();
        try {
            getAllClientPorts();
        } catch (UserAccessPointException e) {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#reservationChanged(java.lang.String, java.lang.String, java.lang.String, net.geant.autobahn.gui.ReservationChangedType, java.lang.String)
     */
    @Override
    public void reservationChanged(String idm, String serviceId, String resID,
            ReservationChangedType state, String message) {
        if (serviceId == null) {
            serviceId = resID.substring(0, resID.lastIndexOf("_res_"));
        }
        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            return;
        }
        ServiceType service = manager.getService(serviceId);
        if (service == null) {
            return;
        }
        List<ReservationType> list = new ArrayList<ReservationType>();
        list.addAll(service.getReservations());
        markIDMCacheAsDirty(list);
        int stateOfReservation = convertReservationTypes(state);
        if (stateOfReservation == SCHEDULED) {
            manager.forceUpdateService(serviceId);
        }
        int length = list.size();
        ReservationType reservation = null;
        for (int i = 0; i < length; i++) {
            reservation = list.get(i);
            if (reservation.getBodID().equals(resID)) {
                reservation.setState(stateOfReservation);
                if (stateOfReservation == FAILED) {
                    reservation.setFailureCause(message);
                }
                break;
            }
        }
        manager.getServices(true);
    }

    /**
     * Finds all IDMs that are traversed by one of the 
     * reservations in the list and marks their caches as dirty.
     * 
     * @param rsvList
     */
    private void markIDMCacheAsDirty(List<ReservationType> rsvList) {
        for (ReservationType rsvType : rsvList) {
            if (rsvType.getPath() != null && rsvType.getPath().getDomains() != null) {
                for (String domain : rsvType.getPath().getDomains()) {
                    InterDomainManager manager = idms.get(domain);

                    if (manager != null) {
                        manager.setIntraCalendarsCacheIsUptodate(false);
                        manager.setIntraPathsCacheIsUptodate(false);
                        manager.setIntraRsvCacheIsUptodate(false);
                    }
                }
            }
        }
    }

    @Override
    public void checkIDMavailability() {
        if (!idms.isEmpty()) {
            Iterator<String> iterator = idmsTime.keySet().iterator();
            do {
                String nextElement = iterator.next();

                if (System.currentTimeMillis() - idmsTime.get(nextElement) > tearDownTime) {
                    logger.info("IDM tear down " + nextElement);
                    idms.remove(nextElement);
                }

            } while (iterator.hasNext());
        } else {
            logger.info("IDMs not available ");
        }

    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#statusUpdated(java.lang.String, java.lang.String, net.geant.autobahn.administration.Status)
     */
    @Override
    public void statusUpdated(String idm, String idmUrl, Status status) {

        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            manager = newInterDomainManagerConnected(idm, idmUrl);
        }

        manager.setStatus(status);

        if (!idmsTime.containsKey(idm)) {
            idmsTime.put(idm, manager.getLastStatusUpdateInMillis());
        } else {
            idmsTime.replace(idm, manager.getLastStatusUpdateInMillis());
        }

        if (manager.getLastStatusUpdateInMillis() > tearDownTime) {
            try {
                manager.getServices(true);
                manager.getProperties();
                manager.getLog(true);
            } catch (Exception e) {
                logger.info(e.getClass().getName() + ":" + e.getMessage());
            }
        }
        if (notifier != null) {
            notifier.updateTopology(idm);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getLogsInterDomainManager(java.lang.String, boolean, boolean)
     */
    @Override
    public String getLogsInterDomainManager(String idm, boolean refresh, boolean all, String resId) {
        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            return null;
        }
        if (resId == null) {
        return manager.getLog(refresh, all);
        }
        return manager.getReservationLog(resId);
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationStates()
     */
    @Override
    public String[] getReservationStates() {
        return reservationStates;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationDescriptions()
     */
    @Override
    public String[] getReservationDescriptions() {
        return reservationDescriptions;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkUserAccessPointConnection(java.lang.String)
     */
    @Override
    public boolean checkUserAccessPointConnection(String idm) {
        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            return false;
        }
        return manager.isUserAccessPointConnected();
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkAdminstrationConnection(java.lang.String)
     */
    @Override
    public boolean checkAdminstrationConnection(String idm) {
        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            return false;
        }
        return manager.isAdmnistrationConnected();
    }

    private void logServiceRequest (ServiceRequest service ) {
        StringBuffer buffer =new StringBuffer ();
        buffer.append ("Service:").append(service.getUserName()).append(",").append(service.getUserHomeDomain());
        if (service.getReservations() == null) {
            logger.info(buffer.toString());
        }
        buffer.append("\n");
        int length = service.getReservations().size();
        ReservationRequest request = null;
        for (int i = 0; i < length; i++) {
            request = service.getReservations().get(i);
            if (request != null) {
                buffer.append("Reservation" + request.getStartTime() + ":"
                        + request.getEndPort());
            }
        }
        // logger.info(buffer.toString());
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#submitServiceAtInterDomainManager(java.lang.String, net.geant.autobahn.useraccesspoint.ServiceRequest)
     */
    @Override
    public String submitServiceAtInterDomainManager(String idm, ServiceRequest request) throws UserAccessPointException, ManagerException {
        logger.info("Submitting service");
        if (request == null) {
            throw new ManagerException(ManagerException.NO_SERVICE, "Sumitting null service");
        }
        if (request.getReservations() == null || request.getReservations().isEmpty()) {
            throw new ManagerException(ManagerException.SERVICE_WITHOUT_RESERVATIONS, "Empty service submitted");
        }

        UserAuthParameters authParameters = getUserAuthParameters();

        for (int i=0; i<request.getReservations().size(); i++) {
            request.getReservations().get(i).setAuthParameters(authParameters);
        }

        logger.info("Verified");
        logServiceRequest(request);
        InterDomainManager manager = idms.get(idm);
        if (manager == null) {
            throw new ManagerException (ManagerException.UNKNOWN_MANAGER,"No manager with name:"+idm);
        }
        logger.info("Verified");
        synchronized (manager) {
            String id = manager.submitService(request);
            logger.info("submitted");
            return id;
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#checkReservationPossibility(java.lang.String, net.geant.autobahn.useraccesspoint.ReservationRequest)
     */
    @Override
    public ReservationTest checkReservationPossibility(String idm,
            ReservationRequest request) throws UserAccessPointException {
        InterDomainManager manager = idms.get(idm);
        logger.info("check reservation possibility" + manager == null);
        ReservationTest test = new ReservationTest();
        if (manager == null) {
            test.setStatus(false);
        } else {
            UserAuthParameters authParameters = getUserAuthParameters();
            request.setAuthParameters(authParameters);

            test.setStatus(manager.checkReservationPossibility(request));
        }
        return test;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getPropertiesForInterDomainManager(java.lang.String)
     */
    @Override
    public List<KeyValue> getPropertiesForInterDomainManager(String idm) {
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            return manager.getProperties();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#setPropertiesForInterDomainManager(java.lang.String, java.util.List)
     */
    @Override
    public void setPropertiesForInterDomainManager(String idm, List<KeyValue> properties) {
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            manager.setProperties(properties);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#setAccessPolicyForInterDomainManager(java.lang.String, AccessPolicy)
     */
    public void setAccessPolicyForInterDomainManager(String idm, AccessPolicy acp) {
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            manager.setAccessPolicy(acp);
        }
    }

    @Override
    public void addRuleForIDM(String idm, String role, String email, String projMem, String org) {
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            AccessPolicy p = manager.getAccessPolicy();
            UserAuthParameters uauth = new UserAuthParameters(role, email, projMem, org);
            p.addRule(new AccessRule(uauth));
            logger.debug("Adding rule:" + new AccessRule(uauth) + " at domain:" + idm);
            manager.setAccessPolicy(p);
        }
    }

    @Override
    public void removeRuleForIDM(String idm, String role, String email, String projMem, String org) {
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            AccessPolicy p = manager.getAccessPolicy();
            UserAuthParameters uauth = new UserAuthParameters(role, email, projMem, org);
            p.removeRule(new AccessRule(uauth));
            logger.debug("Removing rule:" + new AccessRule(uauth) + " from domain:" + idm);
            manager.setAccessPolicy(p);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceStates()
     */
    @Override
    public String[] getServiceStates() {
        return serviceStates;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServicesFromInterDomainManager(java.lang.String)
     */
    @Override
    public List<ServiceType> getServicesFromInterDomainManager(String idm) {
        if (idm == null) {
            List<ServiceType> all_services = new ArrayList<ServiceType>();
            for (InterDomainManager mgr : idms.values()) {
                all_services.addAll(mgr.getServices(false));
            }
            return all_services;
        }
        InterDomainManager manager = idms.get(idm);
        if (manager != null) {
            return manager.getServices(false);
        }
        return null;
    }

    /**
     * Get domain reservations
     * 
     * @param The domain name.
     * @return The list of the ReservationType in selected domain
     */
    public List<ReservationType> getDomainReservations(String idm){
        List<ReservationType> resType = new ArrayList<ReservationType>();
        if(idm == null){
            for (InterDomainManager mgr : idms.values()) {
                for(ServiceType sType : mgr.getServices(false)){
                    for(ReservationType rt :sType.getReservations()){
                        resType.add(rt);
                    }
                }
            }
            return resType;
        }
        InterDomainManager manager = idms.get(idm);
        if (manager != null){
            for(ServiceType sType : manager.getServices(false)){
                for(ReservationType rt :sType.getReservations()){
                    resType.add(rt);
                }
            }
            return resType;
        }
        return null;
    }

    /**
     * Get domain reservations with specific reservation state
     * 
     * @param The domain name
     * @param The state
     * @return The list of the ReservationType in selected domain and specific state
     */
    public List<ReservationType> getInterReservationsForIDMWithSelectedReservationState(String idm, String state){
        List<ReservationType> res = new ArrayList<ReservationType>();
        if (idm == null) {
            for (InterDomainManager mgr : idms.values()) {
                for (ServiceType serviceType : mgr.getServices(false)) {
                    for (ReservationType resType : serviceType.getReservations()){
                        if (reservationStates[resType.getState()].equals(state))
                            res.add(resType);
                    }
                }
            }

            return res;
        }
        InterDomainManager manager = idms.get(idm);
        if (manager != null){
            for(ServiceType serviceType : manager.getServices(false)){
                for(ReservationType resType : serviceType.getReservations()) {
                    if (reservationStates[resType.getState()].equals(state))
                        res.add(resType);
                }
            }
            return res;
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#cancelServiceInInterDomainManager(java.lang.String, java.lang.String)
     */
    @Override
    public void cancelServiceInInterDomainManager(String idm, String serviceID)
            throws UserAccessPointException {

        if (idm == null || serviceID == null) {
            return;
        }
        InterDomainManager manager = idms.get(idm);

        if ((manager != null) && (manager.getService(serviceID) != null)) {
            logger.info("Canceling service: " + idm + ":" + serviceID);
            manager.cancelService(serviceID);
        } else {
            logger.info("Service can not be canceled: " + idm + ":" + serviceID);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.ManagerNotifier#update(java.lang.String, net.geant.autobahn.gui.EventType, java.util.List)
     */
    @Override
    public void update(String idm, EventType event, List<KeyValue> properties) {
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getInterDomainManager(java.lang.String)
     */
    @Override
    public InterDomainManager getInterDomainManager(String idm) {
        return idms.get(idm);
    }

    /**
     * Sets the topology notifier
     * 
     * @return topology notifier
     */
    public TopologyFinderNotifier getNotifier() {
        return notifier;
    }

    public void setNotifier(TopologyFinderNotifier notifier) {
        this.notifier = notifier;
    }

    /**
     * Converts the reservation state known by WEB GUI
     * 
     * @param state
     *            reservation state
     * @return return state known by WEB GUI
     */
    public int convertReservationTypes(ReservationChangedType state) {
        if (state == ReservationChangedType.ACTIVE)
            return ACTIVE;
        else if (state == ReservationChangedType.CANCELLED)
            return CANCELLED;
        else if (state == ReservationChangedType.FAILED)
            return FAILED;
        else if (state == ReservationChangedType.FINISHED)
            return FINISHED;
        else if (state == ReservationChangedType.SCHEDULED)
            return SCHEDULED;
        return 0;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceFromInterDomainManager(java.lang.String, java.lang.String)
     */
    @Override
    public ServiceType getServiceFromInterDomainManager(String idm, String serviceId) {
        InterDomainManager manager = idms.get(idm);
        if (manager == null)
            return null;
        else
            return manager.getService(serviceId);
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getTearDownTime()
     */
    @Override
    public long getTearDownTime() {
        return tearDownTime;
    }

    /**
     * Sets period of time after it the registered IDM is mark as not accessible
     * 
     * @param tearDownTime
     *            period in mili seconds
     */
    public void setTearDownTime(long tearDownTime) {
        this.tearDownTime = tearDownTime;
    }

    /**
     * Get default startTime and endTime strings
     * 
     * @return array with startTime and endTime
     */
    public String[] getTimes() {
        String[] times = new String[2];
        TimeZone time = TimeZone.getTimeZone(timezone);
        if (time == null)
            time = TimeZone.getTimeZone("UTC");
        GregorianCalendar calender = (GregorianCalendar) GregorianCalendar.getInstance(time);
        Date dat = new Date();
        calender.setTime(dat);
        calender.add(Calendar.MINUTE, 15);
        dat = calender.getTime();
        // SimpleDateFormat format=new
        // SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        times[0] = formatter.format(dat);
        calender.add(Calendar.HOUR, 1);
        dat = calender.getTime();
        times[1] = formatter.format(dat);
        return times;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationRequestTemplate()
     */
    @Override
    public ReservatiomDepandentOnTimezone getReservationRequestTemplate() {
        ReservatiomDepandentOnTimezone res = new ReservatiomDepandentOnTimezone();
        ReservationRequest reservation = new ReservationRequest();
        String[] times = getTimes();
        reservation.setPriority(Priority.NORMAL);
        reservation.setResiliency(Resiliency.NONE);
        reservation.setCapacity(10);
        try {
            reservation.setStartTime(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(times[0]).toGregorianCalendar());
            reservation.setEndTime(DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(times[1]).toGregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        res.setRequest(reservation);
        res.setTimezone(timezone);
        return res;
    }

    public UserAuthParameters getUserAuthParameters() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        @SuppressWarnings("unchecked")
        Set<String> authorities = AuthorityUtils.authorityArrayToSet(auth.getAuthorities());
        UserAuthParameters authParameters = new UserAuthParameters(auth.getName(), authorities);
        return authParameters;
    }

    List<PortType> filterUserAuthorized(List<PortType> allPorts) {
        List<PortType> res = new ArrayList<PortType>();
        if (allPorts == null) {
            return null;
        }
        List<String> userAllowed = getUserPorts("allow");
        if (userAllowed != null) {
            logger.info("Some ports allowed only:");
            for (PortType p : allPorts) {
                for (String s : userAllowed) {
                    if (p.getAddress().equals(s)) {
                        logger.info(" --" + s);
                        res.add(p);
                    }
                }
            }
        } else {
            List<String> userDenied = getUserPorts("deny");
            if (userDenied != null) {
                logger.info("Some ports denied:");
                for (PortType p : allPorts) {
                    for (String s : userDenied) {
                        if (p.getAddress().equals(s)) {
                            logger.info(" --" + s);
                            continue;
                        }
                        res.add(p);
                    }
                }
            } else {
                return allPorts;
            }
        }

        return res;
    }

    /**
     * 
     * @param key - Permitted values are allow and deny
     * @return null if no such property is found
     */
    public List<String> getUserPorts(String key) {
        UserAuthParameters userAuth = this.getUserAuthParameters();
        if (userAuth == null) {
            logger.info("No user auth data");
            return null;
        }
        String username = userAuth.getIdentifier();
        Properties prop = getPropertiesFromResource("../etc/aai/" + username);
        if (prop == null) {
            logger.info(username + " not found in etc/aai");
            return null;
        }
        String allowed = prop.getProperty(key);
        if (allowed == null || allowed.length() == 0) {
            logger.info(key + " has no values");
            return null;
        }
        return Arrays.asList(allowed.split(","));
    }

    public Properties getPropertiesFromResource(String path) {
        Properties properties = new Properties();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            properties.load(is);
            is.close();
            logger.debug(properties.size() + " properties loaded from " + path);
        } catch (Exception e) {
            logger.info("Could not load " + path + ": " + e.getMessage());
            return null;
        }
        return properties;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getServiceRequestTemplate()
     */
    @Override
    public ServiceRequestModel getServiceRequestTemplate() {
    	ServiceRequest service = new ServiceRequest();
        ServiceRequestModel serviceModel = new ServiceRequestModel();
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        if (username == null) {
            username = "test";
        }
        // service.setUserEmail("test.user@test.domain");
        service.setUserName(username);
        List<String> idms = getAllInterdomainManagers();
        if (idms != null && !idms.isEmpty()) {
        	service.setUserHomeDomain(idms.get(0));
        }
        serviceModel.setService(service);
        return serviceModel;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationPriorities()
     */
    @Override
    public List<String> getReservationPriorities() {
        List<String> list = new ArrayList<String>();
        for (Priority req : Priority.values())
            list.add(req.toString());
        return list;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#getReservationResiliencies()
     */
    @Override
    public List<String> getReservationResiliencies() {
        List<String> list = new ArrayList<String>();
        for (Resiliency req : Resiliency.values())
            list.add(req.toString());
        return list;
    }

    @Override
    public List<String> getReservationModes() {
        List<String> list = new ArrayList<String>();
        for (Mode req : Mode.values())
            list.add(req.toString());
        return list;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    @Override
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    // Change a date to GMT from a given timezone
    public static Date toGmtFromZone(Date date, String fromZone) {
        TimeZone pst = TimeZone.getTimeZone(fromZone);
        return new Date(date.getTime() - pst.getRawOffset());
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#convertTimeToApplicationTimezone(java.lang.String, net.geant.autobahn.useraccesspoint.ReservationRequest)
     */
    @Override
    public void convertTimeToApplicationTimezone(String userZoneStr, ReservationRequest request) {

        logger.info("Converting times for request " + request
                + " according to timezone " + userZoneStr);
        try {
            if (userZoneStr == null) {
                return;
            }

            // Cut user-friendly text from timezone String
            String[] split_tz = userZoneStr.split("\\) ");
            userZoneStr = split_tz[split_tz.length - 1];
            TimeZone userZone = TimeZone.getTimeZone(userZoneStr);

            Calendar startTime = request.getStartTime();
            GregorianCalendar startTimeShifted = (GregorianCalendar) Calendar.getInstance();
            startTimeShifted.setTimeZone(userZone);
            startTimeShifted.setTimeInMillis(startTime.getTimeInMillis()
                    - userZone.getRawOffset() + startTime.getTimeZone().getRawOffset());

            Calendar endTime = request.getEndTime();
            GregorianCalendar endTimeShifted = (GregorianCalendar) Calendar.getInstance();
            endTimeShifted.setTimeZone(userZone);
            endTimeShifted.setTimeInMillis(endTime.getTimeInMillis() - 
                    userZone.getRawOffset() + endTime.getTimeZone().getRawOffset());

            try {
                request.setStartTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeShifted).toGregorianCalendar());
                request.setEndTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeShifted).toGregorianCalendar());
                logger.info("Request was shifted to " + request);
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    @Override
    public ServicesFormModel getSubmitedServicesInIDM(String idm) {
        ServicesFormModel serv = new ServicesFormModel();
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return serv;
        }
        serv.setIdms(managers);
        if (idm == null) {
            serv.setCurrentIdm(managers.get(0));
            manager = idms.get(managers.get(0));
            serv.setComparator(new ServicesComparator());
            serv.setServices(manager.getServices(false));
        } else {
            manager = idms.get(idm);
            List<ServiceType> services = manager.getServices(false);
            serv.setComparator(new ServicesComparator());
            serv.setServices(services);
            serv.setCurrentIdm(idm);
        }

        // Filtering submitted services
        boolean isAdmin = AuthorityUtils.userHasAuthority("ROLE_ADMINISTRATOR");
        boolean isNetAdmin = AuthorityUtils.userHasAuthority("ROLE_NETWORKADMIN");

        if (!isAdmin && !isNetAdmin && serv.getServices() != null) {
            // Filtering by username
            List<ServiceType> filteredServices = new ArrayList<ServiceType>();

            //TODO: consider using the same way that getServiceRequestTemplate gets the username
            String contextUsername=SecurityContextHolder.getContext().getAuthentication().getName();

            for (ServiceType servType : serv.getServices()) {
                String serviceID = servType.getBodID();
                if (manager.getService(serviceID) == null) {
                    continue;
                }
                // Get the username of the first reservation as username
                String servUsername = servType.getUser().getName();
                if (servUsername == null) {
                    continue;
                }
                if (servUsername.equals(contextUsername)) {
                    filteredServices.add(servType);
                }
            }

            serv.setServices(filteredServices);
        }
        return serv;
    }

    @Override
    public LogsFormModel getLogsForInterDomainManager(String idm, String resId) {
        LogsFormModel serv = new LogsFormModel();
        List<String> managers = getAllInterdomainManagers();
        serv.setIdms(managers);
        if (managers == null || managers.isEmpty()) {
            serv.setError("There is no log provided");
            serv.setLogs("");
            return serv;
        }
        if (idm == null) {
            idm = managers.get(0);
        }
        serv.setLogs(getLogsInterDomainManager(idm, true, true, resId));
        serv.setCurrentIdm(idm);
        if (serv.getLogs() == null)
            serv.setLogs("");

        return serv;
    }

    public IntrasFormModel getIntraReservationsForInterDomainManager(String idm) {
        IntrasFormModel intras = new IntrasFormModel();
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return intras;
        }
        intras.setIdms(managers);
        if (idm == null)
            idm = managers.get(0);

        manager = idms.get(idm);
        intras.setCurrentIdm(idm);
        intras.setComparator(new IntrasComparator());

        HashMap<String, IntradomainReservation> map = manager.getIntradomainReservationParams();

        intras.setIntras(new ArrayList<IntradomainReservation>(map.values()));
        logger.info("Got intra reservations:" + intras.getIntras());

        //TODO: Think about possible authR filtering, as done in getSubmitedServicesInIDM

        return intras;
    }

    public IntrasFormModel getIntraReservationsForIDMWithSelectedReservationState(String idm, String state) {
        IntrasFormModel intras = new IntrasFormModel();
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        if (managers == null || managers.size() == 0) {
            return intras;
        }
        intras.setIdms(managers);
        if (idm == null)
            idm = managers.get(0);

        manager = idms.get(idm);
        intras.setCurrentIdm(idm);
        intras.setComparator(new IntrasComparator());

        HashMap<String, IntradomainReservation> map = manager.getIntradomainReservationParams();

        List<IntradomainReservation> list = new ArrayList<IntradomainReservation>();
        for(IntradomainReservation r : map.values()){
            if(getReservationByResID(r.getReservationId()) != null){
                ReservationType rType = getReservationByResID(r.getReservationId());
                if(reservationStates[rType.getState()].equals(state))
                    list.add(r);
            }
        }
        intras.setIntras(list);
        return intras;
    }

    public IntradomainReservation getIntraReservationsForInterDomainManager(String idm, String resId) {
        IntradomainReservation res = null;
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return null;
        }
        if (idm == null) {
            idm = managers.get(0);
        }
        manager = idms.get(idm);
        HashMap<String, IntradomainReservation> allres = manager.getIntradomainReservationParams();
        if (allres == null) {
            return null;
        }
        logger.info("There are " + allres.size() + " intra-reservations, searching for id " + resId);
        res = allres.get(resId);
        logger.info("Got intra reservation:" + res);

        //TODO: Think about possible authR filtering, as done in getSubmitedServicesInIDM

        return res;
    }

    public ReservationType getReservation (String idm, String resId) {
        ReservationType res = null;
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return null;
        }
        if (idm == null){
            idm = managers.get(0);
        }
        manager = idms.get(idm);
        if(manager == null)
            return null;

        res = manager.getReservation(resId);
        logger.info("Got reservation:" + res);

        //TODO: Think about possible authR filtering, as done in getSubmitedServicesInIDM

        return res;
    }

    public ReservationType getReservationByResID(String resID){
        if(resID == null)
            return null;

        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        if (managers == null || managers.size() == 0)
            return null;

        int end = 0;
        if(resID.contains("@"))
            end = resID.indexOf("@");

        String idm = resID.substring(0, end);
        if (idm == null || idm.length() == 0)
            return null;

        manager = idms.get(idm);
        if(manager == null)
            return null;

        if(manager.getReservation(resID) != null)
            return manager.getReservation(resID);
        else 
            return null;

    }

    public IntraPathsFormModel getIntraPathsForInterDomainManager(String idm) {
        IntraPathsFormModel intraPaths = new IntraPathsFormModel();
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return intraPaths;
        }
        intraPaths.setIdms(managers);
        if (idm == null) {
            idm = managers.get(0);
        }
        manager = idms.get(idm);
        intraPaths.setCurrentIdm(idm);
        //intras.setComparator(new IntrasPathComparator());
        intraPaths.setIntraPaths(manager.getIntradomainPaths());
        logger.info("Got intra paths:" + intraPaths);

        //TODO: Think about possible authR filtering, as done in getSubmitedServicesInIDM

        return intraPaths;
    }

    public IntraCalendarFormModel getIntraCalendarForInterDomainManager(String idm) {
        IntraCalendarFormModel intraCalendar = new IntraCalendarFormModel();
        List<String> managers = getAllInterdomainManagers();
        InterDomainManager manager = null;
        logger.info("managers:" + managers);
        if (managers == null || managers.size() == 0) {
            return intraCalendar;
        }
        intraCalendar.setIdms(managers);
        if (idm == null) {
            idm = managers.get(0);
        }
        manager = idms.get(idm);
        intraCalendar.setCurrentIdm(idm);
        //intras.setComparator(new IntraCalendarComparator());
        intraCalendar.setIntraCalendar(manager.getIntradomainCalendarsUsage(null));
        logger.info("Got intra calendar:" + intraCalendar);

        //TODO: Think about possible authR filtering, as done in getSubmitedServicesInIDM

        return intraCalendar;
    }

    public AccessPolicyFormModel getAccessPolicyForInterDomainManager(String idm) {
        AccessPolicyFormModel serv =  new AccessPolicyFormModel();
        List<String> managers = getAllInterdomainManagers();
        serv.setIdms(managers);
        if (managers == null || managers.isEmpty()) {
            serv.setError("No AccessPolicy information can be fetched");
            return serv;
        }
        if (idm == null) {
            idm = managers.get(0);
        }
        InterDomainManager manager = idms.get(idm);
        try {
            serv.setAccessPolicy(manager.getAccessPolicy());
        } catch (Exception e) {
            logger.error("No Access Policy");
            serv.setAccessPolicy(null);
        }
        serv.setCurrentIdm(idm);
        return serv;
    }

    @Override
    public StatisticsFormModel getStatisticsForInterDomainManager(String idm) {
        StatisticsFormModel serv = new StatisticsFormModel();
        List<String> managers = getAllInterdomainManagers();
        serv.setIdms(managers);
        if (managers == null || managers.isEmpty()){
            serv.setError("No IDM could be found. No statistics provided");
            return serv;
        }
        if (idm == null){
            idm = managers.get(0);
        }
        InterDomainManager manager = idms.get(idm);
        try {
            serv.setStatistics(manager.getStatistics(true));
        } catch (Exception e){
            logger.error("No statistics could be retrieved.");
            serv.setStatistics(null);
        }
        serv.setCurrentIdm(idm);
        return serv;
    }

    @Override
    public SettingsFormModel getSettingsForInterDomainManager(String idm) {
        SettingsFormModel serv = new SettingsFormModel();
        List<String> managers = getAllInterdomainManagers();
        serv.setIdms(managers);
        if (managers == null || managers.isEmpty()) {
            serv.setError("There is no settings provided");
            return serv;
        }
        if (idm == null) {
            idm = managers.get(0);
        }
        InterDomainManager manager = idms.get(idm);
        try {
            serv.setProperties(manager.getProperties());
        } catch (Exception e) {
            logger.error("No settings");
            serv.setProperties(null);
        }
        serv.setCurrentIdm(idm);
        return serv;
    }

    @Override
    public List<ServiceType> sortServicesByBodyID(List<ServiceType> list) {
        Comparator<ServiceType> comparator = new ServicesComparator();
        Collections.sort(list, comparator);
        return list;
    }

    @Override
    public String setParameter(String param) {
        return param;
    }

    @Override
    public void convertCapacity(ReservationRequest request) {
        long capacity = request.getCapacity();
        request.setCapacity(capacity * 1000000);
    }

    @Override
    public String getFriendlyNamePort(String port) throws UserAccessPointException {

        if (port == null || port.length() == 0) {
            return null;
        }

        if (ports.size() == 0) {
            getAllClientPorts();
        }

        for (PortType p : ports) {
            if (port.equals(p.getAddress())) {
                return p.getFriendlyName();
            }
        }
        return null;
    }

    @Override
    public List<LinkMap> getAllDomainLinks(){

        List<String> str = getAllLinks_NonClient();
        List<LinkMap> domainLinks = new ArrayList<LinkMap>();

        for (String idm : idms.keySet()) {	
            InterDomainManager manager = idms.get(idm);
            List<Link> links = manager.getTopology();
            for (Link link : links) {
                if (str.contains(link.getBodID())) {
                    String path = new String();
                    if (link.getStartDomainID().equalsIgnoreCase(link.getEndDomainID())) {
                        path = " ["+link.getBodID() +"] Internal Link "+link.getStartDomainID();
                    } else {
                        path = " ["+link.getBodID() +"] from "+link.getStartDomainID()+" to "+link.getEndDomainID();
                    }
                    domainLinks.add(new LinkMap(link.getBodID(), path));
                }
            }
            break;
        }

        if (domainLinks.size() == 0) {
            return null;
        }

        return domainLinks;
    }

    @Override
    public boolean checkTopology(String idm){

        if (idms != null && idms.size() > 0) {

            if (!idms.containsKey(idm)) {
                return false;
            }

            InterDomainManager manager = idms.get(idm);
            if (manager == null) {
                return false;
            }

            String[] links = manager.getAllLinks();
            String[] domains = manager.getAllDomains();

            if (comparedLinks != links || comparedDomains != domains) {
                comparedLinks = links;
                comparedDomains = domains;

                return true;
            }
        }
        return false;
    }

    public static String[] getReservationstates() {
        return reservationStates;
    }

    /**
     * Get domain reservations (intra and inter) which is used in NOC panel
     * 
     * @param The IntrasFormModel
     * @param The list of ReservationType
     * @return The list of the ReservationHelper used in NOC panel
     */
    public List<ReservationHelper> getDomainReservations(IntrasFormModel intras, List<ReservationType> inter_reservations){

        reservaionHelpers.clear();
        if (intras.getIntras() != null && intras.getIntras().size() > 0) {
            for (IntradomainReservation res : intras.getIntras()) {
                if (getReservationByResID(res.getReservationId()) != null) {
                    ReservationHelper helper = new ReservationHelper();
                    helper.setBodID(res.getReservationId());
                    helper.setCapacity(res.getParams().getCapacity());
                    helper.setState(reservationStates[getReservationByResID(res.getReservationId()).getState()]);
                    helper.setStartTime(res.getParams().getStartTime());
                    helper.setEndTime(res.getParams().getEndTime());
                    helper.setPrevDomain(res.getReservedPath().getFirstLink().getStartInterface().getDomainId());
                    helper.setNextDomain(res.getReservedPath().getLastLink().getEndInterface().getDomainId());

                    reservaionHelpers.add(helper);
                }
            }
        }
        if (inter_reservations != null && inter_reservations.size() > 0) {
            for (ReservationType resType : inter_reservations) {
                if (!checkIfReservationIsAdded(resType.getBodID())) {
                    if (getReservationByResID(resType.getBodID()) != null) {

                        ReservationHelper helper = new ReservationHelper();
                        helper.setBodID(resType.getBodID());
                        helper.setCapacity(resType.getCapacity());				
                        helper.setState(reservationStates[getReservationByResID(resType.getBodID()).getState()]);
                        helper.setStartTime(resType.getStartTime());
                        helper.setEndTime(resType.getEndTime());
                        helper.setPrevDomain("-");
                        helper.setNextDomain("-");

                        reservaionHelpers.add(helper);
                    }
                }
            }
        }
        return reservaionHelpers;
    }

    public boolean checkIfReservationIsAdded(String boodID) {

        for (ReservationHelper r : reservaionHelpers) {
            if (boodID.equals(r.getBodID()))
                return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see net.geant.autobahn.autoBahnGUI.manager.Manager#handleTopologyChange(java.lang.String, boolean)
     */
    @Override
    public void handleTopologyChange(String idmParam, boolean deleteReservations) {
        // Cleanup LS first
        try {
            if (getLookupService() != null) {
                getLookupService().removeAbstractLinks();
            }
        } catch (LookupServiceException e1) {
            // Log the error but continue with IDMs restart
            logger.info("Could not clean up LS ", e1);
        }

        // Work on a copy of idms Map as it will change while IDMs are restarted
        Map<String, InterDomainManager> idmsCopy = new HashMap<String, InterDomainManager>();
        idmsCopy.putAll(idms);

        InterDomainManager manager = idmsCopy.get(idmParam);
        if (manager != null){
            logger.info("Restarting IDM that caused topology change: " + idmParam);
            try {
                manager.handleTopologyChange(deleteReservations, true);
            } catch (AdministrationException e) {
                // Log the problem and continue to the other IDMs
                logger.info(e.getMessage());
            }
        }
        
        // Parse through IDMs and get the first non-null result
        for (String idm : idmsCopy.keySet()) {
            manager = idmsCopy.get(idm);
            
            if(idm != null && !idm.equals(idmParam)) {
                logger.info("Restarting IDM " + idm);
                try {
                    manager.handleTopologyChange(deleteReservations, false);
                } catch (AdministrationException e) {
                    // Log the problem and continue to next IDM
                    logger.error(e.getMessage());
                }
            }
        }
        
    }

}
