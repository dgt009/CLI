import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import model.*;
import com.mds.database.DestinationDatabaseConnection;
import com.mds.database.SourceDatabaseConnection;

/**
 * Copyright (C) Maritime Data Systems, GmbH - All Rights Reserved Unauthorized copying of this file, via any medium is
 * strictly prohibited Proprietary and confidential Written by dhruvil, Oct 23, 2015
 */

public class Shipyards {

	private String[] args = null;
	private Options options = new Options();

	public Shipyards(String args[]) {
		this.args = args;
		// Source Details
		options.addOption("srH", "srH", true, "Source Host");
		options.addOption("srPo", "srPo", true, "Source Port");
		options.addOption("srDB", "srDB", true, "Source Database");
		options.addOption("srU", "srU", true, "Source Username");
		options.addOption("srP", "srP", false, "Source Password");

		// Destination Details
		options.addOption("deH", "deH", true, "Destination Host");
		options.addOption("dePo", "dePo", true, "Destination Port");
		options.addOption("deDB", "deDB", true, "Destination Database");
		options.addOption("deU", "deU", true, "Destination Username");
		options.addOption("deP", "deP", true, "Destination Password");

	}

	public int parse() throws SQLException, InvalidPropertiesFormatException, IOException, InterruptedException {
		CommandLineParser parser = new BasicParser();
		CommandLine commandLine = null;
		Timestamp lastModificationDate;

		try {
			commandLine = parser.parse(options, args);
		} catch (ParseException e) {

		}

		model.CliParameter parameter = new CliParameter();

		// getting source parameters
		if (commandLine.hasOption("srH")) {
			parameter.setSrH(commandLine.getOptionValue("srH"));
		} else {
		}
		// source Port
		if (commandLine.hasOption("srPo")) {
			parameter.setSrPo(commandLine.getOptionValue("srPo"));
		} else {
		}
		if (commandLine.hasOption("srDB")) {
			parameter.setSrDB(commandLine.getOptionValue("srDB"));
		} else {
		}
		if (commandLine.hasOption("srU")) {
			parameter.setSrU(commandLine.getOptionValue("srU"));
		} else {
		}
		// source Password
		if (commandLine.hasOption("srP")) {
			parameter.setSrP(commandLine.getOptionValue("srP"));
		} else {
		}

		if (commandLine.hasOption("deH")) {
			parameter.setDeH(commandLine.getOptionValue("deH"));
		} else {
		}
		if (commandLine.hasOption("dePo")) {
			parameter.setDePo(commandLine.getOptionValue("dePo"));
		} else {
		}
		if (commandLine.hasOption("deDB")) {
			parameter.setDeDB(commandLine.getOptionValue("deDB"));
		} else {
		}
		if (commandLine.hasOption("deU")) {
			parameter.setDeU(commandLine.getOptionValue("deU"));
		} else {
		}
		if (commandLine.hasOption("deP")) {
			parameter.setDeP(commandLine.getOptionValue("deP"));
		} else {
		}

		Connection sourceConnection = SourceDatabaseConnection.getConnection(parameter);
		Connection destinationConnection = DestinationDatabaseConnection.getConnection(parameter);

		getShipyardData(sourceConnection, destinationConnection, parameter);

		if (sourceConnection != null || destinationConnection != null) {
			sourceConnection.close();
			destinationConnection.close();
		}
		return 0;
	}

	private void getShipyardData(Connection sourceConnection, Connection destinationConnection,
			CliParameter parameter) {
		Queries q = Queries.instantiate();
		System.out.println(parameter.getDePo() + "  localhost");
		System.out.println(parameter.getSrPo() + "  seatracks");

		// already have enough positions ######warning#######
		// createPostionsTable(destinationConnection, q); // might contain data ,CAREFUL
		// getAISPositions(sourceConnection, destinationConnection, q);
		generateCSV(destinationConnection, q);
		generateCSVWithoutVessels(destinationConnection, q);

	}

	/**
	 * @param destinationConnection
	 * @param q
	 */
	private void createPostionsTable(Connection destinationConnection, Queries q) {
		try (PreparedStatement psCreateTable = destinationConnection.prepareStatement(q.getQuery("createTable"));) {
			psCreateTable.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param sourceConnection
	 * @param destinationConnection
	 * @param q
	 */
	private void getAISPositions(Connection sourceConnection, Connection destinationConnection, Queries q) {
		try (PreparedStatement psGetAISPositions = sourceConnection.prepareStatement(q.getQuery("getAisPositions"));
				PreparedStatement insetAISPositions = destinationConnection
						.prepareStatement(q.getQuery("insertAisPositions"));) {
			long free = Runtime.getRuntime().freeMemory();
			System.out.println("heapsize is::" + free);
			long max_mem = Runtime.getRuntime().maxMemory();
			System.out.println("heapsize is::" + max_mem);
			long ava_pro = Runtime.getRuntime().availableProcessors();
			System.out.println("heapsize is::" + ava_pro);
			ResultSet rs = psGetAISPositions.executeQuery();
			while (rs.next()) {
				Array timestamp = rs.getArray("timestamps");
				Array lon = rs.getArray("lons");
				Array lat = rs.getArray("lats");
				int imo = rs.getInt("imo");
				String vessel_name = rs.getString("vessel_name");

				Timestamp[] timestamps = (Timestamp[]) timestamp.getArray();
				BigDecimal[] lons = (BigDecimal[]) lon.getArray();
				BigDecimal[] lats = (BigDecimal[]) lat.getArray();

				for (int i = 0; i < timestamps.length; i++) {
					GeoPoint p = new GeoPoint(lons[i].floatValue(), lats[i].floatValue());
					System.out.println(p.toPostGisPoint());
					insetAISPositions.setObject(1, p.toPostGisPoint(), Types.OTHER);
					insetAISPositions.setTimestamp(2, timestamps[i]);
					insetAISPositions.setInt(3, imo);
					insetAISPositions.setString(4, vessel_name);
					insetAISPositions.addBatch();
				}
				insetAISPositions.executeBatch();
				System.out.println("########");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void generateCSV(Connection destinationConnection, Queries q) {
		try (PreparedStatement ps = destinationConnection.prepareStatement(q.getQuery("getShipyardsAndVesselsData"));) {
			ResultSet rs = ps.executeQuery();
			List<Shipyard> list = new ArrayList<Shipyard>();
			while (rs.next()) {
				Shipyard s = new Shipyard();
				s.setShipyard_name(rs.getString("shipyard_name"));
				s.setVessel_name(rs.getString("vessel_name"));
				list.add(s);
			}
			createCsvFile(list);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createCsvFile(List<Shipyard> list) {
		String fileName = "shipyards.csv";
		String FILE_HEADER = "vessel name, shipyard name";
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			for (Shipyard data : list) {
				fileWriter.append(data.getVessel_name());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(data.getShipyard_name());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}

	private void generateCSVWithoutVessels(Connection destinationConnection, Queries q) {
		try (PreparedStatement ps = destinationConnection.prepareStatement(q.getQuery("getShipyardswithoutVessels"));) {
			ResultSet rs = ps.executeQuery();
			List<Shipyard> list = new ArrayList<Shipyard>();
			while (rs.next()) {
				Shipyard s = new Shipyard();
				s.setShipyard_name(rs.getString("shipyard_name"));
				list.add(s);
			}
			createCsvFileWithoutVessels(list);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createCsvFileWithoutVessels(List<Shipyard> list) {

		String fileName = "shipyards_without_vessels.csv";
		String FILE_HEADER = "shipyard without vessels";
		String COMMA_DELIMITER = ",";
		String NEW_LINE_SEPARATOR = "\n";
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER);
			fileWriter.append(NEW_LINE_SEPARATOR);
			for (Shipyard data : list) {
				fileWriter.append(data.getShipyard_name());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {

			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}

		}

	}
}