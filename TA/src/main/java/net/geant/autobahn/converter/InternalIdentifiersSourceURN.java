package net.geant.autobahn.converter;

public class InternalIdentifiersSourceURN implements InternalIdentifiersSource {

    String delimiter = ".";
    
    String domain;
    long count = 0;
    
    /**
     * Creates an instance of the identifiers source.
     * 
     * @param domain
     */
    public InternalIdentifiersSourceURN(String domain) {
        this.domain = domain;
        this.count = 0;
    }

    @Override
    public String generateNodeID(String name, String desc) {
        return domain + delimiter + "Node" + delimiter + ++count;
    }

    @Override
    public String generatePortID(String snode, String dnode, String desc) {
        return domain + delimiter + "Port" + delimiter + ++count;
    }

    @Override
    public String generateLinkID(String startPort, String endPort, String desc) {
        return domain + delimiter + "Link" + delimiter + ++count;
    }
}
