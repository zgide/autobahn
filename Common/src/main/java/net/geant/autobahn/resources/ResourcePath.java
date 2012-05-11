package net.geant.autobahn.resources;

import java.io.IOException;

public class ResourcePath {
	private static boolean isRunningOnApache = true;

	public ResourcePath() {
	}

	public ResourcePath(boolean onApache) {
		isRunningOnApache = onApache;
	}

	public static boolean isOnApache() {
		return isRunningOnApache;
	}

	public String getFullPath(String relative) {
		String path;

		if (isRunningOnApache) {
			path = System.getProperty("autobahn.root");
			if (path.charAt(path.length() - 1) != java.io.File.separatorChar) {
				path += java.io.File.separatorChar;
			}
			path = path + "WEB-INF" + java.io.File.separatorChar + relative;
			return path;

		} else {
			String cur;
			try {
				cur = new java.io.File(".").getCanonicalPath();
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}

			path = java.io.File.separatorChar + relative;
			return cur + path;
		}

	}
}