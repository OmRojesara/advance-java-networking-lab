import java.sql.*;
import java.util.Scanner;

public class StudentDatabaseApp {

    // Database credentials (Update according to your DB)
    private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("===================================");
            System.out.println("   🎓 Student Database Application ");
            System.out.println("===================================");

            boolean exit = false;

            while (!exit) {
                System.out.println("\n======= MENU =======");
                System.out.println("1. Insert Record");
                System.out.println("2. Update Record");
                System.out.println("3. Delete Record");
                System.out.println("4. Display Records");
                System.out.println("5. Exit");
                System.out.print("👉 Enter your choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine()); // safer than nextInt
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        insertRecordUI(conn, sc);
                        break;
                    case 2:
                        updateRecordUI(conn, sc);
                        break;
                    case 3:
                        deleteRecordUI(conn, sc);
                        break;
                    case 4:
                        displayRecords(conn);
                        break;
                    case 5:
                        System.out.print("⚠️  Are you sure you want to exit? (Y/N): ");
                        String confirm = sc.nextLine().trim().toUpperCase();
                        if (confirm.equals("Y")) {
                            exit = true;
                            System.out.println("👋 Thank you for using Student Database App!");
                        }
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please select between 1-5.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed!");
            e.printStackTrace();
        }
    }

    // === User Interface Layer ===
    private static void insertRecordUI(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter Mobile: ");
            String mobile = sc.nextLine().trim();

            if (name.isEmpty() || mobile.isEmpty()) {
                System.out.println("⚠️  Name/Mobile cannot be empty!");
                return;
            }

            insertRecord(conn, id, name, mobile);

        } catch (NumberFormatException e) {
            System.out.println("⚠️  ID must be a number!");
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                System.out.println("⚠️  Record with this ID already exists!");
            } else {
                System.out.println("❌ Error inserting record!");
                e.printStackTrace();
            }
        }
    }

    private static void updateRecordUI(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ID to Update: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter New Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter New Mobile: ");
            String mobile = sc.nextLine().trim();

            updateRecord(conn, id, name, mobile);

        } catch (NumberFormatException e) {
            System.out.println("⚠️  ID must be a number!");
        } catch (SQLException e) {
            System.out.println("❌ Error updating record!");
            e.printStackTrace();
        }
    }

    private static void deleteRecordUI(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter ID to Delete: ");
            int id = Integer.parseInt(sc.nextLine());
            deleteRecord(conn, id);

        } catch (NumberFormatException e) {
            System.out.println("⚠️  ID must be a number!");
        } catch (SQLException e) {
            System.out.println("❌ Error deleting record!");
            e.printStackTrace();
        }
    }

    // === Database Operations Layer ===
    private static void insertRecord(Connection conn, int id, String name, String mobile) throws SQLException {
        String query = "INSERT INTO details(id, name, mobile) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, mobile);
            ps.executeUpdate();
            System.out.println("✅ Record Inserted Successfully!");
        }
    }

    private static void updateRecord(Connection conn, int id, String name, String mobile) throws SQLException {
        String query = "UPDATE details SET name = ?, mobile = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, mobile);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Record Updated Successfully!");
            } else {
                System.out.println("⚠️  No record found with ID " + id);
            }
        }
    }

    private static void deleteRecord(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM details WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Record Deleted Successfully!");
            } else {
                System.out.println("⚠️  No record found with ID " + id);
            }
        }
    }

    private static void displayRecords(Connection conn) throws SQLException {
        String query = "SELECT * FROM details";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n📌 Student Records:");
            System.out.println("------------------------------");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        " | Name: " + rs.getString("name") +
                        " | Mobile: " + rs.getString("mobile"));
            }
            System.out.println("------------------------------");
        }
    }
}
