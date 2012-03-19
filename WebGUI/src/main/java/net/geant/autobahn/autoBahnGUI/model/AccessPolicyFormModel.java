package net.geant.autobahn.autoBahnGUI.model;

import java.io.Serializable;
import java.util.List;

import net.geant.autobahn.aai.AccessPolicy;

/**
 * Data model for AccessPolicy
 * 
 * @author Kostas Stamos <stamos@cti.gr>
 *
 */
public class AccessPolicyFormModel implements Serializable {
    
    /**
     * List of registered IDMs
     */
    private List<String> idms;
    
    /**
     * Chosen IDM
     */
    private String currentIdm;
    
	/**
	 * Identifies Access Policy at selected interdomain manager
	 */
    AccessPolicy dmacp;
    
    /**
     * Error message if some communication error appear
     */
    private String error;
    
	public List<String> getIdms() {
		return idms;
	}

	public void setIdms(List<String> idms) {
		this.idms = idms;
	}

	public String getCurrentIdm() {
		return currentIdm;
	}

	public void setCurrentIdm(String currentIdm) {
		this.currentIdm = currentIdm;
	}

	public AccessPolicy getAccessPolicy() {
		return dmacp;
	}

	public void setAccessPolicy(AccessPolicy dmacp) {
		this.dmacp = dmacp;
	}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
