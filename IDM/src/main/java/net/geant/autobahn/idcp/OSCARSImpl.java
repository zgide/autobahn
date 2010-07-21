/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant.autobahn.idcp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.geant.autobahn.idcp.CreatePathContent;
import net.geant.autobahn.idcp.GetTopologyResponseContent;
import net.geant.autobahn.idcp.GlobalReservationId;
import net.geant.autobahn.idcp.ListReply;
import net.geant.autobahn.idcp.PathInfo;
import net.geant.autobahn.idcp.ResCreateContent;
import net.geant.autobahn.idcp.ResDetails;
import net.geant.autobahn.idcp.TeardownPathContent;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.reservation.HomeDomainReservation;
import net.geant.autobahn.reservation.Reservation;

import org.apache.log4j.Logger;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneDomainSignatureContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneHopContent;
import org.ogf.schema.network.topology.ctrlplane._20080828.CtrlPlaneTopologyContent;


/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:32:27 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@javax.jws.WebService(name = "OSCARS", serviceName = "OSCARS",
                      portName = "OSCARS",
                      targetNamespace = "http://oscars.es.net/OSCARS", 
                      wsdlLocation = "file:etc/wsdl/IDCP/OSCARS.wsdl" ,
                      endpointInterface = "net.geant.autobahn.idcp.OSCARS")
                      
public class OSCARSImpl implements OSCARS {

    private Logger log = Logger.getLogger(this.getClass());

    private HomeDomainReservation makeReservation(
            javax.xml.ws.Holder<java.lang.String> globalReservationId,
            long startTime,
            long endTime,
            int bandwidth,
            java.lang.String description,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.PathInfo> pathInfo,
            javax.xml.ws.Holder<java.lang.String> token,
            javax.xml.ws.Holder<java.lang.String> status)
    throws IOException {
    
        int capacity = bandwidth;
        // Time
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(startTime * 1000);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(endTime * 1000);
        
        // Ports
        String dest = pathInfo.value.getLayer2Info().getDestEndpoint();
        
        // Vlans
        String vlans = pathInfo.value.getLayer2Info().getSrcVtag().getValue();
        
        //CtrlPlaneHopContent[] hops = (CtrlPlaneHopContent[]) pathInfo.value.getPath().getHop().toArray();
        CtrlPlaneHopContent[] hops = new CtrlPlaneHopContent[pathInfo.value.getPath().getHop().size()];
		for (int i=0; i < pathInfo.value.getPath().getHop().size(); i++) {
			hops[i] = pathInfo.value.getPath().getHop().get(i);
		}
        System.out.println("Hops received: " + hops.length);
        
        CtrlPlaneHopContent srcHop = hops[hops.length - 2];
        String src = srcHop.getLinkIdRef();
        String resID = globalReservationId.value;
        //src = src.substring(src.indexOf(":link=") + 6);
        //dest = dest.substring(dest.indexOf(":link=") + 6);

        // TODO vlans, port, bodID?
        Oscars2Autobahn autobahn = new Oscars2Autobahn();
        HomeDomainReservation resp = null;
        resp = autobahn.createReservation(resID, capacity, start, end, src, dest, vlans);

        return resp;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#cancelReservation(net.geant.autobahn.idcp.GlobalReservationId  cancelReservation )*
     */
    public java.lang.String cancelReservation(net.geant.autobahn.idcp.GlobalReservationId cancelReservation) throws AAAFaultMessage , BSSFaultMessage    { 

        log.debug("cancelReservation.begin");

        String resID = cancelReservation.getGri();
        try {
            Oscars2Autobahn autobahn = new Oscars2Autobahn();
            autobahn.cancelReservation(resID);
        } catch (IOException e) {
            log.debug("cancelReservation exc - " + e.getMessage());
            throw new BSSFaultMessage(e.getMessage());
        }

        log.debug("cancelReservation.end");
        return resID;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#createReservation(java.lang.String  globalReservationId ,)long  startTime ,)long  endTime ,)int  bandwidth ,)java.lang.String  description ,)net.geant.autobahn.idcp.PathInfo  pathInfo ,)java.lang.String  token ,)java.lang.String  status )*
     */
    public void createReservation(javax.xml.ws.Holder<java.lang.String> globalReservationId,
            long startTime,
            long endTime,
            int bandwidth,
            java.lang.String description,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.PathInfo> pathInfo,
            javax.xml.ws.Holder<java.lang.String> token,
            javax.xml.ws.Holder<java.lang.String> status) throws AAAFaultMessage , BSSFaultMessage    { 

        try {
            makeReservation(globalReservationId,startTime,endTime,bandwidth,description,pathInfo,token,status);
        } catch (Exception e) {
            System.out.println("Exception makeReservation! " + e.getMessage());
            throw new BSSFaultMessage(e.getMessage(), e);
        }

    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#queryReservation(java.lang.String  gri ,)java.lang.String  globalReservationId ,)java.lang.String  login ,)java.lang.String  status ,)java.lang.Long  startTime ,)java.lang.Long  endTime ,)java.lang.Long  createTime ,)java.lang.Integer  bandwidth ,)java.lang.String  description ,)net.geant.autobahn.idcp.PathInfo  pathInfo )*
     */
    public void queryReservation(java.lang.String resId,
            javax.xml.ws.Holder<java.lang.String> globalReservationId,
            javax.xml.ws.Holder<java.lang.String> login,
            javax.xml.ws.Holder<java.lang.String> status,
            javax.xml.ws.Holder<java.lang.Long> startTime,
            javax.xml.ws.Holder<java.lang.Long> endTime,
            javax.xml.ws.Holder<java.lang.Long> createTime,
            javax.xml.ws.Holder<java.lang.Integer> bandwidth,
            javax.xml.ws.Holder<java.lang.String> description,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.PathInfo> pathInfo) throws AAAFaultMessage , BSSFaultMessage    { 

        log.debug("queryReservation.begin");

        ResDetails res = new ResDetails();
        res.setGlobalReservationId("reservation not found");
        res.setLogin("autobahn");
        res.setStatus("ok");
        res.setGlobalReservationId("aaa");

        try {
            // find reservation
            Oscars2Autobahn autobahn = new Oscars2Autobahn();
            Reservation resInfo = autobahn.queryReservation(resId);

            if (resInfo != null) {
                log.debug("found res: " + resId);
                res.setBandwidth((int) resInfo.getCapacity());
                res.setDescription(resInfo.getDescription());
                res.setGlobalReservationId(resInfo.getBodID());
                res.setLogin("DRAGON");
                res.setStatus(String.valueOf(resInfo.getState()));
                res.setStartTime(resInfo.getStartTime().getTimeInMillis());
                res.setEndTime(resInfo.getEndTime().getTimeInMillis());
            }

        } catch (Exception e) {
            log.info("query - " + e.getMessage());
        }
        log.debug("queryReservation.end");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#refreshPath(java.lang.String  token ,)java.lang.String  globalReservationId ,)java.lang.String  status )*
     */
    public void refreshPath(java.lang.String token,javax.xml.ws.Holder<java.lang.String> globalReservationId,javax.xml.ws.Holder<java.lang.String> status) throws AAAFaultMessage , BSSFaultMessage    { 
        // TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement "
                + this.getClass().getName() + "#refreshPath");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#teardownPath(java.lang.String  token ,)java.lang.String  globalReservationId ,)java.lang.String  status )*
     */
    public void teardownPath(java.lang.String token,javax.xml.ws.Holder<java.lang.String> globalReservationId,javax.xml.ws.Holder<java.lang.String> status) throws AAAFaultMessage , BSSFaultMessage    { 
        // TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement "
                + this.getClass().getName() + "#teardownPath");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#createPath(java.lang.String  token ,)java.lang.String  globalReservationId ,)java.lang.String  status )*
     */
    public void createPath(java.lang.String token,javax.xml.ws.Holder<java.lang.String> globalReservationId,javax.xml.ws.Holder<java.lang.String> status) throws AAAFaultMessage , BSSFaultMessage    { 
        // TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement "
                + this.getClass().getName() + "#createPath");
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#getNetworkTopology(net.geant.autobahn.idcp.GetTopologyContent  getNetworkTopology )*
     */
    public net.geant.autobahn.idcp.GetTopologyResponseContent getNetworkTopology(net.geant.autobahn.idcp.GetTopologyContent getNetworkTopology) throws AAAFaultMessage , BSSFaultMessage    { 

        log.debug("getNetworkTopology.begin");

        List<Link> links = new ArrayList<Link>();
        
        try {
            Oscars2Autobahn autobahn = new Oscars2Autobahn();
            links = autobahn.getTopology();
        } catch (IOException e) {
            throw new BSSFaultMessage(e.getMessage());
        }
        
        GetTopologyResponseContent cont = new GetTopologyResponseContent();
        CtrlPlaneTopologyContent ctrlTopology = new CtrlPlaneTopologyContent();
        ctrlTopology.setId("GEANT2");
        ctrlTopology.setIdcId("GEANT2");
        CtrlPlaneDomainSignatureContent[] ctrlSigns = new CtrlPlaneDomainSignatureContent[1];
        ctrlSigns[0] = new CtrlPlaneDomainSignatureContent();
        ctrlSigns[0].setDomainId("NOT SET");
        ctrlTopology.setDomainSignature(ctrlSigns);

        //TODO THE RIGHT CONVERTER JOHNIES
        CtrlPlaneDomainContent[] ctrlDomains = OscarsConverter
                .getOscarsTopology(links);
        ctrlTopology.setDomain(ctrlDomains);
        cont.setTopology(ctrlTopology);
        log.debug("getNetworkTopolgoy.finish");

        return cont;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#modifyReservation(java.lang.String  globalReservationId ,)long  startTime ,)long  endTime ,)int  bandwidth ,)java.lang.String  description ,)net.geant.autobahn.idcp.PathInfo  pathInfo )*
     */
    public net.geant.autobahn.idcp.ResDetails modifyReservation(
            java.lang.String globalReservationId,
            long startTime,
            long endTime,
            int bandwidth,
            java.lang.String description,
            net.geant.autobahn.idcp.PathInfo pathInfo) throws AAAFaultMessage , BSSFaultMessage    { 

        Reservation resInfo = new Reservation();
        resInfo.setBodID(globalReservationId);
        resInfo.setCapacity(bandwidth);
        resInfo.setDescription(description);
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(startTime);
        resInfo.setStartTime(start);
        Calendar end = Calendar.getInstance();
        end.setTimeInMillis(endTime);
        resInfo.setEndTime(end);

        try {
            Oscars2Autobahn autobahn = new Oscars2Autobahn();
            boolean succ = autobahn.modifyReservation(resInfo);
            if (succ == false) {
                throw new BSSFaultMessage("could not modify reservation");
            }
        } catch (IOException e) {
            throw new BSSFaultMessage(e.getMessage());
        }

        ResDetails rd = new ResDetails();
        rd.setBandwidth(bandwidth);
        rd.setDescription(description);
        rd.setEndTime(endTime);
        rd.setStartTime(startTime);
        rd.setGlobalReservationId(globalReservationId);
        rd.setLogin("no login");
        rd.setStatus("no status");

        return rd;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#notify(org.oasis_open.docs.wsn.b_2.Notify  notify )*
     */
    public void notify(org.oasis_open.docs.wsn.b_2.Notify notify) { 
        // TODO : fill this with the necessary business logic
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#forward(net.geant.autobahn.idcp.ForwardPayload  payload ,)java.lang.String  payloadSender ,)java.lang.String  contentType ,)net.geant.autobahn.idcp.CreateReply  createReservation ,)net.geant.autobahn.idcp.ModifyResReply  modifyReservation ,)java.lang.String  cancelReservation ,)net.geant.autobahn.idcp.ResDetails  queryReservation ,)net.geant.autobahn.idcp.ListReply  listReservations ,)net.geant.autobahn.idcp.CreatePathResponseContent  createPath ,)net.geant.autobahn.idcp.RefreshPathResponseContent  refreshPath ,)net.geant.autobahn.idcp.TeardownPathResponseContent  teardownPath )*
     */
    public void forward(net.geant.autobahn.idcp.ForwardPayload payload,
            java.lang.String payloadSender,
            javax.xml.ws.Holder<java.lang.String> contentType,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.CreateReply> createReservationArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.ModifyResReply> modifyReservationArg,
            javax.xml.ws.Holder<java.lang.String> cancelReservationArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.ResDetails> queryReservationArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.ListReply> listReservationsArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.CreatePathResponseContent> createPathArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.RefreshPathResponseContent> refreshPathArg,
            javax.xml.ws.Holder<net.geant.autobahn.idcp.TeardownPathResponseContent> teardownPathArg) throws AAAFaultMessage , BSSFaultMessage    { 

        ResCreateContent createReservation = payload.getCreateReservation();
        GlobalReservationId cancel = payload.getCancelReservation();
        CreatePathContent createPath = payload.getCreatePath();
        TeardownPathContent teardownPath = payload.getTeardownPath();

        if (createReservation != null) {
            HomeDomainReservation res = null;

            try {
                res = makeReservation(new javax.xml.ws.Holder<String>(createReservation.globalReservationId),
                        createReservation.getStartTime(),
                        createReservation.getEndTime(),
                        createReservation.getBandwidth(),
                        createReservation.getDescription(),
                        new javax.xml.ws.Holder<PathInfo>(createReservation.getPathInfo()),
                        null,null);
            } catch (IOException e) {
                throw new BSSFaultMessage(e.getMessage(), e);
            }

            String failure = res.getDescription();
            if (res != null && failure == null) {
            } else {
                System.out.println("Failure: " + failure);
                throw new BSSFaultMessage(failure);
            }

        }

        String cancelRes = null;
        if (cancel != null && cancel.getGri() != null
                && !"".equals(cancel.getGri())) {
            cancelRes = cancel.getGri();
        }

        if (cancelRes != null) {
            try {
                Oscars2Autobahn autobahn = new Oscars2Autobahn();
                autobahn.cancelReservation(cancelRes);
            } catch (IOException e) {
                log.debug("forward.ProxyException: ", e);
            }
        }

        if (createPath != null) {
            String resID = createPath.getGlobalReservationId();

            System.out.println("CreatePath received: " + resID);

            // Just ignore, create empty reply
        }

        if (teardownPath != null) {
            String resID = teardownPath.getGlobalReservationId();

            log.info("TeardownPath received: " + resID);

            // Just ignore, create empty reply
        }
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#listReservations(net.geant.autobahn.idcp.ListRequest  listReservations )*
     */
    public net.geant.autobahn.idcp.ListReply listReservations(net.geant.autobahn.idcp.ListRequest listReservations) throws AAAFaultMessage , BSSFaultMessage    { 

        log.debug("listReservations.begin");

        List<Reservation> reservations = new ArrayList<Reservation>();

        try {
            Oscars2Autobahn autobahn = new Oscars2Autobahn();
            reservations = autobahn.listReservations();
        } catch (IOException e) {
            throw new BSSFaultMessage(e.getMessage());
        }
        ResDetails[] resDetails = new ResDetails[reservations.size()];
        int index = 0;

        for (Reservation ri : reservations) {

            ResDetails rd = new ResDetails();
            rd.setBandwidth((int) ri.getCapacity());
            rd.setDescription(ri.getDescription());
            rd.setGlobalReservationId(ri.getBodID());
            rd.setLogin("no login");
            rd.setStatus(String.valueOf(ri.getState()));
            rd.setStartTime(ri.getStartTime().getTimeInMillis());
            rd.setEndTime(ri.getEndTime().getTimeInMillis());
            resDetails[index++] = rd;
        }

        ListReply list = new ListReply();
        list.setResDetails(resDetails);

        log.debug("listReservations.end");

        return list;
    }

    /* (non-Javadoc)
     * @see net.geant.autobahn.idcp.OSCARS#initiateTopologyPull(java.lang.String  topologyType )*
     */
    public java.lang.String initiateTopologyPull(java.lang.String topologyType) throws AAAFaultMessage , BSSFaultMessage    { 
        // TODO : fill this with the necessary business logic
        throw new java.lang.UnsupportedOperationException("Please implement "
                + this.getClass().getName() + "#initiateTopologyPull");
    }

}
