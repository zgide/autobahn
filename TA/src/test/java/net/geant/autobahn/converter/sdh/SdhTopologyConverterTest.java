package net.geant.autobahn.converter.sdh;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import net.geant.autobahn.converter.InternalIdentifiersSource;
import net.geant.autobahn.converter.InternalIdentifiersSourceIPv4;
import net.geant.autobahn.converter.InternalIdentifiersSourceURN;
import net.geant.autobahn.converter.PublicIdentifiersMapping;
import net.geant.autobahn.converter.TopologyConverter;
import net.geant.autobahn.intradomain.common.GenericLink;
import net.geant.autobahn.intradomain.converter.Stats;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinder;
import net.geant.autobahn.intradomain.pathfinder.IntradomainPathfinderFactory;
import net.geant.autobahn.network.Link;
import net.geant.autobahn.topologyabstraction.ExternalIdentifiersSource;
import net.geant.autobahn.topologyabstraction.FileIdentifiersSource;
import net.geant.autobahn.utils.IntraTopologyBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SdhTopologyConverterTest {

    public enum Type {ETH, SDH};
    
    @Before
    public void setUp() throws Exception {
        System.out.println(" - --");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingleDomainWith2ClientsIPv4() throws IOException {
        System.out.println(" ---Running testSingleDomainWith2ClientsIPv4");
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        TestTopology1 topo = new TestTopology1();
        topo.domain1(builder);
        
        IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
                builder.getIntradomainTopology());
        
        String nrange = "10.11.0.0/19";
        String prange = "10.11.32.0/19";
        String lrange = "10.11.64.0/19";
        
        InternalIdentifiersSource internal = new InternalIdentifiersSourceIPv4(
                nrange, prange, lrange);
        
        // It's OK as this topology has no neighbors
        PublicIdentifiersMapping mapping = null;

        TopologyConverter conv = new SdhTopologyConverter(builder
                .getIntradomainTopology(), pf, internal, mapping, null);
    
        Stats stats = conv.abstractInternalPartOfTopology();
        
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(4, stats.numEdgeNodes);
        TestCase.assertEquals(3, stats.numPaths);
    
        // It's OK as this topology has no neighbors
        conv.abstractExternalPartOfTopology(null);
        
        List<Link> links = conv.getAbstractLinks();
        
        TestCase.assertEquals(3, links.size());
    }
    
    @Test
    public void testTopologyWithoutExternalConnectionsIPv4() {
        System.out.println(" ---Running testTopologyWithoutExternalConnectionsIPv4");
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        TestTopology3 topo = new TestTopology3();
        topo.domain1(builder);
        
        IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
                builder.getIntradomainTopology());
        
        String nrange = "10.11.0.0/19";
        String prange = "10.11.32.0/19";
        String lrange = "10.11.64.0/19";
        
        InternalIdentifiersSource internal = new InternalIdentifiersSourceIPv4(
                nrange, prange, lrange);
        
        // It's OK as this topology has no neighbors
        PublicIdentifiersMapping mapping = null;
        
        TopologyConverter conv = new SdhTopologyConverter(builder
                .getIntradomainTopology(), pf, internal, mapping, null);

        Stats stats = conv.abstractInternalPartOfTopology();
        
        // TestTopology3 contains 5 nodes, but zero edge nodes
        // Therefore no virtual links are created and no paths are returned
        TestCase.assertEquals(5, stats.numNodes);
        TestCase.assertEquals(0, stats.numEdgeNodes);
        TestCase.assertEquals(0, stats.numPaths);

        // It's OK as this topology has no neighbors
        conv.abstractExternalPartOfTopology(null);
        
        List<Link> links = conv.getAbstractLinks();
        
        // No virtual or edge links in TestTopology3, so no abstract links
        TestCase.assertEquals(0, links.size());
    }
    
    @Test
    public void testAbstractingInternalPartIPv4() throws IOException {
        System.out.println(" ---Running testAbstractingInternalPartIPv4");

        TopologyConverter conv = createTopology2Converter(true);
        
        Stats stats = conv.abstractInternalPartOfTopology();
    
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(7, stats.numEdgeNodes);
        TestCase.assertEquals(7, stats.numLinks);
        TestCase.assertEquals(6, stats.numPaths);
        
        System.out.println(conv);
    }
    
    @Test(expected=NullPointerException.class)
    public void testPassingNullAsExternalSourceIPv4() throws IOException {
        System.out.println(" ---Running testPassingNullAsExternalSourceIPv4");
    
        TopologyConverter conv = createTopology2Converter(true);
        
        @SuppressWarnings("unused")
        Stats stats = conv.abstractInternalPartOfTopology();
        conv.abstractExternalPartOfTopology(null);
    }
    
    @Test
    public void testAbstractingSampleTopologyIPv4() throws IOException {
        System.out.println(" ---Running testAbstractingSampleTopologyIPv4");

        TopologyConverter conv = createTopology2Converter(true);
        
        Stats stats = conv.abstractInternalPartOfTopology();
    
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(7, stats.numEdgeNodes);
        TestCase.assertEquals(7, stats.numLinks);
        TestCase.assertEquals(6, stats.numPaths);
    
        ExternalIdentifiersSource source = new FileIdentifiersSource(
                "./src/test/resources/test_etc/topology2-external-ids.properties");
        List<Link> links = conv.abstractExternalPartOfTopology(source);
        
        TestCase.assertEquals(7, links.size());
        
        for(Link l : links)
            System.out.println(l);
    
        // Check the client link
        GenericLink glink = conv.getEdgeLink(links.get(1));
        TestCase.assertEquals("p2.4-cli-port2", glink.toString());
    
        // Check the external link
        glink = conv.getEdgeLink(links.get(2));
        TestCase.assertEquals("p2.1-dom1-port1", glink.toString());
        
        // Check the virtual link
        glink = conv.getEdgeLink(links.get(5));
        TestCase.assertNull(glink);
    }
    
    @Test
    public void testSingleDomainWith2ClientsURN() throws IOException {
        System.out.println(" ---Running testSingleDomainWith2ClientsURN");
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        TestTopology1 topo = new TestTopology1();
        topo.domain1(builder);
        
        IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
                builder.getIntradomainTopology());
        
        InternalIdentifiersSource internal = new InternalIdentifiersSourceURN("MyDomain");
        
        // It's OK as this topology has no neighbors
        PublicIdentifiersMapping mapping = null;

        TopologyConverter conv = new SdhTopologyConverter(builder
                .getIntradomainTopology(), pf, internal, mapping, null);
    
        Stats stats = conv.abstractInternalPartOfTopology();
        
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(4, stats.numEdgeNodes);
        TestCase.assertEquals(3, stats.numPaths);
    
        // It's OK as this topology has no neighbors
        conv.abstractExternalPartOfTopology(null);
        
        List<Link> links = conv.getAbstractLinks();
        
        TestCase.assertEquals(3, links.size());
    }
    
    @Test
    public void testTopologyWithoutExternalConnectionsURN() {
        System.out.println(" ---Running testTopologyWithoutExternalConnectionsURN");
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        TestTopology3 topo = new TestTopology3();
        topo.domain1(builder);
        
        IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
                builder.getIntradomainTopology());
        
        InternalIdentifiersSource internal = new InternalIdentifiersSourceURN("MyDomain");
        
        // It's OK as this topology has no neighbors
        PublicIdentifiersMapping mapping = null;
        
        TopologyConverter conv = new SdhTopologyConverter(builder
                .getIntradomainTopology(), pf, internal, mapping, null);

        Stats stats = conv.abstractInternalPartOfTopology();
        
        // TestTopology3 contains 5 nodes, but zero edge nodes
        // Therefore no virtual links are created and no paths are returned
        TestCase.assertEquals(5, stats.numNodes);
        TestCase.assertEquals(0, stats.numEdgeNodes);
        TestCase.assertEquals(0, stats.numPaths);

        // It's OK as this topology has no neighbors
        conv.abstractExternalPartOfTopology(null);
        
        List<Link> links = conv.getAbstractLinks();
        
        // No virtual or edge links in TestTopology3, so no abstract links
        TestCase.assertEquals(0, links.size());
    }
    
    @Test
    public void testAbstractingInternalPartURN() throws IOException {
        System.out.println(" ---Running testAbstractingInternalPartURN");

        TopologyConverter conv = createTopology2Converter(false);
        
        Stats stats = conv.abstractInternalPartOfTopology();
    
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(7, stats.numEdgeNodes);
        TestCase.assertEquals(7, stats.numLinks);
        TestCase.assertEquals(6, stats.numPaths);
        
        System.out.println(conv);
    }
    
    @Test(expected=NullPointerException.class)
    public void testPassingNullAsExternalSourceURN() throws IOException {
        System.out.println(" ---Running testPassingNullAsExternalSourceURN");
    
        TopologyConverter conv = createTopology2Converter(false);
        
        @SuppressWarnings("unused")
        Stats stats = conv.abstractInternalPartOfTopology();
        conv.abstractExternalPartOfTopology(null);
    }
    
    @Test
    public void testAbstractingSampleTopologyURN() throws IOException {
        System.out.println(" ---Running testAbstractingSampleTopologyURN");

        TopologyConverter conv = createTopology2Converter(false);
        
        Stats stats = conv.abstractInternalPartOfTopology();
    
        TestCase.assertEquals(7, stats.numNodes);
        TestCase.assertEquals(7, stats.numEdgeNodes);
        TestCase.assertEquals(7, stats.numLinks);
        TestCase.assertEquals(6, stats.numPaths);
    
        ExternalIdentifiersSource source = new FileIdentifiersSource(
                "./src/test/resources/test_etc/topology2-external-ids.properties");
        List<Link> links = conv.abstractExternalPartOfTopology(source);
        
        TestCase.assertEquals(7, links.size());
        
        // The topology should have exactly 2 client links, 2 interdomain links
        // and 3 virtual links
        String clientlink1 = null;
        String clientlink2 = null;
        String externlink1 = null;
        String externlink2 = null;
        int virtualinkNum = 0;
        for (Link l : links) {
            GenericLink glink = conv.getEdgeLink(l);
            System.out.println(l + " ---- " + glink);
            if (glink != null) {
                if ("p2.3-cli-port1".equals(glink.toString())) {
                    clientlink1 = glink.toString();
                }
                if ("p2.4-cli-port2".equals(glink.toString())) {
                    clientlink2 = glink.toString();
                }
                if ("p2.1-dom1-port1".equals(glink.toString())) {
                    externlink1 = glink.toString();
                }
                if ("p2.2-dom3-port2".equals(glink.toString())) {
                    externlink2 = glink.toString();
                }
            } else {
                virtualinkNum++;
            }
        }

        // Check the client links
        TestCase.assertEquals("p2.3-cli-port1", clientlink1);
        TestCase.assertEquals("p2.4-cli-port2", clientlink2);

        // Check the external links
        TestCase.assertEquals("p2.1-dom1-port1", externlink1);
        TestCase.assertEquals("p2.2-dom3-port2", externlink2);
        
        // Check the virtual links number
        TestCase.assertEquals(3, virtualinkNum);
    }
    
    private TopologyConverter createTopology2Converter(boolean ipv4) throws IOException {
        IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
        TestTopology2 topo = new TestTopology2();
        topo.domain2(builder);
        
        IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
                builder.getIntradomainTopology());
        
        InternalIdentifiersSource internal;
        if (ipv4) {
            String nrange = "10.11.0.0/19";
            String prange = "10.11.32.0/19";
            String lrange = "10.11.64.0/19";
            
            internal = new InternalIdentifiersSourceIPv4(nrange, prange, lrange);
        } else {
            internal = new InternalIdentifiersSourceURN("MyDomain");
        }
        
        PublicIdentifiersMapping mapping = new PublicIdentifiersMapping(
                "./src/test/resources/test_etc/topology2-public-ids.properties");

        TopologyConverter conv = new SdhTopologyConverter(builder
                .getIntradomainTopology(), pf, internal, mapping, null);
        
        return conv;
    }

}