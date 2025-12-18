//
package main.java.com.telecom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    public List<Plan> getAllPlans() {
        List<Plan> plans = new ArrayList<>();
        System.out.println("Retrieving all plans.");
        String sql = "SELECT * FROM plans";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("DB Connection successful for getAllPlans.");
            while (rs.next()) {
                Plan p = new Plan(rs.getInt("Plan_id"), rs.getDouble("monthly_fee"), rs.getInt("data_limit_gb"), rs.getInt("duration"), rs.getInt("call_limit_minutes"));
                plans.add(p);
                System.out.println("Retrieved plan: " + p);
            }
            System.out.println("Total plans retrieved: " + plans.size());
        } catch (SQLException e) {
            System.err.println("SQL Error in getAllPlans: " + e.getMessage());
            System.err.println(e.getMessage());
        }
        return plans;
    }

    public Plan getPlanById(int id) {
        System.out.println("Retrieving plan by ID: " + id);
        String sql = "SELECT * FROM plans WHERE Plan_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("DB Connection successful for getPlanById.");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Plan p = new Plan(rs.getInt("Plan_id"), rs.getDouble("monthly_fee"), rs.getInt("data_limit_gb"), rs.getInt("duration"), rs.getInt("call_limit_minutes"));
                System.out.println("Plan found: " + p);
                return p;
            } else {
                System.out.println("No plan found with ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in getPlanById: " + e.getMessage());
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void addPlan(Plan plan) {
        System.out.println("Attempting to add plan: " + plan);
        String sql = "INSERT INTO plans (monthly_fee, data_limit_gb, duration, call_limit_minutes) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            System.out.println("DB Connection successful for addPlan.");

            ps.setDouble(1, plan.getRate());
            ps.setInt(2, plan.getData_limit_gb());
            ps.setInt(3, plan.getDuration());
            ps.setInt(4, plan.getCall_limit_minutes());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows inserted in plans: " + rowsAffected);
            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    plan.setId(rs.getInt(1));
                    System.out.println("Plan added successfully with ID: " + plan.getId());
                }
            } else {
                System.err.println("No rows inserted in plans. Check data or table schema.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in addPlan: " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }

    public void deletePlan(int id) {
        System.out.println("Attempting to delete plan with ID: " + id);
        String sql = "DELETE FROM plans WHERE Plan_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("DB Connection successful for deletePlan.");
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows deleted in plans: " + rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Plan deleted successfully.");
            } else {
                System.err.println("No rows deleted in plans. Check if plan exists.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in deletePlan: " + e.getMessage());
           System.err.println(e.getMessage());
        }
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */