import java.sql.*;
        import java.util.Scanner;

public class HostelManagement {

    static final String URL = "jdbc:mysql://localhost:3306/hostel_db";
    static final String USER = "root";
    static final String PASSWORD = "admin";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Connected to Hostel Database!");

            int choice;

            do {
                System.out.println("\n===== KIET HOSTEL MANAGEMENT SYSTEM =====");
                System.out.println("1. Insert Student Records");
                System.out.println("2. Display Students by Hostel");
                System.out.println("3. Update Hostel Fee");
                System.out.println("4. Search and Update Room Number");
                System.out.println("5. Display Total Fee and Delete Student");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        insertStudents(con);
                        break;

                    case 2:
                        retrieveStudents(con);
                        break;

                    case 3:
                        updateFee(con);
                        break;

                    case 4:
                        updateRoom(con);
                        break;

                    case 5:
                        totalFeeAndDelete(con);
                        break;

                    case 6:
                        System.out.println("Thank you!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 6);

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // (a) INSERT STUDENT RECORDS

    static void insertStudents(Connection con) {

        String sql = "INSERT INTO HostelStudent VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.print("Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Student Name: ");
            String name = sc.nextLine();

            System.out.print("Roll Number: ");
            String roll = sc.nextLine();

            System.out.print("Hostel Name: ");
            String hostel = sc.nextLine();

            System.out.print("Room Number: ");
            String room = sc.nextLine();

            System.out.print("Hostel Fee: ");
            double fee = sc.nextDouble();

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, roll);
            ps.setString(4, hostel);
            ps.setString(5, room);
            ps.setDouble(6, fee);

            ps.executeUpdate();

            System.out.println("Student inserted successfully!");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate Student ID!");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    // (b) RETRIEVE STUDENTS AND SORT BY FEE
    static void retrieveStudents(Connection con) {

        String sql = "SELECT * FROM HostelStudent " +
                "WHERE hostel_name = ? AND fee > 50000 " +
                "ORDER BY fee DESC";

        try {
            System.out.print("Enter Hostel Name: ");
            sc.nextLine();
            String hostel = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, hostel);

            ResultSet rs = ps.executeQuery();

            System.out.println("\nStudents with fee greater than 50000:");

            while (rs.next()) {
                System.out.println(
                        "ID: " + rs.getInt("student_id") +
                                ", Name: " + rs.getString("student_name") +
                                ", Roll: " + rs.getString("roll_number") +
                                ", Hostel: " + rs.getString("hostel_name") +
                                ", Room: " + rs.getString("room_number") +
                                ", Fee: " + rs.getDouble("fee")
                );
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // (c) UPDATE HOSTEL FEE
    static void updateFee(Connection con) {

        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            System.out.print("Enter New Hostel Fee: ");
            double fee = sc.nextDouble();

            String sql = "UPDATE HostelStudent SET fee = ? WHERE student_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, fee);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Hostel fee updated successfully!");
            } else {
                System.out.println("Student record does not exist!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // (d) SEARCH AND UPDATE ROOM NUMBER
    static void updateRoom(Connection con) {

        try {
            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String search = "SELECT * FROM HostelStudent WHERE student_id = ?";

            PreparedStatement ps = con.prepareStatement(search);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\nStudent Details:");
                System.out.println("Name: " + rs.getString("student_name"));
                System.out.println("Roll Number: " + rs.getString("roll_number"));
                System.out.println("Hostel: " + rs.getString("hostel_name"));
                System.out.println("Old Room: " + rs.getString("room_number"));
                System.out.println("Fee: " + rs.getDouble("fee"));

                System.out.print("Enter New Room Number: ");
                String room = sc.nextLine();

                String update = "UPDATE HostelStudent SET room_number = ? WHERE student_id = ?";

                PreparedStatement ps2 = con.prepareStatement(update);
                ps2.setString(1, room);
                ps2.setInt(2, id);

                ps2.executeUpdate();

                System.out.println("\nRoom updated successfully!");

                String updated = "SELECT * FROM HostelStudent WHERE student_id = ?";
                PreparedStatement ps3 = con.prepareStatement(updated);
                ps3.setInt(1, id);

                ResultSet rs2 = ps3.executeQuery();

                if (rs2.next()) {
                    System.out.println("Updated Student Details:");
                    System.out.println("ID: " + rs2.getInt("student_id"));
                    System.out.println("Name: " + rs2.getString("student_name"));
                    System.out.println("Roll: " + rs2.getString("roll_number"));
                    System.out.println("Hostel: " + rs2.getString("hostel_name"));
                    System.out.println("Room: " + rs2.getString("room_number"));
                    System.out.println("Fee: " + rs2.getDouble("fee"));
                }

            } else {
                System.out.println("Student not found!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // (e) AGGREGATE QUERY AND CONDITIONAL DELETE
    static void totalFeeAndDelete(Connection con) {

        try {
            String sql = "SELECT SUM(fee) AS total FROM HostelStudent";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Total Hostel Fee Collected: Rs. " +
                        rs.getDouble("total"));
            }

            System.out.print("Enter Student ID to delete: ");
            int id = sc.nextInt();

            String delete = "DELETE FROM HostelStudent " +
                    "WHERE student_id = ? AND fee > 0";

            PreparedStatement ps = con.prepareStatement(delete);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Record not found or fee is not fully paid!");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
