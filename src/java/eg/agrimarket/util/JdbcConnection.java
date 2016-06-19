package eg.agrimarket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class JdbcConnection {
	
	private static String user = "root";
	private static String pass = "root";
	private static String url = "jdbc:mysql://localhost:3306/agri_project";
	private static Connection connection;
        
	/**
	 * Default configurations
	 */
	private JdbcConnection() {
	}

	 
 
	public static Connection getConnection() throws SQLException {
            try {
                
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                if (connection == null) {
                   connection = DriverManager.getConnection(url, user, pass);	   
                }
                 

            }catch (Exception ex) {
                ex.printStackTrace();
             }
          return connection;
	}

	 

}
