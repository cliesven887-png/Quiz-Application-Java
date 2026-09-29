package com.quizora;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class App extends JFrame {

    public App() {
        DBConnection.initializeDatabase();

        setTitle("Quizora");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 247, 255));
        panel.setLayout(null);

        JLabel title = new JLabel("Quizora");
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setForeground(new Color(0, 102, 204));
        title.setBounds(260, 50, 180, 40);

        JLabel subtitle = new JLabel("Test your knowledge. Trust your results.");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));
        subtitle.setForeground(new Color(60, 60, 60));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setBounds(120, 95, 460, 30);

        JButton adminBtn = new JButton("Admin Login");
        adminBtn.setBackground(new Color(0, 123, 255));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFocusPainted(false);
        adminBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        adminBtn.setBounds(220, 180, 240, 50);

        JButton studentBtn = new JButton("Student Login");
        studentBtn.setBackground(new Color(0, 153, 255));
        studentBtn.setForeground(Color.WHITE);
        studentBtn.setFocusPainted(false);
        studentBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        studentBtn.setBounds(220, 260, 240, 50);

        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginFrame().setVisible(true);
                dispose();
            }
        });

        studentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentLoginFrame().setVisible(true);
                dispose();
            }
        });

        panel.add(title);
        panel.add(subtitle);
        panel.add(adminBtn);
        panel.add(studentBtn);

        setContentPane(panel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new App().setVisible(true);
    }
}
