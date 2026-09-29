package com.quizora;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_NAME = "quizora_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", USER, PASS);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            try (Connection dbConn = getDatabaseConnection();
                 Statement createStmt = dbConn.createStatement()) {

                String adminsTable = "CREATE TABLE IF NOT EXISTS admins ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "username VARCHAR(50) NOT NULL UNIQUE,"
                        + "password VARCHAR(255) NOT NULL"
                        + ")";

                String questionsTable = "CREATE TABLE IF NOT EXISTS questions ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "question_text TEXT NOT NULL,"
                        + "option_a VARCHAR(255) NOT NULL,"
                        + "option_b VARCHAR(255) NOT NULL,"
                        + "option_c VARCHAR(255) NOT NULL,"
                        + "option_d VARCHAR(255) NOT NULL,"
                        + "correct_option CHAR(1) NOT NULL,"
                        + "category VARCHAR(100) DEFAULT 'General'"
                        + ")";

                String resultsTable = "CREATE TABLE IF NOT EXISTS quiz_results ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "student_name VARCHAR(100) NOT NULL,"
                        + "score INT NOT NULL,"
                        + "total_questions INT NOT NULL,"
                        + "percentage DOUBLE NOT NULL,"
                        + "submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                        + ")";

                createStmt.executeUpdate(adminsTable);
                createStmt.executeUpdate(questionsTable);
                createStmt.executeUpdate(resultsTable);

                String checkAdmin = "SELECT COUNT(*) FROM admins WHERE username = 'admin'";
                try (PreparedStatement ps = dbConn.prepareStatement(checkAdmin);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        String insertAdmin = "INSERT INTO admins (username, password) VALUES (?, ?)";
                        try (PreparedStatement insertPs = dbConn.prepareStatement(insertAdmin)) {
                            insertPs.setString(1, "admin");
                            insertPs.setString(2, "admin123");
                            insertPs.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
