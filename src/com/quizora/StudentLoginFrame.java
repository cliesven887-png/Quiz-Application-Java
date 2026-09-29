package com.quizora;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentLoginFrame extends JFrame {

    private JTextField studentNameField;

    public StudentLoginFrame() {
        setTitle("Student Login - Quizora");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 249, 255));
        panel.setLayout(null);

        JLabel heading = new JLabel("Student Login");
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBounds(150, 40, 220, 40);

        JLabel nameLabel = new JLabel("Your Name:");
        nameLabel.setBounds(80, 120, 120, 25);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));

        studentNameField = new JTextField();
        studentNameField.setBounds(180, 117, 220, 30);

        JButton startBtn = new JButton("Start Quiz");
        startBtn.setBounds(180, 180, 220, 40);
        startBtn.setBackground(new Color(0, 123, 255));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(180, 235, 220, 35);
        backBtn.setBackground(new Color(200, 220, 255));
        backBtn.setForeground(new Color(0, 60, 120));
        backBtn.setFocusPainted(false);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(StudentLoginFrame.this, "Please enter your name.");
                    return;
                }

                new QuizFrame(name).setVisible(true);
                dispose();
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
        panel.add(nameLabel);
        panel.add(studentNameField);
        panel.add(startBtn);
        panel.add(backBtn);

        setContentPane(panel);
    }
}
