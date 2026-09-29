package com.quizora;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultFrame extends JFrame {

    public ResultFrame(String studentName, int score, int totalQuestions, double percentage) {
        setTitle("Quiz Result - Quizora");
        setSize(550, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 247, 255));
        panel.setLayout(null);

        JLabel title = new JLabel("Quiz Result");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(0, 102, 204));
        title.setBounds(180, 30, 200, 40);

        JLabel nameLabel = new JLabel("Student: " + studentName);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        nameLabel.setBounds(80, 100, 400, 30);

        JLabel scoreLabel = new JLabel("Score: " + score + " / " + totalQuestions);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        scoreLabel.setBounds(80, 150, 400, 30);

        JLabel percentLabel = new JLabel("Percentage: " + String.format("%.2f", percentage) + "%");
        percentLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        percentLabel.setBounds(80, 200, 400, 30);

        String message = percentage >= 60 ? "Excellent! You passed." : "Keep practicing and try again.";
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        messageLabel.setForeground(new Color(0, 123, 255));
        messageLabel.setBounds(80, 250, 400, 30);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(180, 310, 160, 40);
        exitBtn.setBackground(new Color(0, 123, 255));
        exitBtn.setForeground(Color.WHITE);

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new App().setVisible(true);
                dispose();
            }
        });

        panel.add(title);
        panel.add(nameLabel);
        panel.add(scoreLabel);
        panel.add(percentLabel);
        panel.add(messageLabel);
        panel.add(exitBtn);

        setContentPane(panel);
    }
}
