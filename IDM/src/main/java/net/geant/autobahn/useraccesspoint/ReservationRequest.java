package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.aai.UserAuthParameters;

/**
 * Describes a single reservation request.
 * 
 * @author Michal
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationRequest", namespace="useraccesspoint.autobahn.geant.net", propOrder={
		"startPort", "endPort", "startTime", "endTime",
		"priority", "description", "capacity",
		"userInclude", "userExclude", "mtu",
		"maxDelay", "resiliency", "bidirectional", "processNow",
        "authParameters"
})
public class ReservationRequest implements Serializable {
	
	private static final long serialVersionUID = -612896116488675810L;
	
	private PortType startPort;
	private PortType endPort;
	private Calendar startTime;
	private Calendar endTime;
	private Priority priority;
	private String description;
	private long capacity;
    private PathInfo userInclude;
    private PathInfo userExclude;
    private int mtu;
	private int maxDelay;
	private Resiliency resiliency;
	private boolean bidirectional;
	private boolean processNow;

	//fields added for passing friendly names between the webpages of client portal
	//no need to be serialized and passed through the web service interface
	@XmlTransient
	private String startPortFriendlyName;
	@XmlTransient
    private String endPortFriendlyName;

    private UserAuthParameters authParameters=new UserAuthParameters();
    
	public ReservationRequest() {
        this.userInclude = new PathInfo();
        this.userExclude = new PathInfo();
        
        this.startPort = new PortType();
        this.endPort = new PortType();
	}

    /**
     * Returns the starting port of the reservation.
     * 
     * @return
     */
	public PortType getStartPort() {
		return startPort;
	}

    /**
     * Set the starting port of the reservation.
     * 
     * @param startPort
     */
	public void setStartPort(PortType startPort) {
		this.startPort = startPort;
	}


    /**
     * Returns the ending port of the reservation.
     * 
     * @return
     */
	public PortType getEndPort() {
		return endPort;
	}

    /**
     * Set the ending port of the reservation.
     * 
     * @param endPort
     */
	public void setEndPort(PortType endPort) {
		this.endPort = endPort;
	}

    /**
     * Returns the starting time of the reservation.
     * 
     * @return
     */
	public Calendar getStartTime() {
		return startTime;
	}

	/**
     * Set the starting time of the reservation.
     * 
     * @param startTime
     */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

    /**
     * Returns the ending time of the reservation.
     * 
     * @return
     */
	public Calendar getEndTime() {
		return endTime;
	}

    /**
     * Set the ending time of the reservation.
     * 
     * @param endTime
     */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

    /**
     * Returns the priority of the reservation.
     * 
     * @return
     */
	public Priority getPriority() {
		return priority;
	}

    /**
     * Set the priority of the reservation.
     * 
     * @param priority
     */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

    /**
     * Returns the textual description of the reservation.
     * 
     * @return
     */
	public String getDescription() {
		return description;
	}

	/**
     * Set the free-text description of the reservation.
     * 
     * @param description
     */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the requested capacity for the reservation (in bps).
	 * 
	 * @return
	 */
	public long getCapacity() {
		return capacity;
	}

	/**
	 * Set the requested capacity for the reservation (in bps).
	 * 
	 * @param capacity
	 */
	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

    /**
     * Returns the list of domains and links that should be included in the
     * reservation path.
     * 
     * @return possible object is {@link PathInfo }
     * 
     */
    public PathInfo getUserInclude() {
        return userInclude;
    }

    /**
     * Sets the list of domains and links that should be included in the
     * reservation path.
     * 
     * @param value
     *            allowed object is {@link PathInfo }
     * 
     */
    public void setUserInclude(PathInfo value) {
        this.userInclude = value;
    }

    /**
     * Returns the list of domains and links that should be excluded in the
     * reservation path.
     * 
     * @return
     *     possible object is
     *     {@link PathInfo }
     *     
     */
    public PathInfo getUserExclude() {
        return userExclude;
    }

    /**
     * Sets the list of domains and links that should be excluded in the
     * reservation path.
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
     * Returns the minimum MTU (in bytes) that should be available across the
     * reservation path.
     * 
     * @return
     */
    public int getMtu(){
        return mtu;
    }

    /**
     * Set the minimum MTU (in bytes) that should be available across the
     * reservation path.
     * 
     * @param mtu
     */
    public void setMtu(int mtu){
        this.mtu = mtu;
    }

	public boolean isBidirectional() {
		return bidirectional;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

    /**
     * Returns the maximum allowed delay (in ms).
     * 
     * @return
     */
	public int getMaxDelay() {
		return maxDelay;
	}

    /**
     * Set the maximum allowed delay (in ms).
     * 
     * @param maxDelay
     */
	public void setMaxDelay(int maxDelay) {
		this.maxDelay = maxDelay;
	}

	/**
	 * Returns the type of resiliency requested by the reservation.
	 * 
	 * @return
	 */
	public Resiliency getResiliency() {
		return resiliency;
	}

	/**
	 * Set the type of resiliency requested by the reservation.
	 * 
	 * @param resiliency
	 */
	public void setResiliency(Resiliency resiliency) {
		this.resiliency = resiliency;
	}

	@XmlTransient
    public String getStartPortFriendlyName() {
        return startPortFriendlyName;
    }

    public void setStartPortFriendlyName(String startPortFriendlyName) {
        this.startPortFriendlyName = startPortFriendlyName;
    }

    @XmlTransient
    public String getEndPortFriendlyName() {
        return endPortFriendlyName;
    }

    public void setEndPortFriendlyName(String endPortFriendlyName) {
        this.endPortFriendlyName = endPortFriendlyName;
    }

    /**
     * Checks whether to process the reservation now. If true, the reservation
     * start time will set to the current time.
     * 
     * @return
     */
	public boolean isProcessNow() {
		return processNow;
	}

	/**
	 * Set whether to process the reservation now. If true, the reservation
     * start time will set to the current time.
     * 
	 * @param processNow
	 */
	public void setProcessNow(boolean processNow) {
		this.processNow = processNow;
	}
	
	@Override
	public String toString() {
		String res = "";
		
		if (getStartPort() != null){
			if (getStartPort().getAddress() != null) {
				res += "		 address: 	" + getStartPort().getAddress();
			}
			if (getStartPort().getMode() != null) {
				res += "		StartPort mode: 	" + getStartPort().getMode();
			}

			String vlan = Integer.toString(getStartPort().getVlan());

			if (vlan != null)
				res += "		StartPort vlan: 	" + getStartPort().getVlan();

		}
		if (getEndPort() != null) {
			if (getEndPort().getAddress() != null) {
				res += "		EndPort address: 	" + getEndPort().getAddress();
			}
			if (getEndPort().getMode() != null) {
				res += "		EndPort mode: 	" + getEndPort().getMode();
			}

			String vlan = Integer.toString(getEndPort().getVlan());

			if (vlan != null)
				res += "		EndPort vlan: 	" + getEndPort().getVlan();

		}
        res += "    Start time: " + getStartTime().getTime() + ", End time: " 
        		+ getEndTime().getTime() + "\n";
        res += "    Capacity: " + getCapacity() + ", Delay: " + getMaxDelay() 
        				+ ", Resiliency: " + getResiliency() + ", Description: " 
        				+ getDescription() + "\n";
        res += "    Priority: " + getPriority() + "\n";
        
        if (getUserInclude() != null) {
            if (getUserInclude().getDomains() != null) {
                res += "    User-included domains: " + getUserInclude().getDomains().size() + "\n";
                for (int i=0; i < getUserInclude().getDomains().size(); i++) {
                    res += "        " + getUserInclude().getDomains().get(i) + "\n";
                }
            }
            if (getUserInclude().getLinks() != null) {
                res += "    User-included links: " + getUserInclude().getLinks().size() + "\n";
                for (int i=0; i < getUserInclude().getLinks().size(); i++) {
                    res += "        " + getUserInclude().getLinks().get(i) + "\n";
                }
            }
        }
        
        if (getUserExclude() != null) {
            if (getUserExclude().getDomains() != null) {
                res += "    User-excluded domains: " + getUserExclude().getDomains().size() + "\n";
                for (int i=0; i < getUserExclude().getDomains().size(); i++) {
                    res += "        " + getUserExclude().getDomains().get(i) + "\n";
                }
            }
            if (getUserExclude().getLinks() != null) {
                res += "    User-excluded links: " + getUserExclude().getLinks().size() + "\n";
                for (int i=0; i < getUserExclude().getLinks().size(); i++) {
                    res += "        " + getUserExclude().getLinks().get(i) + "\n";
                }
            }
        }
        
        if (getAuthParameters() != null) {
            res += "    User identifier: " + getAuthParameters().getIdentifier() + "\n";
            res += "    User organization: " + getAuthParameters().getOrganization() + "\n";
            res += "    User project membership: " + getAuthParameters().getProjectMembership() + "\n";
            res += "    User project role: " + getAuthParameters().getProjectRole() + "\n";
            res += "    User email: " + getAuthParameters().getEmail() + "\n";
        }
        
        return res;
	}

    /**
     * Returns information about the user submitting the request.
     * 
     * @return
     */
    public UserAuthParameters getAuthParameters() {
        return authParameters;
    }

    /**
     * Sets information about the user submitting the request.
     * 
     * @param authParameters
     */
    public void setAuthParameters(UserAuthParameters authParameters) {
        this.authParameters = authParameters;
    }
}
