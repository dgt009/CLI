/** 
 * Copyright (C) Maritime Data Systems, GmbH - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by dhruvil, Jun 1, 2015
 */

package com.mds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SourceDatabaseConnection {
	private static final Logger LOGGER = Logger.getLogger(SourceDatabaseConnection.class.getName());

	// Connection connection = DriverManager.getConnection(url, username,
	// password);
	public static Connection getConnection(model.CliParameter parameter) {
		LOGGER.log(Level.INFO, "inside getConnection()");
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		String url = "jdbc:postgresql://" + parameter.getSrH() + ":" + parameter.getSrPo() + "/" + parameter.getSrDB();
		// System.out.println(url);

		Properties props = new Properties();

		props.setProperty("user", parameter.getSrU());

		if (parameter.getSrP() == null || parameter.getSrP().equals("")) {
			props.setProperty("password", "");
		} else {
			props.setProperty("password", parameter.getSrP());
		}

		// props.setProperty("ssl","true");
		try {
			conn = DriverManager.getConnection(url, props);

			// test query
			/*
			 * Statement statement = conn.createStatement(); statement.execute("select * from pg_tables"); ResultSet rs
			 * = statement.getResultSet(); while (rs.next()){ System.out.println(rs.getString("tablename")); } //
			 * statement.execute("select * from vessels limit 10");
			 */

			// System.out.println("connection Established with database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.log(Level.SEVERE, "inside getConnection()", e);

			// System.out.println("Error in establishing connection with database");
		}

		return conn;
	}
}
