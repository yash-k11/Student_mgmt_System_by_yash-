import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame {
    Container c;
    JButton btnAdd, btnView, btnUpdate, btnDelete;
    JLabel lblHeading;

    MainFrame() {
        c = getContentPane();
        c.setLayout(null);
        Font headingFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 40);
        Font buttonFont = new Font("Calibri", Font.BOLD | Font.ITALIC, 30);


        lblHeading = new JLabel("Student Management System", SwingConstants.CENTER);
        lblHeading.setFont(headingFont);
        lblHeading.setBounds(0, 20, 600, 60); // Position and size
        lblHeading.setOpaque(true);
        lblHeading.setBackground(Color.LIGHT_GRAY); // Optional background color


        btnAdd = new JButton("Add Student");
        btnView = new JButton("View Student");
        btnUpdate = new JButton("Update Student");
        btnDelete = new JButton("Delete Student");

        btnAdd.setFont(buttonFont);
        btnView.setFont(buttonFont);
        btnUpdate.setFont(buttonFont);
        btnDelete.setFont(buttonFont);
        btnAdd.setBounds(150, 100, 300, 50);
        btnView.setBounds(150, 170, 300, 50);
        btnUpdate.setBounds(150, 240, 300, 50);
        btnDelete.setBounds(150, 310, 300, 50);


        c.add(lblHeading);
        c.add(btnAdd);
        c.add(btnView);
        c.add(btnUpdate);
        c.add(btnDelete);


        btnAdd.addActionListener(ae -> {
            AddFrame addFrame = new AddFrame(this);
        });

        btnView.addActionListener(ae -> {
            ViewFrame viewFrame = new ViewFrame(this);
        });

        btnUpdate.addActionListener(ae -> {
            UpdateFrame updateFrame = new UpdateFrame(this);
        });

        btnDelete.addActionListener(ae -> {
            DeleteFrame deleteFrame = new DeleteFrame(this);
        });

        setTitle("Student Management System");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
