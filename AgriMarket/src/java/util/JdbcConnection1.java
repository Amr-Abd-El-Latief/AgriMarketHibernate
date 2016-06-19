package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection1 {

    private static String user = "root";
    private static String pass = "root";
    private static String url = "jdbc:mysql://localhost:3306/agri_project";
    private static Connection connection;

    /**
     * Default configurations
     */
    public JdbcConnection1() {
    }

    public JdbcConnection1(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;

        System.out.println("::::: " + user + "/" + pass + "/" + url);
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            try {

                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(url, user, pass);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

}
