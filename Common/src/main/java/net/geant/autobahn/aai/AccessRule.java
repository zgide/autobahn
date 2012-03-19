package net.geant.autobahn.aai;

import java.io.Serializable;

/**
 * Class that handles a single access rule
 * The rule consists of a set of attributes separated by spaces.
 * These attributes correspond to UserAuthParameters, so this class
 * contains a UserAuthParameters object and methods for 
 * converting a string rule to/from the UserAuthParameters object.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 * 
 */
public class AccessRule implements Serializable {

    // Contains the data on which the Access Rule is based
    private UserAuthParameters authp;
    
    // Hopefully these char sequences will never appear in actual Rules
    public static final String space_replacement = "___SPACE___";
    public static final String comma_replacement = "___COMMA___";
    
    public AccessRule() {
        emptyRule();
    }

    public AccessRule(String rule) {
        this();
        setRule(rule);
    }

    /**
     * 
     * @param authp - if null the Access Rule is initialized with empty Strings
     */
    public AccessRule(UserAuthParameters authp) {
        if (authp != null) {
            this.authp = authp;
        } else {
            emptyRule();
        }
    }
    
    public void emptyRule() {
        authp = new UserAuthParameters();
        authp.setProjectRole("");
        authp.setEmail("");
        authp.setProjectMembership("");
        authp.setOrganization("");
    }

    /**
     * Use the provided string to fill in the UserAuthParameters object
     * 
     * @param rule
     */
    public void setRule(String rule) {
        if (rule != null && !rule.equals("")) {
            String[] attr = rule.split(" ");
            if (attr != null && attr.length > 0) {
                emptyRule();
                
                for (String s : attr) {
                    
                    // Insert back any replaced spaces and commas
                    s = s.replaceAll(space_replacement, " ");
                    s = s.replaceAll(comma_replacement, ",");
                    
                    if (s.startsWith(UserAuthParameters.PREFIX_ROLE)) {
                        authp.setProjectRole(
                                s.substring(UserAuthParameters.PREFIX_ROLE.length()));
                    } else if (s.startsWith(UserAuthParameters.PREFIX_EMAIL)) {
                        authp.setEmail(
                                s.substring(UserAuthParameters.PREFIX_EMAIL.length()));
                    } else if (s.startsWith(UserAuthParameters.PREFIX_PROJECTMEMB)) {
                        authp.setProjectMembership(
                                s.substring(UserAuthParameters.PREFIX_PROJECTMEMB.length()));
                    } else if (s.startsWith(UserAuthParameters.PREFIX_ORGANIZATION)) {
                        authp.setOrganization(
                                s.substring(UserAuthParameters.PREFIX_ORGANIZATION.length()));
                    }
                }
            }
        }
    }
    
    /**
     * Extract the UserAuthParameters object in a string format suitable for XML
     * 
     * @return
     */
    public String getRule() {
        String rule = "";

        // Convert any spaces and commas
        String role = authp.getProjectRole().replaceAll(" ", space_replacement)
                .replaceAll(",", comma_replacement);
        String email = authp.getEmail().replaceAll(" ", space_replacement)
                .replaceAll(",", comma_replacement);
        String pm = authp.getProjectMembership().replaceAll(" ", space_replacement)
                .replaceAll(",", comma_replacement);
        String org = authp.getOrganization().replaceAll(" ", space_replacement)
                .replaceAll(",", comma_replacement);

        if (role != null && !role.equals("")) {
            rule += UserAuthParameters.PREFIX_ROLE + role;
        }
        if (email != null && !email.equals("")) {
            rule += " " + UserAuthParameters.PREFIX_EMAIL + email;
        }
        if (pm != null && !pm.equals("")) {
            rule += " " + UserAuthParameters.PREFIX_PROJECTMEMB + pm;
        }
        if (org != null && !org.equals("")) {
            rule += " " + UserAuthParameters.PREFIX_ORGANIZATION + org;
        }
        return rule;
    }
    
    /**
     * @return the authp
     */
    public UserAuthParameters getAuthp() {
        return authp;
    }

    /**
     * @param authp
     *            the authp to set - nothing is done if this is null
     */
    public void setAuthp(UserAuthParameters authp) {
        if (authp != null) {
            this.authp = authp;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getRule();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authp == null) ? 0 : authp.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccessRule other = (AccessRule) obj;
        if (authp == null) {
            if (other.authp != null)
                return false;
        } else if (!authp.equals(other.authp))
            return false;
        return true;
    }

}