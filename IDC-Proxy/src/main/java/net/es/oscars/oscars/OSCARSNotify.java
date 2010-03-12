
package net.es.oscars.oscars;

import javax.jws.WebParam.Mode;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebResult;

/**
 * This class was generated by Apache CXF (incubator) 2.0.3-incubator
 * Fri Feb 12 15:44:06 EET 2010
 * Generated source version: 2.0.3-incubator
 * 
 */

@WebService(targetNamespace = "http://oscars.es.net/OSCARS", name = "OSCARSNotify")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)

public interface OSCARSNotify {

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "SubscribeResponse", name = "SubscribeResponse")
    @WebMethod(operationName = "Subscribe", action = "http://oscars.es.net/OSCARS/Subscribe")
    public org.oasis_open.docs.wsn.b_2.SubscribeResponse subscribe(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "SubscribeRequest", name = "Subscribe")
        org.oasis_open.docs.wsn.b_2.Subscribe subscribeRequest
    ) throws InvalidMessageContentExpressionFault, UnsupportedPolicyRequestFault, InvalidProducerPropertiesExpressionFault, UnacceptableInitialTerminationTimeFault, UnrecognizedPolicyRequestFault, ResourceUnknownFault, TopicNotSupportedFault, NotifyMessageNotSupportedFault, InvalidFilterFault, TopicExpressionDialectUnknownFault, AAAFaultMessage, InvalidTopicExpressionFault, SubscribeCreationFailedFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "ResumeSubscriptionResponse", name = "ResumeSubscriptionResponse")
    @WebMethod(operationName = "ResumeSubscription", action = "http://oscars.es.net/OSCARS/ResumeSubscription")
    public org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse resumeSubscription(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "ResumeSubscriptionRequest", name = "ResumeSubscription")
        org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscriptionRequest
    ) throws ResourceUnknownFault, ResumeFailedFault, AAAFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/br-2", partName = "RegisterPublisherResponse", name = "RegisterPublisherResponse")
    @WebMethod(operationName = "RegisterPublisher", action = "http://oscars.es.net/OSCARS/RegisterPublisher")
    public org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisher(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/br-2", partName = "RegisterPublisherRequest", name = "RegisterPublisher")
        org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest
    ) throws PublisherRegistrationRejectedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, PublisherRegistrationFailedFault, TopicNotSupportedFault, InvalidTopicExpressionFault;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "UnsubscribeResponse", name = "UnsubscribeResponse")
    @WebMethod(operationName = "Unsubscribe", action = "http://oscars.es.net/OSCARS/Unsubscribe")
    public org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribe(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "UnsubscribeRequest", name = "Unsubscribe")
        org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest
    ) throws UnableToDestroySubscriptionFault, ResourceUnknownFault, AAAFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "PauseSubscriptionResponse", name = "PauseSubscriptionResponse")
    @WebMethod(operationName = "PauseSubscription", action = "http://oscars.es.net/OSCARS/PauseSubscription")
    public org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse pauseSubscription(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "PauseSubscriptionRequest", name = "PauseSubscription")
        org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscriptionRequest
    ) throws PauseFailedFault, ResourceUnknownFault, AAAFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "RenewResponse", name = "RenewResponse")
    @WebMethod(operationName = "Renew", action = "http://oscars.es.net/OSCARS/Renew")
    public org.oasis_open.docs.wsn.b_2.RenewResponse renew(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "RenewRequest", name = "Renew")
        org.oasis_open.docs.wsn.b_2.Renew renewRequest
    ) throws ResourceUnknownFault, UnacceptableTerminationTimeFault, AAAFaultMessage;

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @Oneway
    @WebMethod(operationName = "Notify", action = "http://oscars.es.net/OSCARS/Notify")
    public void notify(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/b-2", partName = "Notify", name = "Notify")
        org.oasis_open.docs.wsn.b_2.Notify notify
    );

    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    @WebResult(targetNamespace = "http://docs.oasis-open.org/wsn/br-2", partName = "DestroyRegistrationResponse", name = "DestroyRegistrationResponse")
    @WebMethod(operationName = "DestroyRegistration", action = "http://oscars.es.net/OSCARS/DestroyRegistration")
    public org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse destroyRegistration(
        @WebParam(targetNamespace = "http://docs.oasis-open.org/wsn/br-2", partName = "DestroyRegistrationRequest", name = "DestroyRegistration")
        org.oasis_open.docs.wsn.br_2.DestroyRegistration destroyRegistrationRequest
    ) throws ResourceUnknownFault, ResourceNotDestroyedFault, AAAFaultMessage;
}
