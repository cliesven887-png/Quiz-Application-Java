package com.quizora;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AdminDashboardFrame extends JFrame {

    private JTextArea questionArea;
    private JTextField optionAField;
    private JTextField optionBField;
    private JTextField optionCField;
    private JTextField optionDField;
    private JComboBox<String> correctOptionCombo;
    private JTextField categoryField;
    private JList<String> questionList;

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard - Quizora");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 248, 255));
        panel.setLayout(null);

        JLabel title = new JLabel("Quiz Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(new Color(0, 102, 204));
        title.setBounds(350, 20, 300, 40);

        JLabel qLabel = new JLabel("Question:");
        qLabel.setBounds(40, 90, 120, 25);

        questionArea = new JTextArea();
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 220)));
        JScrollPane qScroll = new JScrollPane(questionArea);
        qScroll.setBounds(120, 90, 420, 90);

        JLabel aLabel = new JLabel("A:");
        aLabel.setBounds(40, 200, 30, 25);
        optionAField = new JTextField();
        optionAField.setBounds(120, 200, 420, 30);

        JLabel bLabel = new JLabel("B:");
        bLabel.setBounds(40, 245, 30, 25);
        optionBField = new JTextField();
        optionBField.setBounds(120, 245, 420, 30);

        JLabel cLabel = new JLabel("C:");
        cLabel.setBounds(40, 290, 30, 25);
        optionCField = new JTextField();
        optionCField.setBounds(120, 290, 420, 30);

        JLabel dLabel = new JLabel("D:");
        dLabel.setBounds(40, 335, 30, 25);
        optionDField = new JTextField();
        optionDField.setBounds(120, 335, 420, 30);

        JLabel correctLabel = new JLabel("Correct:");
        correctLabel.setBounds(40, 380, 70, 25);
        correctOptionCombo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        correctOptionCombo.setBounds(120, 380, 120, 30);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(260, 380, 80, 25);
        categoryField = new JTextField("General");
        categoryField.setBounds(340, 380, 200, 30);

        JButton addBtn = new JButton("Add Question");
        addBtn.setBounds(120, 430, 180, 40);
        addBtn.setBackground(new Color(0, 123, 255));
        addBtn.setForeground(Color.WHITE);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBounds(320, 430, 180, 40);
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(120, 490, 380, 35);
        logoutBtn.setBackground(new Color(200, 220, 255));
        logoutBtn.setForeground(new Color(0, 60, 120));

        JLabel listLabel = new JLabel("Existing Questions");
        listLabel.setBounds(620, 80, 200, 25);
        listLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        DefaultListModel<String> model = new DefaultListModel<>();
        questionList = new JList<>(model);
        questionList.setBackground(new Color(255, 255, 255));
        questionList.setBorder(BorderFactory.createLineBorder(new Color(150, 170, 220)));

        JScrollPane listScroll = new JScrollPane(questionList);
        listScroll.setBounds(620, 110, 300, 430);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedQuestion();
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new App().setVisible(true);
                dispose();
            }
        });

        panel.add(title);
        panel.add(qLabel);
        panel.add(qScroll);
        panel.add(aLabel);
        panel.add(optionAField);
        panel.add(bLabel);
        panel.add(optionBField);
        panel.add(cLabel);
        panel.add(optionCField);
        panel.add(dLabel);
        panel.add(optionDField);
        panel.add(correctLabel);
        panel.add(correctOptionCombo);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(addBtn);
        panel.add(deleteBtn);
        panel.add(logoutBtn);
        panel.add(listLabel);
        panel.add(listScroll);

        setContentPane(panel);
        loadQuestions();
    }

    private void addQuestion() {
        String questionText = questionArea.getText().trim();
        String optionA = optionAField.getText().trim();
        String optionB = optionBField.getText().trim();
        String optionC = optionCField.getText().trim();
        String optionD = optionDField.getText().trim();
        String correctOption = (String) correctOptionCombo.getSelectedItem();
        String category = categoryField.getText().trim();

        if (questionText.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.");
            return;
        }

        String sql = "INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, questionText);
            ps.setString(2, optionA);
            ps.setString(3, optionB);
            ps.setString(4, optionC);
            ps.setString(5, optionD);
            ps.setString(6, correctOption);
            ps.setString(7, category.isEmpty() ? "General" : category);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Question added successfully.");
            clearForm();
            loadQuestions();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding question: " + e.getMessage());
        }
    }

    private void deleteSelectedQuestion() {
        String selected = questionList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a question to delete.");
            return;
        }

        int id = Integer.parseInt(selected.split(" - ")[0]);
        String sql = "DELETE FROM questions WHERE id = ?";

        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Question deleted successfully.");
            loadQuestions();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting question: " + e.getMessage());
        }
    }

    private void clearForm() {
        questionArea.setText("");
        optionAField.setText("");
        optionBField.setText("");
        optionCField.setText("");
        optionDField.setText("");
        correctOptionCombo.setSelectedIndex(0);
        categoryField.setText("General");
    }

    private void loadQuestions() {
        DefaultListModel<String> model = (DefaultListModel<String>) questionList.getModel();
        model.clear();

        String sql = "SELECT id, question_text FROM questions ORDER BY id DESC";
        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String text = rs.getString("question_text");
                model.addElement(id + " - " + text);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
