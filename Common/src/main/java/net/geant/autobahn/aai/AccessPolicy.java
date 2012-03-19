package net.geant.autobahn.aai;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Class that handles a policy
 * The policy consists of a list of access rules separated by commas.
 * This class contains a list of access rules and methods for 
 * converting a string rule to/from the Access Rule list.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class AccessPolicy implements Serializable {

    private List<AccessRule> policyRules = new ArrayList<AccessRule>();

    public AccessPolicy(String policy) {
        setPolicy(policy);
    }

    public AccessPolicy() {

    }

    public void addRule(AccessRule accessRule) {
        if (accessRule != null && !accessRule.getRule().equals("")) {
            if (!policyRules.contains(accessRule)) {
                policyRules.add(accessRule);
            }
        }
    }

    public void emptyPolicy() {
        policyRules.clear();
    }

    public void removeRule(AccessRule accessRule) {
        policyRules.remove(accessRule);
    }

    /**
     * Extract the set of Access Rules in a string format suitable for XML
     * @return
     */
    public String getPolicy() {
        String policy = "";
        if (!policyRules.isEmpty()) {
            policy += policyRules.get(0).getRule().trim();
            for (int i = 1; i < policyRules.size(); i++) {
                policy += "," + policyRules.get(i).getRule().trim();
            }
        }

        return policy.trim();
    }

    public List<AccessRule> getPolicyRules() {
        return policyRules;
    }

    /**
     * Use the provided string to fill in the Access Rules
     * 
     * @param policy
     */
    public void setPolicy(String policy) {
        if (policy != null && !policy.equals("")) {
            String[] rules = policy.trim().split(",");
            if (rules != null && rules.length > 0) {
                emptyPolicy();
                
                for (String s : rules) {
                    addRule(new AccessRule(s));
                }
            }
        }
    }

    public void setPolicyRules(List<AccessRule> policyRules) {
        this.policyRules = policyRules;
    }

}