package net.geant.autobahn.converter;

/**
 * Provides abstract identifiers for internal network devices. Identifiers are
 * in the form of IPv4 addresses. Domain ports, nodes and links are assigned
 * subnets, and identifiers of the particular network entity is generated from
 * the subnet. <br/>
 * CIDR notation is used, i.e. 10.0.10.0/24 for nodes means that successive
 * network's nodes of the domain will be assigned identifiers: 10.0.10.1,
 * 10.0.10.2 up to 10.0.10.255
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 * 
 */
public class InternalIdentifiersSourceIPv4 implements InternalIdentifiersSource {

	private Subnet nodes = null;
	private Subnet ports = null;
	private Subnet links = null;

	/**
	 * Creates an instance of the identifiers source.
	 * 
	 * @param nSub
	 *            String representation of the IPv4 subnet for network nodes
	 * @param pSub
	 *            String representation of the IPv4 subnet for network ports
	 * @param lSub
	 *            String representation of the IPv4 subnet for network links
	 */
	public InternalIdentifiersSourceIPv4(String nSub, String pSub, String lSub) {
		nodes = new Subnet(nSub);
		ports = new Subnet(pSub);
		links = new Subnet(lSub);
	}

	/**
	 * 
	 * @return Abstract identifier for the network node.
	 */
	@Override
	public String generateNodeID(String name, String desc) {
		if(!nodes.hasMoreValues())
			throw new IllegalStateException("Address range has no more values");
		
		return nodes.nextValue();
	}

	/**
	 * 
	 * @return Abstract identifier for the network port.
	 */
    @Override
	public String generatePortID(String snode, String dnode, String desc) {
		if(!ports.hasMoreValues())
			throw new IllegalStateException("Address range has no more values");
		
		return ports.nextValue();
	}
	
	/**
	 * 
	 * @return Abstract identifier for the network link.
	 */
    @Override
	public String generateLinkID(String startPort, String endPort, String desc) {
		if(!links.hasMoreValues())
			throw new IllegalStateException("Address range has no more values");
		
		return links.nextValue();
	}
}
