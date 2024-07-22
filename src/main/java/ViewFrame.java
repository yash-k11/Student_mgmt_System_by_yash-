import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ViewFrame extends JFrame {
    private JLabel lblHeading;
    private JButton btnBack;
    private JTable table;
    private DefaultTableModel model;

    public ViewFrame(MainFrame mainFrame) {
        Container c = getContentPane();
        c.setLayout(null);

        Font headingFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 40);
        Font buttonFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);


        lblHeading = new JLabel("Student Data", SwingConstants.CENTER);
        lblHeading.setFont(headingFont);
        lblHeading.setBounds(0, 20, 800, 60);
        lblHeading.setOpaque(true);
        lblHeading.setBackground(Color.LIGHT_GRAY);


        model = new DefaultTableModel();
        model.addColumn("Rno");
        model.addColumn("Name");
        model.addColumn("Sub1");
        model.addColumn("Sub2");
        model.addColumn("Sub3");

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(50, 100, 700, 300);


        btnBack = new JButton("Back To Main");
        btnBack.setFont(buttonFont);
        btnBack.setBounds(250, 420, 300, 50);


        c.add(lblHeading);
        c.add(pane);
        c.add(btnBack);


        btnBack.addActionListener(ae -> {
            mainFrame.setVisible(true);
            dispose();
        });


        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/javatask3";
            Connection con = DriverManager.getConnection(url, "root", "abc123");
            String sql = "SELECT * FROM student";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("rno"),
                        rs.getString("name"),
                        rs.getInt("sub1marks"),
                        rs.getInt("sub2marks"),
                        rs.getInt("sub3marks")
                });
            }
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(c, "Database error: " + e.getMessage());
        }

        setTitle("Student Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
