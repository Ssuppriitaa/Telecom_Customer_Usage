package main.java.com.telecom;//
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
    public Employee getEmployeeByMobile(String mobile, String password) {
        System.out.println("Retrieving employee by mobile: " + mobile);
        String sql = "SELECT * FROM employees WHERE mobile = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("DB Connection successful for getEmployeeByMobile.");
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee e = new Employee(rs.getString("mobile"), rs.getString("password"), rs.getString("name"), rs.getString("email"), rs.getString("role"));
                System.out.println("Employee found: " + e);
                return e;
            } else {
                System.out.println("No employee found with mobile: " + mobile);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getEmployeeByMobile: " + e.getMessage());
            System.err.println(e.getMessage());
        }
        return null;
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */