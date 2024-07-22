import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteFrame extends JFrame {
    private JLabel lblHeading, lblRno;
    private JTextField txtRno;
    private JButton btnDelete, btnBack;

    public DeleteFrame(MainFrame mainFrame) {
        Container c = getContentPane();
        c.setLayout(null);

        Font headingFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 40);
        Font fieldFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);
        Font buttonFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);


        lblHeading = new JLabel("Delete Data", SwingConstants.CENTER);
        lblHeading.setFont(headingFont);
        lblHeading.setBounds(0, 20, 600, 60);
        lblHeading.setOpaque(true);
        lblHeading.setBackground(Color.LIGHT_GRAY);


        lblRno = new JLabel("Enter Rno: ");
        lblRno.setFont(fieldFont);
        lblRno.setBounds(50, 100, 250, 40);
        txtRno = new JTextField(12);
        txtRno.setFont(fieldFont);
        txtRno.setBounds(300, 100, 250, 40);

        btnDelete = new JButton("Delete Student");
        btnDelete.setFont(buttonFont);
        btnDelete.setBounds(100, 180, 250, 50);

        btnBack = new JButton("Back");
        btnBack.setFont(buttonFont);
        btnBack.setBounds(400, 180, 150, 50);


        c.add(lblHeading);
        c.add(lblRno);
        c.add(txtRno);
        c.add(btnDelete);
        c.add(btnBack);


        btnBack.addActionListener(ae -> {
            mainFrame.setVisible(true);
            dispose();
        });


        btnDelete.addActionListener(ae -> {
            String rno = txtRno.getText();

            if (rno.isEmpty()) {
                JOptionPane.showMessageDialog(c, "Please fill the roll number");
                return;
            }

            try {
                int rollNo = Integer.parseInt(rno);

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                String url = "jdbc:mysql://localhost:3306/javatask3";
                Connection con = DriverManager.getConnection(url, "root", "abc123");
                PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE rno = ?");
                ps.setInt(1, rollNo);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(c, "Student deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(c, "No student found with this roll number");
                }

                txtRno.setText("");
                txtRno.requestFocus();
                con.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(c, "Invalid roll number");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(c, "Database error: " + ex.getMessage());
            }
        });

        setTitle("Student Management System");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
