package net.geant.autobahn.autoBahnGUI.manager;

import java.util.List;
import java.util.Map;

import net.geant.autobahn.aai.AccessPolicy;
import net.geant.autobahn.administration.KeyValue;
import net.geant.autobahn.administration.ReservationType;
import net.geant.autobahn.administration.ServiceType;
import net.geant.autobahn.autoBahnGUI.model.AccessPolicyFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntraCalendarFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntraPathsFormModel;
import net.geant.autobahn.autoBahnGUI.model.IntrasFormModel;
import net.geant.autobahn.autoBahnGUI.model.LogsFormModel;
import net.geant.autobahn.autoBahnGUI.model.ReservatiomDepandentOnTimezone;
import net.geant.autobahn.autoBahnGUI.model.ReservationTest;
import net.geant.autobahn.autoBahnGUI.model.ServiceRequestModel;
import net.geant.autobahn.autoBahnGUI.model.ServicesFormModel;
import net.geant.autobahn.autoBahnGUI.model.SettingsFormModel;
import net.geant.autobahn.autoBahnGUI.model.StatisticsFormModel;
import net.geant.autobahn.lookup.LookupService;
import net.geant.autobahn.useraccesspoint.PortType;
import net.geant.autobahn.useraccesspoint.ReservationRequest;
import net.geant.autobahn.useraccesspoint.ServiceRequest;
import net.geant.autobahn.useraccesspoint.UserAccessPointException;
/**
 * Interface for WEB GUI Manager
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public interface Manager {

    /**
     * Gets array of all string representations of  Reservation states members
     * @return array with string Reservation types members 
     */
    public String[] getReservationStates();

    /**
     * Gets array of all string descriptions of  Reservation states members
     * @return array with string Reservation types descriptions
     */
    public String[] getReservationDescriptions();

    /**
     * Gets array of all string representations of  ReservationService states members
     * @return array with string ReservationService types members 
     */
    public String[] getServiceStates();

    /**
     * Gets list of all of IDMs names registered in WEB GUI
     * @return array with string ReservationService types members 
     */
    public List<String> getAllInterdomainManagers ();

    /**
    * Gets map of homeDomainID and reservationID from all IDMs registered in WEB GUI
    * @return map of reservationID and homeDomainID  
    */
    public Map<String, String> getServicesForAllInterDomainManagers();

    public IntrasFormModel getIntraReservationsForInterDomainManager(String idm);

    public ReservationType getReservation (String idm, String resId);

    public IntraPathsFormModel getIntraPathsForInterDomainManager(String idm);

    public IntraCalendarFormModel getIntraCalendarForInterDomainManager(String idm);

    /**
     * Gets list of all IDMs registered in WEB GUI
     * @return list of InterDomain  types members 
     */
    public List<InterDomainManager> getInterDomainManagers();

    /**
     * Check if UserAccessPoint web service interface for IDM is accessible
     * @return true if interface is working 
     */
    public boolean checkUserAccessPointConnection (String idm);

    /**
     * Check if Administration web service interface for IDM is accessible
     * @return true if interface is accessible 
     */
    public boolean checkAdminstrationConnection (String idm);

    /**
     * Submits the ServiceRequest in IDM by the UserAccessPoint interface
     * @throws UserAccessPointException_Exception if some connection problem appears or user cannot request service
     * @throws ManagerException 
     */
    public String submitServiceAtInterDomainManager (String idm, ServiceRequest request)throws UserAccessPointException, ManagerException;

    /**
     * Gets list of configuration properties for specified IDM 
     * 
     * @param idm identifier name of the IDM
     * @return	list of KeyValue
     */
    public List<KeyValue> getPropertiesForInterDomainManager (String idm);

    /**
     * Sets list of configuration properties for specified IDM registerd in WEB GUI 
     * 
     * @param idm identifier of the IDM
     * @param list of KeyValue
     */
    public void setPropertiesForInterDomainManager (String idm, List<KeyValue> properties);

    /**
     * Gets the access policy for specified IDM
     * 
     * @param idm
     *            identifier name of the IDM
     * @return
     */
    public AccessPolicyFormModel getAccessPolicyForInterDomainManager(String idm);

    /**
     * Sets list of configuration properties for specified IDM
     * 
     * @param idm
     *            identifier of the IDM
     * @param the
     *            new AccessPolicy to be set
     */
    public void setAccessPolicyForInterDomainManager(String idm, AccessPolicy acp);
	
    /**
     * Adds the rule with the supplied attributes to the specified IDM
     * 
     * @param idm
     * @param role
     * @param email
     * @param projMem
     * @param org
     */
    public void addRuleForIDM(String idm, String role, String email, String projMem, String org);

    /**
     * Removes the rule with the supplied attributes from the specified IDM
     * 
     * @param idm
     * @param role
     * @param email
     * @param projMem
     * @param org
     */
    public void removeRuleForIDM(String idm, String role, String email, String projMem, String org);

    /**
     * Gets list of all services for specified IDM registered in WEB GUI
     * @param idm identifier of the IDM
     * @return list of Service objects  
     */
    public List<ServiceType> getServicesFromInterDomainManager (String idm);

    /**
     * Gets submitted services from specified IDM
     * 
     * @param idm
     *            identifier of the IDM - if null provides list of services from first IDM
     * @return ServicesFormModel
     */
    public ServicesFormModel getSubmitedServicesInIDM(String idm);

    /**
     * Gets specified service from specified IDM registered in WEB GUI
     * 
     * @param idm identifier of the IDM 
     * @param serviceId identifier of the search service
     * @return Service if exist, null if not
     */
    public ServiceType getServiceFromInterDomainManager (String idm, String serviceId);

    /**
     * Cancel specified service in specified IDM registered in WEB GUI
     *  
     * @param idm identifier of the IDM 
     * @param serviceId identifier of the search service
     * @throws UserAccessPointException_Exception if some connection problem or cancelling error appear
     */
    public void cancelServiceInInterDomainManager(String idm,String serviceId) throws UserAccessPointException;

    /**
     * Gets specified IDM registered in WEB GUI
     * @param idm identifier of the IDM
     * @return InterDomainManager object if exist, if not null
     */
    public InterDomainManager getInterDomainManager (String idm);

    /**
     * Gets logged information from specified IDM registered in WEB GUI
     * for specified reservation
     * @param idm identifier of the IDM
     * @param resId Only gets logs for specific reservation. If null, returns all logs
     * @return String log information
     */
    public String getLogsInterDomainManager(String idm, boolean b, boolean c, String resId);
    
    /**
     * Gets all IDCP port names in all IDM registered in WEB GUI
     * 
     * @param idm - preferred IDM to get ports from
     * @return list of  ports names
     */
    public List<PortType> getAllIdcpPorts (String idmIdentifier);

    /**
     * Gets all IDCP port names in all IDM registered in WEB GUI
     * @return list of  ports names
     */
    public List<PortType> getAllIdcpPorts ();

    /**
     * Gets all client ports
     * If the supplied parameter is not null, it tries with that IDM,
     * otherwise it searches for any registered IDM that can return a valid result
     * Does not include any IDCP ports
     * 
     * @param idm - preferred IDM to get ports from
     * @return list of ports names with associated friendly ones
     * @throws UserAccessPointException 
     */
    public List<PortType> getAllClientPorts (String idmIdentifier) throws UserAccessPointException;

    /**
     * Gets all client ports
     * It searches for any registered IDM that can return a valid result
     * 
     * @param idmIdentifier
     * @return list of port identifiers
     * @throws UserAccessPointException 
     */
    public List<PortType> getAllClientPorts() throws UserAccessPointException;

    /**
     * Gets all port names (with associated friendly ones from LS) in all IDM
     * registered in WEB GUI
     * Also gets IDCP ports
     * 
     * @param idm - preferred IDM to get ports from
     * @return list of ports names with associated friendly ones
     * @throws UserAccessPointException 
     */
    public List<PortType> getAllFriendlyAndIdcpPorts (String idm) throws UserAccessPointException;

    /**
     * Gets all domain names
     * @return list of domains names
     */
    public List<String> getAllDomains();

    /**
     * Gets all non-client domain names
     * @return list of domains names
     */
    public List<String> getAllDomains_NonClient();

    /**
     * Gets all link names
     * @return list of links names
     */
    public List<String> getAllLinks();

    /**
     * Gets all link names that do not attach to a client domain
     * @return list of links names
     */
    public List<String> getAllLinks_NonClient();

    /**
     * Gets list of client ports managed by specified IDM
     * 
     * @param idm
     *            identifier of the IDM
     * @return list of client ports
     */
    public List<PortType> getInterDomainManagerPorts(String idm);

    /**
     * Gets time period after with the  registered earlier IDM is mark as not accessible
     * @return
     */
    public long getTearDownTime();

    /**
     * Checks if request reservation is possible to schedule
     * @param idm identifier of the IDM
     * @param request reservation request
     * @return true if reservation is possible to schedule
     */
    public ReservationTest  checkReservationPossibility(String idm,ReservationRequest request)throws UserAccessPointException;

    /**
     * Gets ServiceRequest template for service request form
     * @return ServiceRequest template
     */
    public ServiceRequestModel getServiceRequestTemplate();

    /**
     * Gets ReservationRequest template for reservation request form 
     * @return ReservationRequest template
     */
    public ReservatiomDepandentOnTimezone getReservationRequestTemplate();

    public List<String> getReservationPriorities();

    public List<String> getReservationResiliencies();

    /**
     * Gets list of time zones
     * @return	list of time zones
     */
    public List<String> getTimezones ();

    /**
     * Gets default time zone for Web GUI
     * @return time zone name
     */
    public String getTimezone ();

    /**
     * Shifts the request start/end times by as many hours as the difference
     * between the supplied timezone parameter and the start/end times timezone.
     * 
     * @param timezone
     * @param request
     */
    public void convertTimeToApplicationTimezone(String timezone, ReservationRequest request);

    /**
     * Gets SettingFormModel used in IDM setting view
     * @param idm identifier of IDM
     * @return	SettingsFormModel
     */
    public SettingsFormModel getSettingsForInterDomainManager  (String idm);

    /**
     * Gets LogsFormModel used in IDM logs view
     * @param idm identifier of IDM
     * @param resId if null, all logs are returned
     * @return LogsFormModel
     */
    public LogsFormModel getLogsForInterDomainManager (String idm, String resId);

    /**
     * Gets StatisticsFormModel used in IDM setting view
     * @param idm identifier of IDM
     * @return  StatisticsFormModel
     */
    public StatisticsFormModel getStatisticsForInterDomainManager (String idm);

    public LookupService getLookupService();

    public void checkIDMavailability();

    public List<ServiceType> sortServicesByBodyID(List<ServiceType> list);

    public String setParameter(String param);

    public List<String> getReservationModes();

    public void convertCapacity(ReservationRequest request);

    /**
     * Get the friendly port name for the specified port
     * 
     * @param port identifier
     * @return friendly port name, null if not found
     * @throws UserAccessPointException
     */
    public String getFriendlyNamePort(String port) throws UserAccessPointException;

    /**
     * Gets list of domain links no clients
     * 
     * @return list of links
     */
    public List<LinkMap> getAllDomainLinks();

    /**
     * Gets IntrasFormModel used in IDM setting view
     * @param idm identifier of IDM
     * @param state identifier of reservation state
     * @return IntrasFormModel
     */
    public IntrasFormModel getIntraReservationsForIDMWithSelectedReservationState(String idm, String state);
    /**
     * Get reservation by reservation id
     * @param resID identifier of reservation
     * @return reservation type
     */
    public ReservationType getReservationByResID(String resID);

    /**
     * Get reservation type for selected domain name and reservation state
     * @param idm identifier of domain name
     * @param state identifier of reservation state
     * @return List of reservations type
     */
    public List<ReservationType> getInterReservationsForIDMWithSelectedReservationState(String idm, String resState);

    /**
     * Get reservation helper for selected domain name and reservation state
     * @param intras identifier of intra-domain reservations
     * @param inter-reservations identifier of list of inter-domain reservations
     * @return List of reservations helper
     */
    public List<ReservationHelper> getDomainReservations(IntrasFormModel intras, List<ReservationType> inter_reservations);

    /**
     * Get reservation type for selected domain name
     * @param idm identifier of domain name
     * @return List of reservations type
     */
    public List<ReservationType> getDomainReservations(String idm);

    public boolean checkTopology(String idm);

    /**
     * Handles a topology update event by restarting all IDMs connected to the
     * Portal, starting with the one that caused the topology update event
     * 
     * @param idmParam - the IDM that caused the topology update event
     * @param deleteReservations - whether to forcefully delete any existing 
     *          reservations in the restarted IDMs. If set to false and reservations
     *          are found, the restart at the respective IDM will not happen.
     */
    public void handleTopologyChange(String idmParam, boolean deleteReservations);
}
