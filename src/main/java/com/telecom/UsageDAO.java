//
package main.java.com.telecom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsageDAO {
    public UsageRecord getUsageByCustomer(String mobile) {
        String sql = "SELECT * FROM usage_records WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UsageRecord ur = new UsageRecord();
                ur.setDataLeft(rs.getDouble("data_used_gb"));
                ur.setDaysLeft(rs.getInt("calls_used_minutes"));
                return ur;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */