package net.geant2.jra3.converter;

import net.geant2.jra3.dm2idm.Dm2Idm;
import net.geant2.jra3.dm2idm.Dm2IdmClient;
import net.geant2.jra3.network.LinkIdentifiers;

/**
 * Provides abstract identifiers from an external AutoBAHN domain.
 * 
 * @author <a href="mailto:jaxlucas@man.poznan.pl">Jacek Lukasik</a>
 *
 */
public class ExternalIdentifiersSourceImpl implements net.geant2.jra3.topologyabstraction.ExternalIdentifiersSource {

	private String idmAddress;
	
	/**
	 * Creates instance.
	 * 
	 * @param idmAddress String address of the IDM of the other AutoBAHN domain.
	 */
	public ExternalIdentifiersSourceImpl(String idmAddress) {
		this.idmAddress = idmAddress;
	}
	
	/* (non-Javadoc)
	 * @see net.geant2.jra3.intradomain.converter.ExternalIdentifiersSource#getIdentifiers(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LinkIdentifiers getIdentifiers(String domain, String portName,
			String linkBodId) {
		
		Dm2Idm dm2Idm = new Dm2IdmClient(idmAddress);
		
		return dm2Idm.getIdentifiers(domain, portName, linkBodId);
	}
}
