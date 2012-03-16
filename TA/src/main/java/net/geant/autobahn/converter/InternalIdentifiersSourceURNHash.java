package net.geant.autobahn.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.CRC32;

public class InternalIdentifiersSourceURNHash implements
		InternalIdentifiersSource {

	static String delimiter = ":";

	String domain;
	boolean encrypt;

	public static final String VIRTUAL_PORT = "pv";
	public static final String CLIENT_PORT = "pc";
	public static final String INTER_PORT = "pi";
	public static final String CLIENT_NODE = "nc";
	public static final String EDGE_NODE = "ne";
	public static final String VIRTUAL_LINK = "lv";
	public static final String INTER_LINK = "li";
	public static final String CLIENT_LINK = "lc";

	/**
	 * Creates an instance of the identifiers source.
	 * 
	 * @param domain
	 */
	public InternalIdentifiersSourceURNHash(String domain, boolean encrypt) {
		this.domain = domain;
		this.encrypt = encrypt;
	}

	public String generateNodeID(String name, String desc) {
		if (encrypt) {
			return domain + delimiter + desc + delimiter + getHash(name);
		} else {
			return domain + delimiter + desc + delimiter + name;
		}
	}

	public String generatePortID(String snode, String dnode, String desc) {
		if (encrypt) {
			return domain + delimiter + desc + delimiter
					+ getHash(snode + delimiter + dnode);
		} else {
			return domain + delimiter + desc + delimiter + snode + delimiter
					+ dnode;
		}
	}

	public String generateClientPortID(String sname, String desc) {
		if (encrypt) {
			return domain + delimiter + desc + delimiter + getHash(sname);
		} else {
			return domain + delimiter + desc + delimiter + sname;
		}
	}

	public String generateLinkID(String startPort, String endPort, String desc) {
		List<String> ports = new ArrayList<String>();
		ports.add(startPort);
		ports.add(endPort);
		Collections.sort(ports);

		if (encrypt) {
			return domain + delimiter + desc + delimiter
					+ getHash(ports.get(0) + delimiter + ports.get(1));
		} else {
			return domain + delimiter + desc + delimiter + ports.get(0)
					+ delimiter + ports.get(1);
		}
	}

	private String getHash(String arg) {
		CRC32 crc = new CRC32();
		crc.update(arg.getBytes());

		return Long.toHexString(crc.getValue());
	}
}
