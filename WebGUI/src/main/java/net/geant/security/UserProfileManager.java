package net.geant.security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserProfileManager {

    public static final String userConfigPath = "../etc/aai/";
    private static UserProfileManager userProfileManager;
    private static final Log logger = LogFactory
            .getLog("net.geant.security.UserProfileManager");
    private Map<String, UserProfile> userProfiles = new HashMap<String, UserProfile>();

    public static UserProfileManager getUserProfileManager() {
        if (userProfileManager == null) {
            return new UserProfileManager();
        }

        return userProfileManager;
    }

    /**
     * Returns the permission properties for the specified user
     * 
     * @param username
     * @return null if no file for the specified user is found
     */
    public Properties readUserPropertiesFromFile(String username) {
        Properties properties = new Properties();
        String path = userConfigPath + username;
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            properties.load(is);
            is.close();
            userProfiles.put(username, new UserProfile(username, properties));
            logger.debug(properties.size() + " properties loaded from " + path);
        } catch (Exception e) {
            logger.info("Could not load " + path + ": " + e.getMessage());
            return null;
        }
        return properties;
    }

    public void writeUserPropertiesToFile(String username, Properties props) {
        String path = userConfigPath + username;
        try {
            if (props == null) {
                File f = new File(path);
                f.delete();
                logger.debug("Removed " + path);
                return;
            }

            OutputStream os = new FileOutputStream(path);
            props.store(os, null);
            os.close();
            logger.debug(props.size() + " properties stored to " + path);
        } catch (Exception e) {
            logger.info("Could not save to " + path + ": " + e.getMessage());
        }
    }

    /**
     * 
     * @param username
     * @return null if the user has no associated rights profile
     */
    public UserProfile getUserProfile(String username) {
        // If this is first time user profile is accessed, we have to read
        // it from the file
        if (userProfiles.get(username) == null) {
            readUserPropertiesFromFile(username);
        }

        return userProfiles.get(username);
    }

    public void setUserProfile(String username, String portsAllow,
            String portsDeny, String vlanAllow, String vlanDeny,
            String maxCapacity) {
        logger.debug("setUserProfile called:" + username+" "+ portsAllow+" "+
                portsDeny+" "+ vlanAllow+" "+ vlanDeny+" "+ maxCapacity);
        UserProfile userProfile = new UserProfile(username, portsAllow,
                portsDeny, vlanAllow, vlanDeny, maxCapacity);
        userProfiles.put(username, userProfile);
        writeUserPropertiesToFile(username, userProfile.getUserProps());
    }

    public void setUserProfile(String username, Properties props) {
        UserProfile userProfile = new UserProfile(username, props);
        userProfiles.put(username, userProfile);
        writeUserPropertiesToFile(username, userProfile.getUserProps());
    }

    public String getUserProperty(String username, String key) {
        logger.info("in getUserProperty for "+username+", "+key);
        UserProfile userProfile = getUserProfile(username);
        if (userProfile == null) {
            return null;
        }
        Properties props = userProfile.getUserProps();
        if (props == null) {
            return null;
        }
        logger.info("return " + props.getProperty(key));
        logger.info("success ");
        return props.getProperty(key);
    }

    public void setUserProperty(String username, String key, String value) {
        UserProfile userProfile = getUserProfile(username);
        if (userProfile == null) {
            userProfile = new UserProfile(username, null);
        }
        Properties props = userProfile.getUserProps();
        if (props == null) {
            props = new Properties();
        }
        props.put(key, value);
        setUserProfile(username, props);
    }
}
