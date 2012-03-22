package net.geant.autobahn.converter;

/**
 * Provides abstract identifiers for internal network devices.
 * 
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public interface InternalIdentifiersSource {

    /**
     * 
     * @return Abstract identifier for the network's node.
     */
    public String generateNodeID(String name, String desc);

    /**
     * 
     * @return Abstract identifier for the network's port.
     */
    public String generatePortID(String snode, String dnode, String desc);

    /**
     * 
     * @return Abstract identifier for the network's link.
     */
    public String generateLinkID(String startPort, String endPort, String desc);
}
