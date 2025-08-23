/*
 * Student Registration System
 * A simple Java Swing + MySQL program to manage student records.
 * Features: Add, Delete, Update, Show All.
 */

package Registration;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class StudentRegistration {

    private static final String URL = "jdbc:mysql://localhost:3306/student";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Registration");

        JTextField idText = new JTextField();
        JTextField nameText = new JTextField();
        JTextField mobileText = new JTextField();

        JButton addBtn = new JButton("Add");
        JButton delBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");
        JButton showBtn = new JButton("Show All");

        JLabel label1 = new JLabel("ID: ");
        JLabel label2 = new JLabel("Name: ");
        JLabel label3 = new JLabel("Mobile: ");

        frame.setLayout(new GridLayout(6, 2, 10, 10));
        frame.add(label1); frame.add(idText);
        frame.add(label2); frame.add(nameText);
        frame.add(label3); frame.add(mobileText);
        frame.add(addBtn); frame.add(delBtn);
        frame.add(updateBtn); frame.add(showBtn);
        frame.add(new JLabel()); 
        frame.add(new JLabel());

        frame.setSize(400, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // INSERT
        addBtn.addActionListener(e -> {
            String sql = "INSERT INTO details (id, name, mobile) VALUES (?, ?, ?)";
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, Integer.parseInt(idText.getText()));
                ps.setString(2, nameText.getText());
                ps.setString(3, mobileText.getText());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Record Inserted");
            } catch (Exception ex) {
                showError(frame, ex);
            }
        });

        // DELETE
        delBtn.addActionListener(e -> {
            String sql = "DELETE FROM details WHERE id = ?";
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, Integer.parseInt(idText.getText()));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Record Deleted");
            } catch (Exception ex) {
                showError(frame, ex);
            }
        });

        // UPDATE
        updateBtn.addActionListener(e -> {
            String sql = "UPDATE details SET name = ?, mobile = ? WHERE id = ?";
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, nameText.getText());
                ps.setString(2, mobileText.getText());
                ps.setInt(3, Integer.parseInt(idText.getText()));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(frame, "Record Updated");
            } catch (Exception ex) {
                showError(frame, ex);
            }
        });

        // SHOW ALL
        showBtn.addActionListener(e -> {
            String sql = "SELECT * FROM details";
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                StringBuilder data = new StringBuilder();
                while (rs.next()) {
                    data.append("ID: ").append(rs.getInt("id"))
                        .append(", Name: ").append(rs.getString("name"))
                        .append(", Mobile: ").append(rs.getString("mobile"))
                        .append("\n");
                }

                JOptionPane.showMessageDialog(frame, 
                    data.length() == 0 ? "No Data Found" : data.toString());

            } catch (Exception ex) {
                showError(frame, ex);
            }
        });
    }

    // Helper method to show errors
    private static void showError(Component parent, Exception ex) {
        JOptionPane.showMessageDialog(parent, "Error: " + ex.getMessage());
    }
}
