import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class getLegsData {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		
		
		Connection conn= null;
		Statement statement= null;
//		Connection connection = DriverManager.getConnection(url, username, password);
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://localhost/postgres";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","");
		//props.setProperty("ssl","true");
		try {
			 conn = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			System.out.println("Error in establishing connection with database");
		}
		
		System.out.println("connection Established with database");
		statement = conn.createStatement();
		String sql;
	      sql = "SELECT * FROM ships;";
	      ResultSet rs = statement.executeQuery(sql);
	      
	      
	      while(rs.next()){
	    	  int leg_id = rs.getInt("id");
	    	  String name = rs.getString("name");
	    	  String IMO = rs.getString("IMO");
	    	  System.out.println(leg_id);
	    	  System.out.println(name);
	    	  System.out.println(IMO+" validity:"+ new methodCollection().checkIMO(IMO));
	    	  
	    	  
	    	  
	      }
		

	}

}
