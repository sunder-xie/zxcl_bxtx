package com.zxcl.webapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zxj
 *
 */
public class PropertiesUtil {
	private Properties proper;

	public PropertiesUtil(String configName) {
		try {
			this.proper = new Properties();
			InputStream in = getClass().getResourceAsStream("/" + configName);
			this.proper.load(in);
		} catch (IOException e) {
		}
	}

	public String getProperty(String key) {
		return this.proper.getProperty(key);
	}
}