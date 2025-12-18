package main.java.com.telecom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Customer getCustomerByMobile(String mobile) {
        String sql = "SELECT * FROM customers WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("mobile_number"), rs.getString("password"), rs.getInt("currentPlanId"), rs.getDate("validity")
                ,rs.getString("name"),
                rs.getString("email"), rs.getInt("customer_id"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name,email,mobile_number, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getMobile());
            ps.setString(4, customer.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public String getPasswordByMobile(String mobile) {
    String sql = "SELECT password FROM customers WHERE mobile_number = ?";
    
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, mobile);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("password");
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
    return null; // customer not found
}

    public void addCustomer(String mobile, String password, String name, String email) {
        String sql = "INSERT INTO customers (name,email,mobile_number, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, mobile);
            ps.setString(4, password);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateCustomerProfile(Customer customer) {
        String sql = "UPDATE customers SET password = ?, currentPlanId = ?, validity = ? WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customer.getPassword());
            ps.setString(5, customer.getEmail());
            ps.setObject(2, customer.getCurrentPlanId());
            ps.setDate(3, customer.getValidity() != null ? new java.sql.Date(customer.getValidity().getTime()) : null);
            ps.setString(4, customer.getMobile());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customers.add(new Customer(rs.getString("mobile_number"), rs.getString("password"), rs.getInt("currentPlanId"), rs.getDate("validity"), rs.getString("name"), rs.getString("email"), rs.getInt("customer_id")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customers;
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */