/*
 * SpanningTreeDAO.java
 *
 * 2007-03-29
 */
package net.geant2.jra3.intradomain.ethernet.dao;

import java.util.List;

import net.geant2.jra3.intradomain.ethernet.EthLink;
import net.geant2.jra3.intradomain.ethernet.SpanningTree;
import net.geant2.jra3.intradomain.ethernet.Vlan;

/**
 * DAO class used for operations on SpanningTree instances.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public interface SpanningTreeDAO {
    
    public void create(SpanningTree instance);
    
    public SpanningTree get(EthLink link, Vlan vlan);

    public List<SpanningTree> getAll();
    
    public void update(SpanningTree transientObject);
    
    public void delete(SpanningTree persistentObject);
    
    public void deleteAll();
}
