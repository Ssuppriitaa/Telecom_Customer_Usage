package main.java.com.telecom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TelecomApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you an Employee or Customer?");
        String role = sc.nextLine().trim();
        if (role.equalsIgnoreCase("customer")) {
            handleCustomerLogin(sc);
        } else if (role.equalsIgnoreCase("employee")) {
            handleEmployeeLogin(sc);
        } else {
            System.out.println("Invalid role. Exiting.");
        }
        sc.close();
    }
    
    
    private static void handleCustomerLogin(Scanner sc) {
        System.out.println("Are you logging in for the first time? (y/n)");
        String firstTime = sc.nextLine().trim();
        if (firstTime.equalsIgnoreCase("y")) { 
             // Create new account
        String mobile = getValidMobile(sc, "customer");
            System.out.println("Enter a password:");
            String pass = sc.nextLine().trim();
            System.out.println("Enter your name");
            String name = sc.nextLine().trim();
            System.out.println("Enter your email:");
            String email = sc.nextLine().trim();
            addCustomer(mobile, pass, name, email);
            System.out.println("Account created. Logged in.");
        } else {
            // Login to existing account
            String mobile = getValidMobile(sc, "customer");
            Customer cust = getCustomerByMobile(mobile);
            if (cust == null) {
                System.out.println("Account not found. Please create a new account first.");
                return;
            }
            else{
            System.out.println("Enter password:");
            String pass = sc.nextLine().trim();
            String dbPassword = getPasswordByMobile(mobile);
            if (dbPassword != null && dbPassword.equals(pass)) {
                System.out.println("Logged in.");
            } else {
                System.out.println("Incorrect password.");
                return;}
        }
        }
        // Customer menu
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Buy plan");
            System.out.println("2. View previous bills");
            System.out.println("3. Check remaining data/days");
            System.out.println("4. Exit");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
       case 1 -> {
           // Buy new plan (shows all plans)
           List<Plan> plans = getAllPlans();
           System.out.println("Available Plans:");
           for (Plan p : plans) {
               System.out.println("Plan_id: " + p.getId() + ", Monthly_Fee: " + p.getRate() + ", Data_Limit: " + p.getData_limit_gb() + ", Duration: " + p.getDuration()+ ", Call Limit: " + p.getCall_limit_minutes());
           }
           System.out.println("Enter Plan ID to buy:");
           int pid = sc.nextInt();
           sc.nextLine();
           Plan selected = getPlanById(pid);
           String mobile = getValidMobile(sc, "customer");
           if (selected != null) {
               updateCustomerPlan(mobile, pid);
               Date newValidity = new Date(System.currentTimeMillis() + (selected.getDuration() * 30L * 24 * 60 * 60 * 1000));
               updateCustomerValidity(mobile, newValidity);
                System.out.println("Plan purchased successfully. New validity: " + newValidity);
                }
            }
                case 2 -> {
                    // Check remaining data/days
                    String mobile = getValidMobile(sc, "customer");
                    UsageRecord ur = getUsageByCustomer(mobile);
                    if (ur != null) {
                        System.out.println("Data left: " + ur.getDataLeft() + ", Days left: " + ur.getDaysLeft());
                    } else {
                        System.out.println("No usage record found.");
                    }
                }
                case 3 -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }


    private static void handleEmployeeLogin(Scanner sc) {
        String mobile = getValidMobile(sc, "employee");
        Employee emp = getEmployeeByMobile(mobile);
        if (emp == null) {
            System.out.println("Invalid mobile number. Exiting.");
            return;
        }
        System.out.println("Enter password:");
        String pass = sc.nextLine().trim();
        if (!emp.getPassword().equals(pass)) {
            System.out.println("Invalid password. Exiting.");
            return;
        }
        System.out.println("Logged in.");


        // Employee menu
        while (true) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. Add plan");
            System.out.println("2. Delete plan");
            System.out.println("3. Check customer data");
            System.out.println("4. View all plans");
            System.out.println("5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    // Add plan (ask all details: rate, month, duration)
                  /*  System.out.println("Enter plan id:");
                    int plan_id = sc.nextInt();*/
                    System.out.println("Enter plan rate (e.g., 100.0):");
                    double rate = sc.nextDouble();
                    System.out.println("Enter plan duration (e.g., 1):");
                    int duration = sc.nextInt();
                    System.out.println("Enter plan data_limit_gb (e.g., 30):");
                    int data_limit_gb = sc.nextInt();
                    System.out.println("Enter plan call_limit_minutes (e.g., 30):");
                    int call_limit_minutes = sc.nextInt();
                    sc.nextLine();
                    if (rate > 0 && duration > 0 && data_limit_gb > 0) {

                      Plan plan = new Plan(rate, data_limit_gb, duration, call_limit_minutes);

                        PlanDAO planDAO = new PlanDAO();
                            planDAO.addPlan(plan);


                        System.out.println("Plan added successfully.");
                    } else {
                        System.out.println("Invalid details. Rate, month, and duration must be positive.");
                    }
                }
                case 2 -> {
                    // Delete plan
                    System.out.println("Enter Plan ID to delete:");
                    int pid = sc.nextInt();
                    sc.nextLine();
                    deletePlan(pid);
                    System.out.println("Plan deleted.");
                }
    
                case 3 -> {
                    // Check customer data
                    List<Customer> customers = getAllCustomers();
                    System.out.println("Customer Data:");
                    for (Customer c : customers) {
                        Plan cp = getCurrentPlanForCustomer(c.getMobile());
                        Date validity = getCustomerValidity(c.getMobile());
                        System.out.println("Phone: " + c.getMobile() + ", Current Plan ID: " + (cp != null ? cp.getId() : "None") + ", Validity: " + validity+", Name: "+c.getName()+", Email: "+c.getEmail());
                    }
                }
                case 4 -> {
                    // View all plans
                    List<Plan> allPlans = getAllPlans();
                    System.out.println("All Plans:");
                    allPlans.forEach(System.out::println);

                }
                case 5 -> {
                    System.out.println("Exiting.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // Helper method for 10-digit mobile validation
    private static String getValidMobile(Scanner sc, String role) {
        while (true) {
            System.out.println("Enter your 10-digit mobile number:");
            String mobile = sc.nextLine().trim();
            if (mobile.length() == 10 && mobile.matches("\\d{10}")) {
                return mobile;
            } 
            else {
                System.out.println("Invalid mobile number");
            }
        }
    }

    // Helper methods with inline SQL
    private static Plan getCurrentPlanForCustomer(String mobile) {
        String sql = "SELECT p.* FROM plans p JOIN customers c ON c.current_plan_id = p.id WHERE c.mobile = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Plan p = new Plan();
  /* */              p.setId(rs.getInt("id"));
                p.setRate(rs.getDouble("rate"));
                p.setDuration(rs.getInt("duration"));
                return p;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    } 

    private static Date getCustomerValidity(String mobile) {
        String sql = "SELECT validity FROM customers WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDate("validity");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private static void updateCustomerValidity(String mobile, Date validity) {
        String sql = "UPDATE customers SET validity = ? WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(validity.getTime()));
            ps.setString(2, mobile);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void updateCustomerPlan(String mobile, int planId) {
        String sql = "UPDATE customers SET currentPlanId = ? WHERE mobile_number = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, planId);
            ps.setString(2, mobile);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private static List<Plan> getAllPlans() {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plans";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("Plan_id"));
                p.setRate(rs.getDouble("monthly_fee"));
                p.setData_limit_gb(rs.getInt("data_limit_gb"));
                p.setDuration(rs.getInt("duration"));
                p.setCall_limit_minutes(rs.getInt("call_limit_minutes"));
                plans.add(p);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return plans;
    }

    private static Plan getPlanById(int id) {
        String sql = "SELECT * FROM plans WHERE Plan_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Plan p = new Plan();
                p.setId(rs.getInt("Plan_id"));
                p.setRate(rs.getDouble("monthly_fee"));
                p.setData_limit_gb(rs.getInt("data_limit_gb"));
                p.setDuration(rs.getInt("duration"));
                p.setCall_limit_minutes(rs.getInt("call_limit_minutes"));
                return p;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    

   
    private static UsageRecord getUsageByCustomer(String mobile) {
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

    private static Employee getEmployeeByMobile(String mobile) {
        String sql = "SELECT * FROM employees WHERE mobile = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobile);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                e.setMobile(rs.getString("mobile"));
                e.setPassword(rs.getString("password"));
                return e;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    private double monthly_fee;
    private int data_limit_gb;
    private int call_limit_minutes;
    private int duration;
   

     public static void addPlan(Plan plan) {
        System.out.println("Attempting to add plan: " + plan);
        String sql = "INSERT INTO plans ( monthly_fee, data_limit_gb, duration, call_limit_minutes) VALUES (?, ?, ?, ?)";
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

    public static void deletePlan(int id) {
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

    public void updatePlan(Plan plan) {
        System.out.println("Attempting to update plan: " + plan);
        String sql = "UPDATE plans SET monthly_fee = ?, call_limit_minutes = ?, duration = ?, data_limit_gb = ? WHERE plan_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("DB Connection successful for updatePlan.");
            ps.setDouble(1, plan.getRate());
            ps.setInt(2, plan.getCall_limit_minutes());
            ps.setInt(3, plan.getDuration());
            ps.setInt(4, plan.getData_limit_gb());
            ps.setInt(5, plan.getId());
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows updated in plans: " + rowsAffected);
            if (rowsAffected > 0) {
                System.out.println("Plan updated successfully.");
            } else {
                System.err.println("No rows updated in plans. Check if plan exists.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in updatePlan: " + e.getMessage());
            System.err.println(e.getMessage());
        }
    }

     public void Plan(double rate, int data, int duration, int callLimit) {
        this.monthly_fee = rate;
        this.data_limit_gb = data;
        this.call_limit_minutes = callLimit;
        this.duration = duration;
    }
    //customer dao
    public static Customer getCustomerByMobile(String mobile) {
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
    public static void addCustomer(String mobile, String password, String name, String email) {
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

    public static List<Customer> getAllCustomers() {
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
    public static String getPasswordByMobile(String mobile) {
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
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */