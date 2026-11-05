# KIET Hostel Management System (Java JDBC + MySQL)

## Project Description
The KIET Hostel Management System is a menu-driven Java application developed using JDBC and MySQL. It manages student hostel accommodation records and performs database operations.

## Technologies Used
- Java
- JDBC (Java Database Connectivity)
- MySQL
- MySQL Connector/J
- IntelliJ IDEA

## Database Details
**Database Name:** `hostel_db`

**Table Name:** `HostelStudent`

### Table Attributes
| Column Name | Data Type | Description |
|---|---|---|
| student_id | INT | Primary Key |
| student_name | VARCHAR(100) | Student Name |
| roll_number | VARCHAR(20) | Roll Number |
| hostel_name | VARCHAR(50) | Hostel Name |
| room_number | VARCHAR(20) | Room Number |
| fee | DOUBLE | Hostel Fee |

## Features

1. **Insert Student Records:** Insert at least three student records using PreparedStatement and handle duplicate student IDs.
2. **Conditional Retrieval:** Display students of a particular hostel whose fee is greater than ₹50,000, sorted in descending order of fee.
3. **Update Hostel Fee:** Update the hostel fee using student ID.
4. **Search and Modify Hostel Details:** Search a student using ID and update their room number.
5. **Aggregate and Delete:** Calculate total hostel fees using SUM(fee) and delete a student only if their fee is greater than zero.

## Project Structure
```text
HostelManagementSystem
│
└── src
    └── HostelManagement.java
```

## Database Setup

Execute the following SQL commands in MySQL:

```sql
CREATE DATABASE hostel_db;

USE hostel_db;

CREATE TABLE HostelStudent (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(100),
    roll_number VARCHAR(20),
    hostel_name VARCHAR(50),
    room_number VARCHAR(20),
    fee DOUBLE
);
```

## How to Run the Project

1. Install Java JDK.
2. Install MySQL and open MySQL Workbench.
3. Create the `hostel_db` database and `HostelStudent` table.
4. Create a Java project in IntelliJ IDEA.
5. Add the MySQL Connector/J library.
6. Create `HostelManagement.java` and paste the Java code.
7. Update the MySQL username and password in the code.
8. Run the Java application.
9. Select the required option from the menu.

## JDBC Methods Used

- `DriverManager.getConnection()` – Establish database connection.
- `prepareStatement()` – Prepare SQL queries.
- `executeUpdate()` – Insert, update and delete records.
- `executeQuery()` – Execute SELECT queries.
- `ResultSet` – Retrieve database records.
- `close()` – Close database connection.

## Conclusion

The project demonstrates how Java JDBC connects with MySQL and performs CRUD operations. It provides a simple way to manage hostel student records, update accommodation details, calculate fees and delete records.
