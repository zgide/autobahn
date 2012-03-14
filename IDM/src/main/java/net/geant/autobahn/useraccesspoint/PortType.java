package net.geant.autobahn.useraccesspoint;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.geant.autobahn.network.Port;

/**
 * This class represents a starting or ending reservation port.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortType", namespace = "useraccesspoint.autobahn.geant.net", propOrder = {
    "address", "mode", "vlan", "description", "isIdcp", "isClient"
})
public class PortType implements Serializable, Comparable<PortType> {
	
	private static final long serialVersionUID = 1L;

	private String address;
	private Mode mode;
	private int vlan;
	private String description;
	private boolean isClient;
	private boolean isIdcp;
	
	public PortType() {
		
	}
	
	public PortType(String address) {
		this.address = address;
	}
	
	public PortType(String address, Mode mode, int vlan) {
		super();
		this.address = address;
		this.mode = mode;
		this.vlan = vlan;
	}

    /**
     * Get the port identifier.
     * 
     * @return an identifier String.
     */
	public String getAddress() {
		return address;
	}

    /**
     * Set the port identifier.
     * 
     * @param address
     */
	public void setAddress(String address) {
		this.address = address;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
     * Get the VLAN that the reservation will use for this port.
	 * 
	 * @return the VLAN number
	 */
	public int getVlan() {
		return vlan;
	}

    /**
     * Set the VLAN that the reservation will use for this port.
     * 
     * @param vlan
     */
	public void setVlan(int vlan) {
		this.vlan = vlan;
	}

	/**
	 * Get the description of the port.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

    /**
     * Set a free text description of the port.
     * 
     * @param description
     */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * Checks whether the port belongs to a client domain (end host).
     * 
     * @return
     */
	public boolean isClient() {
		return isClient;
	}

    /**
     * Set whether the port belongs to a client domain (end host).
     * 
     * @param isClient
     */
	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

    /**
     * Checks whether this is an IDCP port.
     * 
     * @return true, if the port does not belong to an AutoBAHN domain but has
     *         originated from an IDCP area
     */
	public boolean isIdcp() {
		return isIdcp;
	}

    /**
     * Set whether this is an IDCP port.
     * 
     * @param isIdcp
     */
	public void setIdcp(boolean isIdcp) {
		this.isIdcp = isIdcp;
	}

    /**
     * Returns a combination of port description and port address that is
     * suitable for presentation to the user.
     * 
     * @return
     */
	public String getFriendlyName() {
		if(description == null || description.trim().equals("")
             || description.trim().equalsIgnoreCase("null") || description.trim() == address.trim()) {
			return address;
		} else {
			return description.trim() + " (" + address + ")";
		}
	}

	public String toString() {
        return address;
    }

    /**
     * Converts an internal IDM Port object to a PortType object that may be
     * transfered over the UAP interface.
     * 
     * @param port - The Port object to convert
     * @return
     */
	public static PortType convert(Port port) {
		PortType pType = new PortType();
		pType.setAddress(port.getBodID());
		pType.setDescription(port.getDescription());
		pType.setIdcp(port.isIdcpPort());
		pType.setClient(port.isClientPort());
		
		return pType;
	}

    /**
     * Creates a PortType object that may be transfered over the UAP interface
     * using the supplied parameters.
     * 
     * @param port - the port identifier
     * @param vlan - the VLAN number to be used at the port
     * @param description - a free text description of the port
     * @return
     */
	public static PortType convert(String port, int vlan, String description) {
		PortType pType = new PortType();
		pType.setAddress(port);
		pType.setVlan(vlan);
		pType.setDescription(description);
		
		return pType;
	}

    @Override
    public int compareTo(PortType p2) {
        if (p2 == null) {
            return 1;
        }
        if (this.getAddress() == null) {
            return -1;
        }
        return this.getAddress().compareTo(p2.getAddress());
    }
}
