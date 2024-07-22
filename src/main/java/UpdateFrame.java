import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateFrame extends JFrame {
    private JLabel lblHeading, lblRno, lblName, lblSub1, lblSub2, lblSub3;
    private JTextField txtRno, txtName, txtSub1, txtSub2, txtSub3;
    private JButton btnUpdate, btnBack;

    public UpdateFrame(MainFrame mainFrame) {
        Container c = getContentPane();
        c.setLayout(null);

        Font headingFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 40);
        Font fieldFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);
        Font buttonFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);


        lblHeading = new JLabel("Update Data", SwingConstants.CENTER);
        lblHeading.setFont(headingFont);
        lblHeading.setBounds(0, 20, 700, 60);
        lblHeading.setOpaque(true);
        lblHeading.setBackground(Color.LIGHT_GRAY); // Optional background color


        lblRno = new JLabel("Enter Roll No: ");
        lblRno.setFont(fieldFont);
        lblRno.setBounds(50, 100, 250, 40);
        txtRno = new JTextField(12);
        txtRno.setFont(fieldFont);
        txtRno.setBounds(300, 100, 250, 40);

        lblName = new JLabel("Enter Name: ");
        lblName.setFont(fieldFont);
        lblName.setBounds(50, 160, 250, 40);
        txtName = new JTextField(12);
        txtName.setFont(fieldFont);
        txtName.setBounds(300, 160, 250, 40);

        lblSub1 = new JLabel("Enter Subject 1 Marks: ");
        lblSub1.setFont(fieldFont);
        lblSub1.setBounds(50, 220, 250, 40);
        txtSub1 = new JTextField(12);
        txtSub1.setFont(fieldFont);
        txtSub1.setBounds(300, 220, 250, 40);

        lblSub2 = new JLabel("Enter Subject 2 Marks: ");
        lblSub2.setFont(fieldFont);
        lblSub2.setBounds(50, 280, 250, 40);
        txtSub2 = new JTextField(12);
        txtSub2.setFont(fieldFont);
        txtSub2.setBounds(300, 280, 250, 40);

        lblSub3 = new JLabel("Enter Subject 3 Marks: ");
        lblSub3.setFont(fieldFont);
        lblSub3.setBounds(50, 340, 250, 40);
        txtSub3 = new JTextField(12);
        txtSub3.setFont(fieldFont);
        txtSub3.setBounds(300, 340, 250, 40);

        btnUpdate = new JButton("Update Student");
        btnUpdate.setFont(buttonFont);
        btnUpdate.setBounds(100, 420, 250, 50);

        btnBack = new JButton("Back To Main");
        btnBack.setFont(buttonFont);
        btnBack.setBounds(400, 420, 250, 50);


        c.add(lblHeading);
        c.add(lblRno);
        c.add(txtRno);
        c.add(lblName);
        c.add(txtName);
        c.add(lblSub1);
        c.add(txtSub1);
        c.add(lblSub2);
        c.add(txtSub2);
        c.add(lblSub3);
        c.add(txtSub3);
        c.add(btnUpdate);
        c.add(btnBack);


        btnBack.addActionListener(ae -> {
            mainFrame.setVisible(true);
            dispose();
        });


        btnUpdate.addActionListener(ae -> {
            String rno = txtRno.getText();
            String name = txtName.getText();
            String sub1 = txtSub1.getText();
            String sub2 = txtSub2.getText();
            String sub3 = txtSub3.getText();

            if (rno.isEmpty()) {
                JOptionPane.showMessageDialog(c, "Roll No is compulsory");
                return;
            }

            if (!rno.matches("\\d+")) {
                JOptionPane.showMessageDialog(c, "Roll No should be an integer");
                return;
            }

            try {
                int rollNo = Integer.parseInt(rno);

                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                String url = "jdbc:mysql://localhost:3306/javatask3";
                Connection con = DriverManager.getConnection(url, "root", "abc123");

                PreparedStatement checkPs = con.prepareStatement("SELECT COUNT(*) FROM student WHERE rno = ?");
                checkPs.setInt(1, rollNo);
                ResultSet rs = checkPs.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(c, "No student found with this roll number");
                    con.close();
                    return;
                }

                StringBuilder sql = new StringBuilder("UPDATE student SET");
                boolean isFirst = true;

                if (!name.isEmpty()) {
                    sql.append(" name = ?");
                    isFirst = false;
                }
                if (!sub1.isEmpty()) {
                    if (!isFirst) sql.append(",");
                    sql.append(" sub1marks = ?");
                    isFirst = false;
                }
                if (!sub2.isEmpty()) {
                    if (!isFirst) sql.append(",");
                    sql.append(" sub2marks = ?");
                    isFirst = false;
                }
                if (!sub3.isEmpty()) {
                    if (!isFirst) sql.append(",");
                    sql.append(" sub3marks = ?");
                }
                sql.append(" WHERE rno = ?");

                PreparedStatement ps = con.prepareStatement(sql.toString());
                int index = 1;
                if (!name.isEmpty()) ps.setString(index++, name);
                if (!sub1.isEmpty()) ps.setInt(index++, Integer.parseInt(sub1));
                if (!sub2.isEmpty()) ps.setInt(index++, Integer.parseInt(sub2));
                if (!sub3.isEmpty()) ps.setInt(index++, Integer.parseInt(sub3));
                ps.setInt(index, rollNo);

                ps.executeUpdate();

                JOptionPane.showMessageDialog(c, "Student info updated successfully");
                txtRno.setText("");
                txtName.setText("");
                txtSub1.setText("");
                txtSub2.setText("");
                txtSub3.setText("");
                txtRno.requestFocus();
                con.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(c, "Invalid input for marks");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(c, "Database error: " + ex.getMessage());
            }
        });

        setTitle("Student Management System");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
