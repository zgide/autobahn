package net.geant.security;

import java.io.Serializable;
import java.util.Properties;

public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String PORTSALLOW = "ports.allow";
    public static String PORTSDENY = "ports.deny";
    public static String VLANALLOW = "vlan.allow";
    public static String VLANDENY = "vlan.deny";
    public static String MAXCAPACITY = "maxCapacity";

    private String username = null;
    private String portsAllow = null;
    private String portsDeny = null;
    private String vlanAllow = null;
    private String vlanDeny = null;
    private String maxCapacity = null;

    public UserProfile(String username, Properties userProps) {
        this.username = username;
        this.setUserProps(userProps);
    }

    public UserProfile(String username, String portsAllow, String portsDeny,
            String vlanAllow, String vlanDeny, String maxCapacity) {
        this.username = username;
        this.portsAllow = portsAllow;
        this.portsDeny = portsDeny;
        this.vlanAllow = vlanAllow;
        this.vlanDeny = vlanDeny;
        this.maxCapacity = maxCapacity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPortsAllow() {
        return portsAllow;
    }

    public void setPortsAllow(String portsAllow) {
        this.portsAllow = portsAllow;
    }

    public String getPortsDeny() {
        return portsDeny;
    }

    public void setPortsDeny(String portsDeny) {
        this.portsDeny = portsDeny;
    }

    public String getVlanAllow() {
        return vlanAllow;
    }

    public void setVlanAllow(String vlanAllow) {
        this.vlanAllow = vlanAllow;
    }

    public String getVlanDeny() {
        return vlanDeny;
    }

    public void setVlanDeny(String vlanDeny) {
        this.vlanDeny = vlanDeny;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Properties getUserProps() {
        Properties userProps = new Properties();
        if (portsAllow != null) {
            userProps.put(PORTSALLOW, portsAllow);
        }
        if (portsDeny != null) {
            userProps.put(PORTSDENY, portsDeny);
        }
        if (vlanAllow != null) {
            userProps.put(VLANALLOW, vlanAllow);
        }
        if (vlanDeny != null) {
            userProps.put(VLANDENY, vlanDeny);
        }
        if (maxCapacity != null) {
            userProps.put(MAXCAPACITY, maxCapacity);
        }
        return userProps;
    }

    public void setUserProps(Properties userProps) {
        if (userProps != null) {
            this.portsAllow = userProps.getProperty(PORTSALLOW);
            this.portsDeny = userProps.getProperty(PORTSDENY);
            this.vlanAllow = userProps.getProperty(VLANALLOW);
            this.vlanDeny = userProps.getProperty(VLANDENY);
            this.maxCapacity = userProps.getProperty(MAXCAPACITY);
        }
    }
}
