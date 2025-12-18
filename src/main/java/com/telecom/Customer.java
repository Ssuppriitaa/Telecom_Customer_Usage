package main.java.com.telecom;
import java.util.Date;

public class Customer {
    private int customer_id;
    private String mobile_number;
    private String email;
    private String name;
    private String password;
    private Integer currentPlanId; // Nullable
    public Date validity;

    public Customer() {}

    public Customer(String mobile, String password, Integer currentPlanId, Date validity, String name, String email, int customer_id) {
        this.mobile_number = mobile;
        this.password = password;
        this.currentPlanId = currentPlanId;
        this.validity = validity;
        this.name = name;
        this.email = email;
        this.customer_id = customer_id;
    }

    // Getters and Setters
    public String getMobile() { return mobile_number; }
    public void setMobile(String mobile) { this.mobile_number = mobile; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getCurrentPlanId() { return currentPlanId; }
    public void setCurrentPlanId(Integer currentPlanId) { this.currentPlanId = currentPlanId; }
    public Date getValidity() { return validity; }
    public void setValidity(Date validity) { this.validity = validity; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    @Override
    public String toString() {
        return "Customer{mobile='" + mobile_number + "', password='" + password + "', currentPlanId=" + currentPlanId + ", validity=" + validity + ", name='" + name + "', email='" + email + "', customer_id=" + customer_id + "}";
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */