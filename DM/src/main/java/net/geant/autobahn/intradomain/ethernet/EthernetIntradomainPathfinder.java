package net.geant.autobahn.intradomain.ethernet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.geant.autobahn.constraints.BooleanConstraint;
import net.geant.autobahn.constraints.ConstraintsNames;
import net.geant.autobahn.constraints.MinValueConstraint;
import net.geant.autobahn.constraints.PathConstraints;
import net.geant.autobahn.constraints.RangeConstraint;
import net.geant.autobahn.intradomain.IntradomainTopology;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.common.Node;
import net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.GraphEdge;
import net.geant.autobahn.intradomain.pathfinder.GraphNode;
import net.geant.autobahn.intradomain.pathfinder.GraphSearch;

import org.apache.log4j.Logger;

/**
 * Implementation of the intradomain pathfinder for ethernet domains.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class EthernetIntradomainPathfinder extends GenericIntradomainPathfinder {

    private List<SpanningTree> all_sptrees = new ArrayList<SpanningTree>();
    private List<Node> all_nodes = new ArrayList<Node>();

    private static final Logger log = Logger.getLogger(EthernetIntradomainPathfinder.class);
    private int defaultEthernetMtu = 1514; 
    
    /**
     * Initialize object with given topology.
     * 
     * @param topology
     * @param defaultMtu If null is provided, value 1514 is used
     */
    public EthernetIntradomainPathfinder(IntradomainTopology topology, String defaultMtu) {
        this.all_nodes = topology.getNodes();
        this.all_sptrees = topology.getSpanningTrees();
        try {
            defaultEthernetMtu = Integer.parseInt(defaultMtu);
        } catch (NumberFormatException e) {
            log.info("Using default Ethernet MTU value of " + defaultEthernetMtu);
        }
    }
	
	/**
	 * Creates instance with the given network devices.
	 * 
	 * @param all_sptrees List of all spanning tree objects
	 * @param all_nodes of all nodes in the domain
	 */
    public EthernetIntradomainPathfinder(List<SpanningTree> all_sptrees,
			List<Node> all_nodes, String defaultMtu) {
		this.all_sptrees = all_sptrees;
		this.all_nodes = all_nodes;
        try {
            defaultEthernetMtu = Integer.parseInt(defaultMtu);
        } catch (NumberFormatException e) {
            log.info("Using default Ethernet MTU value of " + defaultEthernetMtu);
        }
    }
	
	/* (non-Javadoc)
	 * @see net.geant.autobahn.intradomain.pathfinder.GenericIntradomainPathfinder#initGraph(java.util.Collection)
	 */
	@Override
	public GraphSearch initGraph(Collection<GenericLink> excluded, int mtu) {
		
		List<Node> nodes = all_nodes;
		List<SpanningTree> sptrees = all_sptrees;
		
    	grnodes = new HashMap<Node, GraphNode>();
        for (Node n : nodes) {
        	grnodes.put(n, new GraphNode(n));
        }

    	gredges.clear();
        
    	// Multiple SpanningTree objects may refer to the same ethLink
    	// We want to add each ethLink once, but with as many VLAN ranges
    	// as there are SpanningTrees, so we keep this map that combines both
    	HashMap<EthLink, List<SpanningTree>> links = new HashMap<EthLink, List<SpanningTree>>();
    	for (SpanningTree st : sptrees) {
            List<SpanningTree> stList = links.get(st.getEthLink());
    	    if (stList != null) {
    	        stList.add(st);
    	    } else {
    	        stList = new ArrayList<SpanningTree>();
    	        stList.add(st);
    	        links.put(st.getEthLink(), stList);
    	    }
    	}
    	
        // Determine neighbors of each node
        for (Map.Entry<EthLink, List<SpanningTree>> lnk : links.entrySet()) {
            
            EthLink elink = lnk.getKey();
            
            if (mtu > 0){
                log.debug("User has requested Mtu size " + mtu + ", checking if" +
                        " ethernet link supports it...");
                
                int linkMtu = elink.getGenericLink().getStartInterface().getMtu();
                
                if (linkMtu<=0) {
                    linkMtu = this.defaultEthernetMtu;
                }
                
                if (mtu > linkMtu){  
                    log.debug("Link " + elink + " rejected.");
                    continue;
                }
                
                linkMtu = elink.getGenericLink().getEndInterface().getMtu();
                
                if (linkMtu<=0) {
                    linkMtu = this.defaultEthernetMtu;
                }
                
                if (mtu > linkMtu){
                    log.debug("Link " + elink + " rejected.");
                    continue;
                }
            }
            GenericLink link = elink.getGenericLink();
            
            // Skip excluded generic links
            if(excluded != null && excluded.contains(link))
            	continue;
            
            Node s = link.getStartInterface().getNode();
            Node e = link.getEndInterface().getNode();
            
            GraphNode sgr = grnodes.get(s);
            GraphNode egr = grnodes.get(e);

            RangeConstraint rcon = new RangeConstraint();
            // Add a VLAN range for each associated SpanningTree
            for (SpanningTree st : lnk.getValue()) {
                rcon.addRange((int) st.getVlan().getLowNumber(), 
                        (int) st.getVlan().getHighNumber());
            }
			PathConstraints pcon = new PathConstraints();
			pcon.addRangeConstraint(ConstraintsNames.VLANS, rcon);
			
			if(sgr.getInternalNode().isVlanTranslationSupport() || egr.getInternalNode().isVlanTranslationSupport()) {
				pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, new BooleanConstraint(true, "OR"));
        	} else {
				pcon.addBooleanConstraint(ConstraintsNames.SUPPORTS_VLAN_TRANSLATION, new BooleanConstraint(false, "OR"));
        	}
			
			//mtu info added
            MinValueConstraint mcon = null;
            if ((elink.getGenericLink().getStartInterface().getMtu() != 0) && (elink.getGenericLink().getEndInterface().getMtu()!= 0)){
                if (elink.getGenericLink().getStartInterface().getMtu() < elink.getGenericLink().getEndInterface().getMtu()){
                    mcon = new MinValueConstraint((double)elink.getGenericLink().getStartInterface().getMtu());
                } else {
                    mcon = new MinValueConstraint((double)elink.getGenericLink().getEndInterface().getMtu());
                }
                pcon.addMinValueConstraint(ConstraintsNames.MTU, mcon);
            }
			
			GraphEdge edge = new GraphEdge(sgr, egr, link, pcon);
            sgr.addEdge(edge);
            
            GraphEdge invEdge = new GraphEdge(egr, sgr, link, pcon);
            egr.addEdge(invEdge);
            
            if(link.isInterdomain()) {
            	gredges.put(link, edge);
            }
            
            if(link.getStartInterface().getDomainId() != null) {
            	sgr.setEdgeNode(true);
            }

            if(link.getEndInterface().getDomainId() != null) {
            	egr.setEdgeNode(true);
            }
        }

        // Add nodes to graph
        GraphSearch grSearch = new GraphSearch();
        
        for (GraphNode gn : grnodes.values()) { 
            grSearch.addGraphNode(gn);
        }
        
        return grSearch;
	}
	
}
