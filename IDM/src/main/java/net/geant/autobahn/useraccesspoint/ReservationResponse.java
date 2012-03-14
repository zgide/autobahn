package net.geant.autobahn.useraccesspoint;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents the information about a reservation received in a response.
 * @author Michal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationResponse", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"bodID", "state", "message",
		"startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity", 
        "userInclude", "userExclude", "userVlanId", "mtu",
		"maxDelay",
		"resiliency", "bidirectional"
})
public class ReservationResponse {
	
	private String bodID;
	private State state;
	private String message;
	private String startPort,  endPort;
	private Calendar startTime, endTime;
	private Priority priority;
	private String description;
	private long capacity;
    private PathInfo userInclude;
    private PathInfo userExclude;
    private int userVlanId;
    private int mtu;
	private int maxDelay;
	private Resiliency resiliency;
	private boolean bidirectional;
		
	/**
	 * The ID that has been assigned to this reservation.
	 * 
	 * @return
	 */
	public String getBodID() {
		return bodID;
	}

	/**
	 * @param bodID
	 */
	public void setBodID(String bodID) {
		this.bodID = bodID;
	}

	/**
	 * The current state of the reservation.
	 * 
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * A message with optional information.
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * The starting port of the reservation.
	 * 
	 * @return
	 */
	public String getStartPort() {
		return startPort;
	}

	/**
	 * @param startPort
	 */
	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}

	/**
	 * The ending port of the reservation.
	 * 
	 * @return
	 */
	public String getEndPort() {
		return endPort;
	}

	/**
	 * @param endPort
	 */
	public void setEndPort(String endPort) {
		this.endPort = endPort;
	}

	/**
	 * The priority of the reservation.
	 * 
	 * @return
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * Textual description of the reservation.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The requested capacity for the reservation (in bps).
	 * 
	 * @return
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

    /**
     * A list of domains and links that should be included in the reservation
     * path.
     * 
     * @return possible object is {@link PathInfo }
     * 
     */
    public PathInfo getUserInclude() {
        return userInclude;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link PathInfo }
     *     
     */
    public void setUserInclude(PathInfo value) {
        this.userInclude = value;
    }

    /**
     * A list of domains and links that should be excluded from the reservation
     * path.
     * 
     * @return possible object is {@link PathInfo }
     * 
     */

    public PathInfo getUserExclude() {
        return userExclude;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link PathInfo }
     *     
     */
    public void setUserExclude(PathInfo value) {
        this.userExclude = value;
    }

    /**
     * A VLAN id that should be used by the reservation when traversing Ethernet
     * domains
     * 
     * @return
     */
    public int getUserVlanId() {
        return userVlanId;
    }

    /**
     * 
     * @param value
     */
    public void setUserVlanId(int value) {
        this.userVlanId = value;
    }

    /**
     * The minimum MTU available in bytes.
     * 
     * @return
     */
    public int getMtu(){
        return mtu;
    }

    /**
     * 
     * @param mtu
     */
    public void setMtu(int mtu){
        this.mtu = mtu;
    }

	/**
	 * The maximum allowed delay (in ms).
	 * 
	 * @return
	 */
	public int getMaxDelay() {
		return maxDelay;
	}

	/**
	 * @param maxDelay
	 */
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	/**
	 * The type of resiliency requested by the reservation.
	 * 
	 * @return
	 */
	public Resiliency getResiliency() {
		return resiliency;
	}

	/**
	 * @param resiliency
	 */
	public void setResiliency(Resiliency resiliency) {
		this.resiliency = resiliency;
	}

	/**
	 * @return
	 */
	public boolean isBidirectional() {
		return bidirectional;
	}

	/**
	 * @param bidirectional
	 */
	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	/**
	 * The starting time of the reservation.
	 * 
	 * @return
	 */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * The ending time of the reservation.
	 * 
	 * @return
	 */
	public Calendar getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}
}
