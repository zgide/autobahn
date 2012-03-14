package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a service request, which is a collection of reservation requests.
 * 
 * @author Michal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ServiceRequest", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"userName", "userHomeDomain", "userEmail", "justification",
		"reservations"
})
public class ServiceRequest implements Serializable {
	
	private static final long serialVersionUID = -2744900026489569640L;
	
	private String userName;
	private String userHomeDomain;
	private String userEmail;
	private String justification;
	
	@XmlElement(required = true, nillable = true)
	private List<ReservationRequest> reservations = new ArrayList<ReservationRequest>();

	/**
	 * Returns the name of the user submitting the service.
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set the name of the user submitting the service.
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Returns home domain of the user submitting the service.
	 * 
	 * @return
	 */
	public String getUserHomeDomain() {
		return userHomeDomain;
	}

	/**
	 * Set the home domain of the user submitting the service.
	 * 
	 * @param userHomeDomain
	 */
	public void setUserHomeDomain(String userHomeDomain) {
		this.userHomeDomain = userHomeDomain;
	}

	/**
	 * Returns the email of the user submitting the service.
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * Sets the email of the user submitting the service.
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * Returns a textual description of the service.
	 * 
	 * @return the justification
	 */
	public String getJustification() {
		return justification;
	}

	/**
	 * Set a free-text description of the service.
	 * 
	 * @param justification
	 */
	public void setJustification(String justification) {
		this.justification = justification;
	}

	/**
	 * Returns the reservation request(s) included in this service.
	 * 
	 * @return
	 */
	public List<ReservationRequest> getReservations() {
		return reservations;
	}

	/**
	 * Set the reservation request(s) to be included in this service.
	 * 
	 * @param reservations
	 */
	public void setReservations(List<ReservationRequest> reservations) {
		this.reservations = reservations;
	}
	
	@Override
	public String toString() {
		String res = "Service request: \n"; 
		res += "  Justification: " + getJustification() + "\n";
		res += "  Number of reservations: " + getReservations().size() + "\n";
		
        int num = 1;
        for(ReservationRequest r : getReservations()) {
            res += "  Reservation #" + (num++) + ":\n" + r + "\n";
        }
        
        return res;
	}
}
