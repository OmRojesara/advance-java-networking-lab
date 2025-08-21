import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EmployeeManagerFrame {
    Connection con;
    // ...existing code...

    public EmployeeManagerFrame() {
        JFrame f = new JFrame("Employee Management");

        JLabel l1 = new JLabel("Employee ID:");
        JLabel l2 = new JLabel("Employee Name:");
        JTextField t1 = new JTextField();
        JTextField t2 = new JTextField();

        JButton insertBtn = new JButton("Insert");
        JButton selectBtn = new JButton("Select");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        f.setLayout(new GridLayout(4, 2));
        f.add(l1); f.add(t1);
        f.add(l2); f.add(t2);
        f.add(insertBtn); f.add(selectBtn);
        f.add(updateBtn); f.add(deleteBtn);

        insertBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eidStr = t1.getText().trim();
                String ename = t2.getText().trim();
                if (eidStr.isEmpty() || ename.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Please enter both Employee ID and Name.");
                    return;
                }
                PreparedStatement pst = null;
                try {
                    int eid = Integer.parseInt(eidStr);
                    pst = con.prepareStatement("INSERT INTO emp (eid, ename) VALUES (?, ?)");
                    pst.setInt(1, eid);
                    pst.setString(2, ename);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(f, "Inserted Successfully");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(f, "Employee ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Insert Error: " + ex.getMessage());
                } finally {
                    try { if (pst != null) pst.close(); } catch (Exception ignore) {}
                }
            }
        });

        selectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eidStr = t1.getText().trim();
                if (eidStr.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Please enter Employee ID.");
                    return;
                }
                PreparedStatement pst = null;
                ResultSet rs = null;
                try {
                    int eid = Integer.parseInt(eidStr);
                    pst = con.prepareStatement("SELECT ename FROM emp WHERE eid = ?");
                    pst.setInt(1, eid);
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        t2.setText(rs.getString("ename"));
                    } else {
                        JOptionPane.showMessageDialog(f, "Record Not Found");
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(f, "Employee ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Select Error: " + ex.getMessage());
                } finally {
                    try { if (rs != null) rs.close(); } catch (Exception ignore) {}
                    try { if (pst != null) pst.close(); } catch (Exception ignore) {}
                }
            }
        });

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eidStr = t1.getText().trim();
                String ename = t2.getText().trim();
                if (eidStr.isEmpty() || ename.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Please enter both Employee ID and Name.");
                    return;
                }
                PreparedStatement pst = null;
                try {
                    int eid = Integer.parseInt(eidStr);
                    pst = con.prepareStatement("UPDATE emp SET ename = ? WHERE eid = ?");
                    pst.setString(1, ename);
                    pst.setInt(2, eid);
                    int rows = pst.executeUpdate();
                    JOptionPane.showMessageDialog(f, rows > 0 ? "Updated Successfully" : "Record Not Found");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(f, "Employee ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Update Error: " + ex.getMessage());
                } finally {
                    try { if (pst != null) pst.close(); } catch (Exception ignore) {}
                }
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eidStr = t1.getText().trim();
                if (eidStr.isEmpty()) {
                    JOptionPane.showMessageDialog(f, "Please enter Employee ID.");
                    return;
                }
                PreparedStatement pst = null;
                try {
                    int eid = Integer.parseInt(eidStr);
                    pst = con.prepareStatement("DELETE FROM emp WHERE eid = ?");
                    pst.setInt(1, eid);
                    int rows = pst.executeUpdate();
                    JOptionPane.showMessageDialog(f, rows > 0 ? "Deleted Successfully" : "Record Not Found");
                    if (rows > 0) t2.setText("");
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(f, "Employee ID must be a number.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Delete Error: " + ex.getMessage());
                } finally {
                    try { if (pst != null) pst.close(); } catch (Exception ignore) {}
                }
            }
        });

        // DB Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Database Connection Failed: " + ex.getMessage());
        }

        f.setSize(400, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new EmployeeManagerFrame();
    }
} 

