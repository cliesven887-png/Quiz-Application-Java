CREATE DATABASE IF NOT EXISTS quizora_db;
USE quizora_db;

CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'student') NOT NULL DEFAULT 'student'
);

CREATE TABLE IF NOT EXISTS questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_text TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_option CHAR(1) NOT NULL,
    category VARCHAR(100) DEFAULT 'General'
);

CREATE TABLE IF NOT EXISTS quiz_results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    total_questions INT NOT NULL,
    percentage DOUBLE NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO admins (username, password)
SELECT 'admin', 'admin123'
WHERE NOT EXISTS (
    SELECT 1 FROM admins WHERE username = 'admin'
);

INSERT INTO users (username, password, role)
SELECT 'admin', 'admin123', 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'admin'
);

INSERT INTO users (username, password, role)
SELECT 'student1', 'student123', 'student'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'student1'
);

INSERT INTO users (username, password, role)
SELECT 'student2', 'student123', 'student'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'student2'
);

INSERT INTO users (username, password, role)
SELECT 'student3', 'student123', 'student'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'student3'
);

INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_option, category)
SELECT * FROM (
    SELECT 'What is the capital city of the Philippines?', 'Manila', 'Cebu', 'Davao', 'Baguio', 'A', 'Geography'
    UNION ALL SELECT 'Which language is used to style web pages?', 'HTML', 'Java', 'SQL', 'C++', 'A', 'Technology'
    UNION ALL SELECT 'What is 12 × 8?', '96', '84', '92', '104', 'A', 'Mathematics'
    UNION ALL SELECT 'Which planet is known as the Red Planet?', 'Earth', 'Mars', 'Venus', 'Jupiter', 'B', 'Science'
    UNION ALL SELECT 'Which of these is a Java keyword for class inheritance?', 'extends', 'implements', 'static', 'new', 'A', 'Programming'
) AS temp
WHERE NOT EXISTS (SELECT 1 FROM questions);
