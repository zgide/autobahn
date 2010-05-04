package net.geant.autobahn.converter.sdh;


import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import net.geant.autobahn.converter.InternalIdentifiersSource;
import net.geant.autobahn.converter.PublicIdentifiersMapping;
import net.geant.autobahn.converter.TopologyConverter;
import net.geant.autobahn.converter.sdh.SdhTopologyConverter;
import net.geant.autobahn.converter.sdh.TestTopology2;
import net.geant.autobahn.converter.sdh.SdhTopologyConverter;
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

//import net.geant.autobahn.intradomain.IntradomainTopology.Type;

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
public void testSingleDomainWith2Clients() throws IOException {
    IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
    TestTopology1 topo = new TestTopology1();
    
    topo.domain1(builder);
    
    if(builder.getTopology().getType() != null)
    System.out.println("##########" + builder.getTopology().getType().toString());
    IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
            builder.getTopology());
    
    String nrange = "10.11.0.0/19";
    String prange = "10.11.32.0/19";
    String lrange = "10.11.64.0/19";
    
    InternalIdentifiersSource internal = new InternalIdentifiersSource(
            nrange, prange, lrange);
    
    // It's OK as this topology has no neighbors
    PublicIdentifiersMapping mapping = null;
    String tempLookup = null;
    TopologyConverter conv = new SdhTopologyConverter(builder
            .getTopology(), pf, internal, mapping, tempLookup);

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
public void testTopologyWithoutExternalConnections() {
    //TODO write the test body
}

@Test
public void testAbstractingInternalPart() throws IOException {

    TopologyConverter conv = createTopology2Converter();
    
    Stats stats = conv.abstractInternalPartOfTopology();

    TestCase.assertEquals(7, stats.numNodes);
    TestCase.assertEquals(7, stats.numEdgeNodes);
    TestCase.assertEquals(7, stats.numLinks);
    TestCase.assertEquals(6, stats.numPaths);
    
    System.out.println(conv);
}

@Test(expected=NullPointerException.class)
public void testPassingNullAsExternalSource() throws IOException {

    TopologyConverter conv = createTopology2Converter();
    
    //TODO write the test body
    
    Stats stats = conv.abstractInternalPartOfTopology();
    conv.abstractExternalPartOfTopology(null);
}

@Test
public void testAbstractingSampleTopology() throws IOException {

    TopologyConverter conv = createTopology2Converter();
    
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

private TopologyConverter createTopology2Converter() throws IOException {
    IntraTopologyBuilder builder = new IntraTopologyBuilder(false);
    TestTopology2 topo = new TestTopology2();
    topo.domain2(builder);
    
    IntradomainPathfinder pf = IntradomainPathfinderFactory.getIntradomainPathfinder(
            builder.getTopology());
    
    String nrange = "10.11.0.0/19";
    String prange = "10.11.32.0/19";
    String lrange = "10.11.64.0/19";
    
    InternalIdentifiersSource internal = new InternalIdentifiersSource(
            nrange, prange, lrange);
    PublicIdentifiersMapping mapping = new PublicIdentifiersMapping(
            "./src/test/resources/test_etc/topology2-public-ids.properties");
    String tempLookup = null;
    TopologyConverter conv = new SdhTopologyConverter(builder
            .getTopology(), pf, internal, mapping, tempLookup);
    
    return conv;
}

}