package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * This class is used to perform insertion operation into DB.
 *
 */
public class FileDBOperation {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileDBOperation.class);

	public void storeEventLogsIntoDB(AlertLogEvent alertLogEvent) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		try {
			// Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			// Creating the connection with HSQLDB

			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
			if (con != null) {
				LOGGER.info("Connection created successfully");

			} else {
				LOGGER.info("Problem with creating connection");
			}
			preparedStatement = con.prepareStatement("INSERT into eventlog values (?,?,?,?,?)");
			preparedStatement.setString(0, alertLogEvent.getId());
			preparedStatement.setLong(1, alertLogEvent.getEventDuration());
			preparedStatement.setString(2, alertLogEvent.getType());
			preparedStatement.setString(3, alertLogEvent.getHost());
			preparedStatement.setString(4, String.valueOf(alertLogEvent.isAlert()));
			boolean isInserted = preparedStatement.execute();
			LOGGER.info("Inserted successfully {}" ,isInserted);
			con.commit();

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
