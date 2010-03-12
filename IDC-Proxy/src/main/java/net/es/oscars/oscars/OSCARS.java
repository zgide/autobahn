
package net.es.oscars.oscars;

import javax.jws.WebParam.Mode;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:32:27 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebService(targetNamespace = "http://oscars.es.net/OSCARS", name = "OSCARS")

public interface OSCARS {

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://oscars.es.net/OSCARS", partName = "cancelReservationResponse", name = "cancelReservationResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/cancelReservation")
    public java.lang.String cancelReservation(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", partName = "cancelReservation", name = "cancelReservation")
        net.es.oscars.oscars.GlobalReservationId cancelReservation
    ) throws AAAFaultMessage, BSSFaultMessage;

    @RequestWrapper(localName = "createReservation", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.ResCreateContent")
    @ResponseWrapper(localName = "createReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.CreateReply")
    @WebMethod(action = "http://oscars.es.net/OSCARS/createReservation")
    public void createReservation(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId", mode = Mode.INOUT)
        javax.xml.ws.Holder<java.lang.String> globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "startTime")
        long startTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "endTime")
        long endTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "bandwidth")
        int bandwidth,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "description")
        java.lang.String description,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "pathInfo", mode = Mode.INOUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.PathInfo> pathInfo,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "token", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> token,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "status", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> status
    ) throws AAAFaultMessage, BSSFaultMessage;

    @RequestWrapper(localName = "queryReservation", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.GlobalReservationId")
    @ResponseWrapper(localName = "queryReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.ResDetails")
    @WebMethod(action = "http://oscars.es.net/OSCARS/queryReservation")
    public void queryReservation(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "gri")
        java.lang.String gri,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "login", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> login,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "status", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> status,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "startTime", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.Long> startTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "endTime", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.Long> endTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "createTime", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.Long> createTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "bandwidth", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.Integer> bandwidth,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "description", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> description,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "pathInfo", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.PathInfo> pathInfo
    ) throws AAAFaultMessage, BSSFaultMessage;

    @RequestWrapper(localName = "refreshPath", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.RefreshPathContent")
    @ResponseWrapper(localName = "refreshPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.RefreshPathResponseContent")
    @WebMethod(action = "http://oscars.es.net/OSCARS/refreshPath")
    public void refreshPath(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "token")
        java.lang.String token,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId", mode = Mode.INOUT)
        javax.xml.ws.Holder<java.lang.String> globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "status", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> status
    ) throws AAAFaultMessage, BSSFaultMessage;

    @RequestWrapper(localName = "teardownPath", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.TeardownPathContent")
    @ResponseWrapper(localName = "teardownPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.TeardownPathResponseContent")
    @WebMethod(action = "http://oscars.es.net/OSCARS/teardownPath")
    public void teardownPath(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "token")
        java.lang.String token,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId", mode = Mode.INOUT)
        javax.xml.ws.Holder<java.lang.String> globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "status", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> status
    ) throws AAAFaultMessage, BSSFaultMessage;

    @RequestWrapper(localName = "createPath", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.CreatePathContent")
    @ResponseWrapper(localName = "createPathResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.CreatePathResponseContent")
    @WebMethod(action = "http://oscars.es.net/OSCARS/createPath")
    public void createPath(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "token")
        java.lang.String token,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId", mode = Mode.INOUT)
        javax.xml.ws.Holder<java.lang.String> globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "status", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> status
    ) throws AAAFaultMessage, BSSFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://oscars.es.net/OSCARS", partName = "getNetworkTopologyResponse", name = "getNetworkTopologyResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/getNetworkTopology")
    public net.es.oscars.oscars.GetTopologyResponseContent getNetworkTopology(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", partName = "getNetworkTopology", name = "getNetworkTopology")
        net.es.oscars.oscars.GetTopologyContent getNetworkTopology
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(targetNamespace = "http://oscars.es.net/OSCARS", name = "reservation")
    @RequestWrapper(localName = "modifyReservation", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.ModifyResContent")
    @ResponseWrapper(localName = "modifyReservationResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.ModifyResReply")
    @WebMethod(action = "http://oscars.es.net/OSCARS/modifyReservation")
    public net.es.oscars.oscars.ResDetails modifyReservation(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "globalReservationId")
        java.lang.String globalReservationId,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "startTime")
        long startTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "endTime")
        long endTime,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "bandwidth")
        int bandwidth,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "description")
        java.lang.String description,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "pathInfo")
        net.es.oscars.oscars.PathInfo pathInfo
    ) throws AAAFaultMessage, BSSFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @Oneway
    @WebMethod(operationName = "Notify", action = "http://oscars.es.net/OSCARS/Notify")
    public void notify(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "Notify", name = "Notify")
        org.oasis_open.docs.wsn.b_2.Notify notify
    );

    @RequestWrapper(localName = "forward", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.Forward")
    @ResponseWrapper(localName = "forwardResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.ForwardReply")
    @WebMethod(action = "http://oscars.es.net/OSCARS/forward")
    public void forward(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "payload")
        net.es.oscars.oscars.ForwardPayload payload,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "payloadSender")
        java.lang.String payloadSender,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "contentType", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> contentType,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "createReservation", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.CreateReply> createReservation,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "modifyReservation", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.ModifyResReply> modifyReservation,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "cancelReservation", mode = Mode.OUT)
        javax.xml.ws.Holder<java.lang.String> cancelReservation,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "queryReservation", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.ResDetails> queryReservation,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "listReservations", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.ListReply> listReservations,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "createPath", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.CreatePathResponseContent> createPath,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "refreshPath", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.RefreshPathResponseContent> refreshPath,
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "teardownPath", mode = Mode.OUT)
        javax.xml.ws.Holder<net.es.oscars.oscars.TeardownPathResponseContent> teardownPath
    ) throws AAAFaultMessage, BSSFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://oscars.es.net/OSCARS", partName = "listReservationsResponse", name = "listReservationsResponse")
    @WebMethod(action = "http://oscars.es.net/OSCARS/listReservations")
    public net.es.oscars.oscars.ListReply listReservations(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", partName = "listReservations", name = "listReservations")
        net.es.oscars.oscars.ListRequest listReservations
    ) throws AAAFaultMessage, BSSFaultMessage;

    @WebResult(targetNamespace = "http://oscars.es.net/OSCARS", name = "resultMsg")
    @RequestWrapper(localName = "initiateTopologyPull", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.InitiateTopologyPullContent")
    @ResponseWrapper(localName = "initiateTopologyPullResponse", targetNamespace = "http://oscars.es.net/OSCARS", className = "net.es.oscars.oscars.InitiateTopologyPullResponseContent")
    @WebMethod(action = "http://oscars.es.net/OSCARS/initiateTopologyPull")
    public java.lang.String initiateTopologyPull(
        @WebParam(targetNamespace = "http://oscars.es.net/OSCARS", name = "topologyType")
        java.lang.String topologyType
    ) throws AAAFaultMessage, BSSFaultMessage;
}
