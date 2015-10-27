import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 */

/**
 * @author dhruvil
 * 
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 * @throws SecurityException
	 */

	private static final String filePath = "/home/dhruvil/Desktop/shipyards.json";

	public static void main(String[] args) throws SecurityException, IOException, ParseException, SQLException,
			ClassNotFoundException, InterruptedException {
		Connection conn = null;

		// Connection connection = DriverManager.getConnection(url, username, password);
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost/routing";
		Properties props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "");
		// props.setProperty("ssl", "true");
		try {
			conn = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in establishing connection with database");
		}

		System.out.println("connection Established with database");
		FileReader reader = new FileReader(filePath);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
		JSONArray featureArray = (JSONArray) jsonObject.get("features");
		PreparedStatement psInsert = null;
		for (int i = 0; i < featureArray.size(); i++) {
			JSONObject featureObject = new JSONObject();
			featureObject = (JSONObject) featureArray.get(i);
			JSONObject propertiesObject = (JSONObject) featureObject.get("properties");
			String name = (String) propertiesObject.get("name");
			JSONObject geometryObject = (JSONObject) featureObject.get("geometry");
			String geom = geometryObject.toString();
			String type = (String) geometryObject.get("type");
			/* create shipyards_points table first */
			// if (type.equalsIgnoreCase("Point")) {
			if (type.equalsIgnoreCase("Polygon")) {
				psInsert = conn.prepareStatement(
						"insert into shipyards_polygons(name,polygon) values (?,ST_SetSRID(st_geomfromgeojson(?),4326))");
				// "insert into shipyards_points(name,point) values (?,ST_SetSRID(st_geomfromgeojson(?),4326))");
				psInsert.setString(1, name);
				psInsert.setString(2, geom);
				psInsert.execute();
				System.out.println(name);
				System.out.println(type + " " + geom);
			}

		}
		new Shipyards(args).parse();
		// new CLI(args).parse();

	}
}
