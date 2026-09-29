package com.quizora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QuizFrame extends JFrame {

    private final String studentName;
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    private JLabel questionNumberLabel;
    private JTextArea questionArea;
    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;
    private ButtonGroup group;
    private JButton nextButton;

    public QuizFrame(String studentName) {
        this.studentName = studentName;
        loadQuestions();

        setTitle("Quizora Quiz");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 247, 255));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(240, 247, 255));
        questionNumberLabel = new JLabel("Question 1");
        questionNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        questionNumberLabel.setForeground(new Color(0, 102, 204));
        header.add(questionNumberLabel);

        mainPanel.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(255, 255, 255));
        content.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        questionArea.setBackground(new Color(255, 255, 255));
        JScrollPane scroll = new JScrollPane(questionArea);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBackground(new Color(255, 255, 255));

        optionA = new JRadioButton("A");
        optionB = new JRadioButton("B");
        optionC = new JRadioButton("C");
        optionD = new JRadioButton("D");

        Font optionFont = new Font("SansSerif", Font.PLAIN, 18);
        optionA.setFont(optionFont);
        optionB.setFont(optionFont);
        optionC.setFont(optionFont);
        optionD.setFont(optionFont);

        group = new ButtonGroup();
        group.add(optionA);
        group.add(optionB);
        group.add(optionC);
        group.add(optionD);

        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        content.add(scroll, BorderLayout.NORTH);
        content.add(optionsPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(0, 123, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setPreferredSize(new Dimension(150, 45));
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 16));

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitAnswerAndMove();
            }
        });

        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(nextButton, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        showCurrentQuestion();
    }

    private void loadQuestions() {
        String sql = "SELECT * FROM questions ORDER BY RAND() LIMIT 10";
        questions = new ArrayList<>();

        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOptionA(rs.getString("option_a"));
                q.setOptionB(rs.getString("option_b"));
                q.setOptionC(rs.getString("option_c"));
                q.setOptionD(rs.getString("option_d"));
                q.setCorrectOption(rs.getString("correct_option"));
                q.setCategory(rs.getString("category"));
                questions.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }

        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions found in the database.");
            new App().setVisible(true);
            dispose();
        }
    }

    private void showCurrentQuestion() {
        if (currentIndex >= questions.size()) {
            finishQuiz();
            return;
        }

        Question q = questions.get(currentIndex);

        questionNumberLabel.setText("Question " + (currentIndex + 1));
        questionArea.setText(q.getQuestionText());

        optionA.setText("A. " + q.getOptionA());
        optionB.setText("B. " + q.getOptionB());
        optionC.setText("C. " + q.getOptionC());
        optionD.setText("D. " + q.getOptionD());

        group.clearSelection();

        if (currentIndex == questions.size() - 1) {
            nextButton.setText("Finish");
        } else {
            nextButton.setText("Next");
        }
    }

    private void submitAnswerAndMove() {
        if (!optionA.isSelected() && !optionB.isSelected() && !optionC.isSelected() && !optionD.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select an answer.");
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        String selectedAnswer = "";

        if (optionA.isSelected()) selectedAnswer = "A";
        if (optionB.isSelected()) selectedAnswer = "B";
        if (optionC.isSelected()) selectedAnswer = "C";
        if (optionD.isSelected()) selectedAnswer = "D";

        if (selectedAnswer.equalsIgnoreCase(currentQuestion.getCorrectOption())) {
            score++;
        }

        currentIndex++;

        if (currentIndex < questions.size()) {
            showCurrentQuestion();
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        int totalQuestions = questions.size();
        double percentage = (score * 100.0) / totalQuestions;

        saveResult(studentName, score, totalQuestions, percentage);

        new ResultFrame(studentName, score, totalQuestions, percentage).setVisible(true);
        dispose();
    }

    private void saveResult(String studentName, int score, int totalQuestions, double percentage) {
        String sql = "INSERT INTO quiz_results (student_name, score, total_questions, percentage) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDatabaseConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentName);
            ps.setInt(2, score);
            ps.setInt(3, totalQuestions);
            ps.setDouble(4, percentage);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving result: " + e.getMessage());
        }
    }
}
