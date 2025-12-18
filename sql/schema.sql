import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/telecom_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"; // Updated URL from your error
    private static final String USER = "root"; // Adjust as needed
    private static final String PASSWORD = "Suprita@300510"; // Adjust as needed

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */