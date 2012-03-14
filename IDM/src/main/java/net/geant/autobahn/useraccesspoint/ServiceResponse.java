package net.geant.autobahn.useraccesspoint;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the information about a service (a collection of reservations)
 * received in a response.
 * 
 * @author Michal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceResponse", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"userName", "userHomeDomain", "userEmail",
		"reservations"
})
public class ServiceResponse {
		
	private String userName;
	private String userHomeDomain;
	private String userEmail;
	
	@XmlElement(required = true, nillable = true)
	private List<ReservationResponse> reservations;

	/**
	 * The name of the user that submitted the service.
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * The home domain of the user that submitted the service.
	 * 
	 * @return
	 */
	public String getUserHomeDomain() {
		return userHomeDomain;
	}

	/**
	 * @param userHomeDomain
	 */
	public void setUserHomeDomain(String userHomeDomain) {
		this.userHomeDomain = userHomeDomain;
	}

	/**
	 * The email of the user that submitted the service.
	 * 
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * The reservation request(s) that are included in this service.
	 * 
	 * @return
	 */
	public List<ReservationResponse> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations
	 */
	public void setReservations(List<ReservationResponse> reservations) {
		this.reservations = reservations;
	}
}
