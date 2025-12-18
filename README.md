# 📡 Telecom Management System (Java + MySQL)

A **console-based Telecom Management System** built using **Java and MySQL**, designed to manage customers, employees, telecom plans, and usage records.  
The project follows the **DAO (Data Access Object) design pattern** and demonstrates clean separation between business logic and database operations.

---

Features

### Customer Module
- Customer registration
- Secure customer login
- Profile update (name, email, password)
- Mobile number validation

### Employee/Admin Module
- Employee login
- Add new telecom plans
- View all available plans
- Manage customer usage records

### Usage Management
- Store customer usage data
- Track usage linked with mobile numbers
- Retrieve usage records for customers

---

## 🛠️ Technologies Used
- **Java (Core Java, OOPs)**
- **JDBC (MySQL Connector)**
- **MySQL Database**
- **DAO Design Pattern**
- **SQL (CRUD operations)**
- **Git & GitHub**

---

## 🧱 Project Structure

telecom-billing-system/
│
├── src/main/java/com/telecom/
│ ├── DBUtil.java
│ ├── TelecomApp.java
│ ├── Customer.java
│ ├── Employee.java
│ ├── Plan.java
│ ├── UsageRecord.java
│ ├── CustomerDAO.java
│ ├── EmployeeDAO.java
│ ├── PlanDAO.java
│ └── UsageDAO.java
│
└── README.md

## Database Design

### Tables Used
- `customers`
- `employees`
- `plans`
- `usage_records`

All tables use **primary keys**, proper **foreign key relationships**, and follow normalization rules.

 By : Name: Suprita Thakur
     Email: thakursuprita30@gmail.com