/** 
 * Copyright (C) Maritime Data Systems, GmbH - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by dhruvil, Jun 10, 2015
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Queries {
	private static Logger LOG = Logger.getLogger(Queries.class.getName());

	private Properties properties = new Properties();

	private static Queries queries = new Queries();

	private Queries() {
	};

	public static Queries instantiate(String path) {
		try {
			if (0 == queries.properties.size()) {
				FileInputStream fos = new FileInputStream(path);
				queries.properties.loadFromXML(fos);
				fos.close();
			}
		} catch (Exception e) {
			LOG.throwing(Queries.class.getName(), "Static ", e);
		}
		return queries;
	}

	public static Queries instantiate() {
		try {
			if (0 == queries.properties.size()) {
				InputStream fos = Queries.class.getClassLoader().getResourceAsStream("queries.xml");
				queries.properties.loadFromXML(fos);
				fos.close();
			}
		} catch (Exception e) {
			LOG.throwing(Queries.class.getName(), "Static ", e);
		}
		return queries;

	}

	public String getQuery(String name) {
		return properties.getProperty(name);
	}

}
