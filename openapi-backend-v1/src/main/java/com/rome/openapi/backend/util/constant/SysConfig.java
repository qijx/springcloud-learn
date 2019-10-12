/*
 * Filename SysConfig.java
 * Company 上海来伊份电子商务有限公司。
 * @author kongweixiang
 * @version 1.0.0
 */
package com.rome.openapi.backend.util.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysConfig {

	private static Logger logger = LoggerFactory.getLogger(SysConfig.class);

	private static Properties prop = new Properties();

	static {
		InputStream in = null;
		try {
			in = SysConfig.class.getResourceAsStream("/system.properties");
			prop.load(in);
		} catch (IOException ex) {
			logger.info("Some problems with system.properties");
			logger.info(ex.getMessage(), ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}

	}

	public static String get(String key) {
		return prop.getProperty(key);
	}
}
