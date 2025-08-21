import java.sql.*;

public class EmployeeCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String pwd = "";

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection con = DriverManager.getConnection(url, user, pwd);

            // Insert record (safe way)
            String insertSQL = "INSERT INTO employee(eid, ename) VALUES (?, ?)";
            PreparedStatement insertStmt = con.prepareStatement(insertSQL);
            insertStmt.setInt(1, 1);
            insertStmt.setString(2, "kamiya");
            insertStmt.executeUpdate();

            // Update record
            String updateSQL = "UPDATE employee SET eid=? WHERE ename=?";
            PreparedStatement updateStmt = con.prepareStatement(updateSQL);
            updateStmt.setInt(1, 5);
            updateStmt.setString(2, "manish");
            updateStmt.executeUpdate();

            // Select records
            String selectSQL = "SELECT eid, ename FROM employee";
            PreparedStatement selectStmt = con.prepareStatement(selectSQL);
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int eid = rs.getInt("eid");
                String ename = rs.getString("ename");
                System.out.println("Employee id: " + eid);
                System.out.println("Employee name: " + ename);
            }

            // Delete record
            String deleteSQL = "DELETE FROM employee WHERE eid=?";
            PreparedStatement deleteStmt = con.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, 7);
            deleteStmt.executeUpdate();

            // Close resources
            rs.close();
            insertStmt.close();
            updateStmt.close();
            selectStmt.close();
            deleteStmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}