/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package net.geant.autobahn.idcp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;
import org.apache.log4j.Logger;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;

/**
 * This class was generated by Apache CXF 2.4.2 2011-09-05T13:54:31.306+02:00
 * Generated source version: 2.4.2
 * 
 */

@javax.jws.WebService(serviceName = "OSCARSNotify", portName = "OSCARSNotify", targetNamespace = "http://oscars.es.net/OSCARS", 
		wsdlLocation = "file:etc/wsdl/IDCP/OSCARS-Notify.wsdl", endpointInterface = "net.geant.autobahn.idcp.OSCARSNotify")
public class OSCARSNotifyImpl implements OSCARSNotify {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private EndpointReferenceType createEndpoint(String address, String subscriptionId, String publisherId) { 
		
		EndpointReferenceType endpoint = new EndpointReferenceType();
		AttributedURIType uri = new AttributedURIType();
		uri.setValue(address);
		endpoint.setAddress(uri);
		ReferenceParametersType params = new ReferenceParametersType();
		params.setPublisherRegistrationId(publisherId);
		params.setSubscriptionId(subscriptionId);
		endpoint.setReferenceParameters(params);
		return endpoint;
	}
	
	private void printEndpoint(EndpointReferenceType endpoint) { 
		
		final String address = endpoint.getAddress().getValue() == null ? "not set" : endpoint.getAddress().getValue();
		String subId, pubId;
		if (endpoint.getReferenceParameters() == null) {
			subId = "not set";
		} else {
			subId = endpoint.getReferenceParameters().getSubscriptionId();
		}
		pubId = "not set";
		log.info("endpoint - address: " + endpoint.getAddress().getValue() + ", subId: " + subId);
		
	}
	
	private XMLGregorianCalendar toXmlCalendar(Date time) {

		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(time);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			log.info("could not create xml calendar from Date - " + time.toString());
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#subscribe(org.oasis_open.docs.wsn.b_2.Subscribe subscribeRequest)
	 */
	public org.oasis_open.docs.wsn.b_2.SubscribeResponse subscribe(org.oasis_open.docs.wsn.b_2.Subscribe subscribeRequest)
			throws InvalidMessageContentExpressionFault, UnsupportedPolicyRequestFault,	InvalidProducerPropertiesExpressionFault,
			UnacceptableInitialTerminationTimeFault, UnrecognizedPolicyRequestFault, ResourceUnknownFault,
			TopicNotSupportedFault, NotifyMessageNotSupportedFault,	InvalidFilterFault, TopicExpressionDialectUnknownFault,
			AAAFaultMessage, InvalidTopicExpressionFault, SubscribeCreationFailedFault {
		
		// POLICY
		if (subscribeRequest.getSubscriptionPolicy() != null) {
			log.info("subscribe - policy not supported");
			throw new UnsupportedPolicyRequestFault("policy not supported");
		}
		
		// FILTER
		FilterType filter = subscribeRequest.getFilter();
		if (filter == null) {
			log.info("subscribe - no filter");
			throw new InvalidFilterFault("no filter");
		}
		if (filter.getTopicExpression() == null) {
			log.info("subscribe - no topic expression");
			throw new InvalidFilterFault("no topic expression");
		}
			
		if (filter.getTopicExpression().size() > 1)
			log.info("subscribe - multiple filters defined");
		
		TopicExpressionType topicExpression = filter.getTopicExpression().get(0);
		
		final String topic = topicExpression.getValue();
		if (!topic.equals(Idcp.TOPIC_IDC)) {
			log.info("SRV.subscribe - topic: " + topic + " not supported");
			throw new TopicNotSupportedFault("only " + Idcp.TOPIC_INFO + " is supported"); 
		}
			
		// CONSUMER
		Calendar termTime = null;
		if (subscribeRequest.getInitialTerminationTime() != null) {
			termTime = subscribeRequest.getInitialTerminationTime().toGregorianCalendar();
			//log.info("SRV.subscribe - termTime: " + termTime.getTime().toString());
		} else {
			//log.info("SRV.subscribe - termTime not set");
		}
		
		final EndpointReferenceType consumer = subscribeRequest.getConsumerReference();
		if (consumer == null) {
			log.info("SRV.subscribe - no consumer");
			throw new SubscribeCreationFailedFault("consumer not set"); 
		}
			
		final String consumerUrl = consumer.getAddress().getValue();
		if (consumerUrl == null) {
			log.info("SRV.subscribe - consumer url not set");
			throw new SubscribeCreationFailedFault("consumer url not set"); 
		}
		
		String subscriptionId = null;
		final String publisherId = null; // should be null for our purposes
		ReferenceParametersType params = consumer.getReferenceParameters();
					
		if (params == null || params.getSubscriptionId() == null) {
			subscriptionId = Idcp.generateSubscriptionId();
			log.info("SRV.subscribe - consumer params not set, generating subId: " + subscriptionId);
		} else {
			subscriptionId = params.getSubscriptionId();
			log.info("SRV.subscribe - subId: " + subscriptionId);
		}

		// add this subscriber to manager
		final String notifierUrl = IdcpManager.getIdcpNotifyUrl();
		SubscriptionInfo subInfo = new SubscriptionInfo(consumerUrl, notifierUrl, subscriptionId, publisherId, topic, termTime);
		if (IdcpManager.addSubscriber(subInfo) == false) {
			log.info("SRV.subscribe - subscriptionId: " + subscriptionId + " already exists");
			throw new SubscribeCreationFailedFault("subscriptionId: " + subscriptionId + " already exists");
		}
		log.info("SRV.subscribe - consumer: " + consumerUrl + ", subId: " + subscriptionId);
		
		SubscribeResponse response = new SubscribeResponse();
		response.setCurrentTime(null); // can't set due to cxf issues
		response.setTerminationTime(null); // can't set due to cxf issues
		EndpointReferenceType responseEndpoint = createEndpoint(notifierUrl, subscriptionId, publisherId);
		//log.info("sending response back with notifier set to " + notifierUrl);
		response.setSubscriptionReference(responseEndpoint);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#resumeSubscription(org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscriptionRequest)
	 */
	public org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse resumeSubscription(org.oasis_open.docs.wsn.b_2.ResumeSubscription resumeSubscriptionRequest)
			throws ResourceUnknownFault, ResumeFailedFault, AAAFaultMessage {

		log.info("resumeSubscription - not supported");
		throw new ResumeFailedFault("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest)
	 */
	public org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse registerPublisher(org.oasis_open.docs.wsn.br_2.RegisterPublisher registerPublisherRequest)
			throws PublisherRegistrationRejectedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault,
			PublisherRegistrationFailedFault, TopicNotSupportedFault, InvalidTopicExpressionFault {
		
		log.info("registerPublisher - not supported");
		throw new PublisherRegistrationFailedFault("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#unsubscribe(org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest)
	 */
	public org.oasis_open.docs.wsn.b_2.UnsubscribeResponse unsubscribe(org.oasis_open.docs.wsn.b_2.Unsubscribe unsubscribeRequest)
			throws UnableToDestroySubscriptionFault, ResourceUnknownFault, AAAFaultMessage {
		
		final EndpointReferenceType consumer = unsubscribeRequest.getSubscriptionReference();
		if (consumer == null) { 
			log.info("SRV.unsubscribe - no consumer");
			throw new UnableToDestroySubscriptionFault("consumer not set");
		}
		
		if (consumer.getReferenceParameters() == null) {
			log.info("SRV.unsubscribe - no consumer params");
			throw new UnableToDestroySubscriptionFault("consumer params not set");
		}
		
		final String notifierUrl = consumer.getAddress().getValue();
		final String subscriptionId = consumer.getReferenceParameters().getSubscriptionId();
		final String publisherId = consumer.getReferenceParameters().getPublisherRegistrationId();
		
		log.info("SRV.unsubscribe - notifierUrl: " + notifierUrl + ", subscriptionId: " + subscriptionId);
				
		if (IdcpManager.removeSubscriber(notifierUrl, subscriptionId) == false)
			throw new UnableToDestroySubscriptionFault("subscriptionId: " + subscriptionId + " not found");

		// remove from manager
		UnsubscribeResponse response = new UnsubscribeResponse();
		final EndpointReferenceType responseEndpoint = createEndpoint(notifierUrl, subscriptionId, publisherId);
		response.setSubscriptionReference(responseEndpoint);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#pauseSubscription(org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscriptionRequest)
	 */
	public org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse pauseSubscription(org.oasis_open.docs.wsn.b_2.PauseSubscription pauseSubscriptionRequest)
			throws PauseFailedFault, ResourceUnknownFault, AAAFaultMessage {

		log.info("pauseSubscription - not supported");
		throw new PauseFailedFault("not supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#renew(org.oasis_open.docs.wsn.b_2.Renew renewRequest)
	 */
	public org.oasis_open.docs.wsn.b_2.RenewResponse renew(org.oasis_open.docs.wsn.b_2.Renew renewRequest)
			throws ResourceUnknownFault, UnacceptableTerminationTimeFault,	AAAFaultMessage {
		
		final EndpointReferenceType consumer = renewRequest.getSubscriptionReference();
		if (consumer == null) { 
			log.info("SRV.renew - no consumer");
			throw new ResourceUnknownFault("consumer not set");
		}
		
		if (consumer.getReferenceParameters() == null) {
			log.info("SRV.renew - no consumer params");
			throw new ResourceUnknownFault("consumer params not set");
		}
		
		final String notifierUrl = consumer.getAddress().getValue();
		final String subscriptionId = consumer.getReferenceParameters().getSubscriptionId();
		final String publisherId = consumer.getReferenceParameters().getPublisherRegistrationId();
		
		log.info("SRV.renew - notifierUrl: " + notifierUrl + ", subscriptionId: " + subscriptionId);
		
		// check for term if null
		Calendar termination = null;
		if (renewRequest.getTerminationTime() == null) {
			log.info("SRV.renew - termination time not set");
		} else {
			termination = renewRequest.getTerminationTime().toGregorianCalendar();
			log.info("SRV.renew - termination time: " + termination.getTime().toString());
		}
		
		// update calendar in subscription manager
		if (IdcpManager.updateSubscriber(notifierUrl, subscriptionId, termination) == false) {
			
			log.info("SRV.renew - failed updating subscriber");
			throw new ResourceUnknownFault("subscriptionId: " + subscriptionId + " not found");
		}
		
		RenewResponse response = new RenewResponse();
		response.setCurrentTime(null); // can't set due to cxf issues
		response.setTerminationTime(null); // can't set due to cxf issues
		final String abNotifierUrl = IdcpManager.getIdcpNotifyUrl();
		EndpointReferenceType endpointResponse = createEndpoint(abNotifierUrl, subscriptionId, publisherId);
		response.setSubscriptionReference(endpointResponse);
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#notify(org.oasis_open.docs.wsn.b_2.Notify notify)
	 */
	public void notify(org.oasis_open.docs.wsn.b_2.Notify notify) {
		
		log.info("notify arrived on notification interface, ignoring");
		// should be handled by OscarsNotifyOnlyImpl
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.geant.autobahn.idcp.OSCARSNotify#destroyRegistration(org.oasis_open.docs.wsn.br_2.DestroyRegistration destroyRegistrationRequest)
	 */
	public org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse destroyRegistration(org.oasis_open.docs.wsn.br_2.DestroyRegistration destroyRegistrationRequest)
			throws ResourceUnknownFault, ResourceNotDestroyedFault, AAAFaultMessage {
		
		log.info("destroyRegistration - not supported");
		throw new ResourceUnknownFault("not supported");
	}
}
