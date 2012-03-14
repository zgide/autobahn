package net.geant.autobahn.useraccesspoint;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Web service methods for placing new service, query reservations and
 * cancelling them
 * 
 * @author Michal
 */

@WebService(targetNamespace = "http://useraccesspoint.autobahn.geant.net/", name = "UserAccessPoint")
public interface UserAccessPoint {

    /**
     * Returns all domains in the global topology (not including IDCP clouds).
     * 
     * @return an array of domain names
     */
    @WebResult(name = "Domains")
    String[] getAllDomains();

    /**
     * Returns all non-client domains in the global topology (not including IDCP
     * clouds).
     * 
     * @return an array of domain names
     */
    @WebResult(name = "Domains")
    String[] getAllDomains_NonClient();

    /**
     * Returns all links in the global topology (not including links in IDCP
     * clouds).
     * 
     * @return an array of link identifiers
     */
    @WebResult(name = "Links")
    String[] getAllLinks();

    /**
     * Returns all links in the global topology that do not attach to a client
     * domain (not including links in IDCP clouds).
     * 
     * @return an array of link identifiers
     */
    @WebResult(name = "Links")
    String[] getAllLinks_NonClient();

    /**
     * Returns all client ports in the global topology. Does not include IDCP
     * ports.
     * 
     * @return a list of ports
     * @throws UserAccessPointException
     */
    @WebResult(name = "PortTypes")
    List<PortType> getAllClientPorts() throws UserAccessPointException;

    /**
     * Returns all client ports the are connected to this IDM.
     * 
     * @return a list of ports
     */
    @WebResult(name = "PortTypes")
    List<PortType> getDomainClientPorts();

    /**
     * Returns the actual IDCP ports in the global topology
     * 
     * @return a list of ports
     */
    @WebResult(name = "PortTypes")
    List<PortType> getIdcpPorts();

    /**
     * Submits a service for processing.
     * 
     * @param request
     *            The service request containing the service details
     * @return The ID of the submitted service, assigned by the IDM
     * @throws UserAccessPointException
     *             When the reservations in the service contain some erroneous
     *             information (e.g. start time is after the end time).
     */
    @WebResult(name = "serviceID")
    String submitService(@WebParam(name = "request") ServiceRequest request)
            throws UserAccessPointException;

    /**
     * Checks whether a reservation is possible. No actual reservation takes
     * place.
     * 
     * @param res
     *            The reservation to be checked
     * @return Whether the reservation is possible
     * @throws UserAccessPointException
     *             When the reservation contains some erroneous information
     *             (e.g. start time is after the end time).
     */
    @WebResult(name = "Possibility")
    boolean checkReservationPossibility(
            @WebParam(name = "request") ReservationRequest res)
            throws UserAccessPointException;

    /**
     * Submits a service for processing and simultaneously registers the method
     * caller at the IDM for receiving callbacks related to a service. The IDM
     * can use the list of registered callback clients in order to notify them
     * with updates about the service status.
     * 
     * @param request
     *            The service request containing the service details
     * @param url
     *            The location where callback calls should be made (normally the
     *            UI server that can handle the callback calls from the IDM)
     * @return The ID of the submitted service, assigned by the IDM
     * @throws UserAccessPointException
     *             When the reservations in the service contain some erroneous
     *             information (e.g. start time is after the end time).
     */
    @WebResult(name = "serviceID")
    String submitServiceAndRegister(
            @WebParam(name = "request") ServiceRequest request,
            @WebParam(name = "url") String url) throws UserAccessPointException;

    /**
     * Cancels a previously submitted service.
     * 
     * @param serviceID
     *            The ID of the service to be canceled
     * @throws UserAccessPointException
     *            When the serviceID specified at the request does not exist
     */
    void cancelService(@WebParam(name = "serviceID") String serviceID)
            throws UserAccessPointException;

    /**
     * This method is called by a client (e.g. UI) to modify a previously
     * submitted service.
     * 
     * @param request
     *            The modification request containing the modified reservation
     *            details
     */
    void modifyReservation(@WebParam(name = "request") ModifyRequest request);

    /**
     * Retrieves information about a service and its reservations.
     * 
     * @param serviceID
     *            The ID of the service for which information will be retrieved
     * @return The response with the information about the requested service
     * @throws UserAccessPointException
     *            When the serviceID specified at the request does not exist
     */
    @WebResult(name = "ServiceResponse")
    ServiceResponse queryService(@WebParam(name = "serviceID") String serviceID)
            throws UserAccessPointException;

    /**
     * Registers the method caller (e.g. UI) at the IDM for receiving callbacks
     * related to a service. The IDM can use the list of registered callback
     * clients in order to notify them with updates about the service status.
     * 
     * @param serviceID
     *            The ID of the service for which callback calls will be made
     * @param url
     *            The location where callback calls should be made (normally the
     *            UI server that can handle the callback calls from the IDM)
     */
    public void registerCallback(
            @WebParam(name = "serviceID") String serviceID,
            @WebParam(name = "url") String url);
}
