package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//this class establishes and closes connections to the database
public class DBConnection {
    //JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06NwI";
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver and connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U06NwI";
    private static final String password = "53688815066";

    public static Connection getConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            //System.out.println("Connection successful");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("class not found exception");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("sqlexception");
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e) {
            System.out.println("Error: " +  e.getMessage());
        }
    }
}
