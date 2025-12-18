package main.java.com.telecom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/telecom_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "password"; 

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the MySQL driver
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */