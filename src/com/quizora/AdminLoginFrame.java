package com.quizora;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AdminLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginFrame() {
        setTitle("Admin Login - Quizora");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 249, 255));
        panel.setLayout(null);

        JLabel heading = new JLabel("Admin Login");
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBounds(150, 40, 220, 40);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(80, 120, 120, 25);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        usernameField = new JTextField();
        usernameField.setBounds(180, 117, 220, 30);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(80, 180, 120, 25);
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 177, 220, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(180, 250, 220, 40);
        loginBtn.setBackground(new Color(0, 123, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(180, 300, 220, 35);
        backBtn.setBackground(new Color(200, 220, 255));
        backBtn.setForeground(new Color(0, 60, 120));
        backBtn.setFocusPainted(false);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateAdmin();
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new App().setVisible(true);
                dispose();
            }
        });

        panel.add(heading);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(loginBtn);
        panel.add(backBtn);

        setContentPane(panel);
    }

    private void authenticateAdmin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.");
            return;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = 'admin'";

        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                new AdminDashboardFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin credentials.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }
}
