import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class AddFrame extends JFrame {
    Container c;
    JLabel lblHeading, labRno, labName, labSub1, labSub2, labSub3;
    JTextField txtRno, txtName, txtSub1, txtSub2, txtSub3;
    JButton btnSave, btnBack;

    AddFrame(MainFrame mainFrame) {
        c = getContentPane();
        c.setLayout(null);

        Font headingFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 40);
        Font labelFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);
        Font textFont = new Font("Calibri", Font.PLAIN, 30);
        Font buttonFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);


        lblHeading = new JLabel("Add Student", SwingConstants.CENTER);
        lblHeading.setFont(headingFont);
        lblHeading.setBounds(0, 20, 600, 60); // Position and size
        lblHeading.setOpaque(true);
        lblHeading.setBackground(Color.LIGHT_GRAY); // Optional background color


        labRno = new JLabel("Enter Rno: ");
        labRno.setFont(labelFont);
        txtRno = new JTextField(12);
        txtRno.setFont(textFont);

        labName = new JLabel("Enter Name: ");
        labName.setFont(labelFont);
        txtName = new JTextField(12);
        txtName.setFont(textFont);

        labSub1 = new JLabel("Enter Sub1 Marks: ");
        labSub1.setFont(labelFont);
        txtSub1 = new JTextField(12);
        txtSub1.setFont(textFont);

        labSub2 = new JLabel("Enter Sub2 Marks: ");
        labSub2.setFont(labelFont);
        txtSub2 = new JTextField(12);
        txtSub2.setFont(textFont);

        labSub3 = new JLabel("Enter Sub3 Marks: ");
        labSub3.setFont(labelFont);
        txtSub3 = new JTextField(12);
        txtSub3.setFont(textFont);

        btnSave = new JButton("Save Student");
        btnSave.setFont(buttonFont);

        btnBack = new JButton("Back To Main");
        btnBack.setFont(buttonFont);


        lblHeading.setBounds(0, 20, 600, 60);

        labRno.setBounds(50, 100, 200, 40);
        txtRno.setBounds(250, 100, 300, 40);

        labName.setBounds(50, 160, 200, 40);
        txtName.setBounds(250, 160, 300, 40);

        labSub1.setBounds(50, 220, 200, 40);
        txtSub1.setBounds(250, 220, 300, 40);

        labSub2.setBounds(50, 280, 200, 40);
        txtSub2.setBounds(250, 280, 300, 40);

        labSub3.setBounds(50, 340, 200, 40);
        txtSub3.setBounds(250, 340, 300, 40);

        btnSave.setBounds(50, 400, 200, 50);
        btnBack.setBounds(300, 400, 250, 50);


        c.add(lblHeading);
        c.add(labRno);
        c.add(txtRno);
        c.add(labName);
        c.add(txtName);
        c.add(labSub1);
        c.add(txtSub1);
        c.add(labSub2);
        c.add(txtSub2);
        c.add(labSub3);
        c.add(txtSub3);
        c.add(btnSave);
        c.add(btnBack);


        btnBack.addActionListener(ae -> {
            mainFrame.setVisible(true);
            dispose();
        });

        btnSave.addActionListener(ae -> {
            String rno = txtRno.getText();
            String name = txtName.getText();
            String sub1 = txtSub1.getText();
            String sub2 = txtSub2.getText();
            String sub3 = txtSub3.getText();

            if (rno.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(c, "Rno and Name cannot be empty");
                return;
            }

            if (!rno.matches("\\d+")) {
                JOptionPane.showMessageDialog(c, "Rno should be an integer");
                return;
            }

            if (!name.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(c, "Name should be alphabets only");
                return;
            }

            boolean validMarks = true;
            int sub1Marks = 0, sub2Marks = 0, sub3Marks = 0;

            if (!sub1.isEmpty()) {
                if (!sub1.matches("\\d+")) {
                    JOptionPane.showMessageDialog(c, "Sub1 Marks should be an integer");
                    validMarks = false;
                } else {
                    sub1Marks = Integer.parseInt(sub1);
                    if (sub1Marks < 0 || sub1Marks > 100) {
                        JOptionPane.showMessageDialog(c, "Sub1 Marks should be between 0 and 100");
                        validMarks = false;
                    }
                }
            }

            if (!sub2.isEmpty()) {
                if (!sub2.matches("\\d+")) {
                    JOptionPane.showMessageDialog(c, "Sub2 Marks should be an integer");
                    validMarks = false;
                } else {
                    sub2Marks = Integer.parseInt(sub2);
                    if (sub2Marks < 0 || sub2Marks > 100) {
                        JOptionPane.showMessageDialog(c, "Sub2 Marks should be between 0 and 100");
                        validMarks = false;
                    }
                }
            }

            if (!sub3.isEmpty()) {
                if (!sub3.matches("\\d+")) {
                    JOptionPane.showMessageDialog(c, "Sub3 Marks should be an integer");
                    validMarks = false;
                } else {
                    sub3Marks = Integer.parseInt(sub3);
                    if (sub3Marks < 0 || sub3Marks > 100) {
                        JOptionPane.showMessageDialog(c, "Sub3 Marks should be between 0 and 100");
                        validMarks = false;
                    }
                }
            }

            if (!validMarks) return;

            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                String url = "jdbc:mysql://localhost:3306/javatask3";
                Connection con = DriverManager.getConnection(url, "root", "abc123");
                String sql = "insert into student values(?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(rno));
                pst.setString(2, name);
                pst.setInt(3, sub1Marks);
                pst.setInt(4, sub2Marks);
                pst.setInt(5, sub3Marks);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(c, "Saved successfully");
                txtRno.setText("");
                txtName.setText("");
                txtSub1.setText("");
                txtSub2.setText("");
                txtSub3.setText("");
                txtRno.requestFocus();
                con.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(c, "Issue: " + e);
            }
        });

        setTitle("Student Management System");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
