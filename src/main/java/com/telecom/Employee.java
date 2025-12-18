package main.java.com.telecom;

public class Employee {
    public int Emp_id;
    public String Emp_Name;
    public String Emp_Email;
    public String Emp_role;
    public String mobile;
    public String password;

    public Employee() {}

    public Employee(String pswd, String name, String department,
                    String email, String mobile) {
        this.password = pswd;
        this.Emp_Name = name;
        this.Emp_Email = email;
        this.Emp_role = department;
        this.mobile = mobile;
    }

    public Employee(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    // Getters and Setters
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getEmp_id() { return Emp_id; }
    public void setEmp_id(int emp_id) { this.Emp_id = emp_id; }
    public String getEmp_Name() { return Emp_Name; }
    public void setEmp_Name(String emp_Name) { this.Emp_Name = emp_Name; }
    public String getEmp_Email() { return Emp_Email; }
    public void setEmp_Email(String emp_Email) { this.Emp_Email = emp_Email; }
    public String getEmp_role() { return Emp_role; }
    public void setEmp_role(String emp_role) { this.Emp_role = emp_role; }


    @Override
    public String toString() {
        return "Employee{mobile='" + mobile + "', password='" + password +", Emp_id=" + Emp_id + ", Emp_Name='" + Emp_Name + "', Emp_Email='" + Emp_Email + "', Emp_role='" + Emp_role + "'}";
    }
}
/* By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com */