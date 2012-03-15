package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;

import net.geant.autobahn.useraccesspoint.ServiceRequest;

public class ServiceRequestModel implements Serializable {

	private ServiceRequest service = new ServiceRequest();
	private String destinationDomain;
	
	
	public String getDestinationDomain() {
		return destinationDomain;
	}
	public void setDestinationDomain(String destinationDomain) {
		this.destinationDomain = destinationDomain;
	}
	public ServiceRequest getService() {
		return service;
	}
	public void setService(ServiceRequest service) {
		this.service = service;
	}
	

}
