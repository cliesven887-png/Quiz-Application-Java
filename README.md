# Quizora

Quizora is a Java Swing desktop quiz application built for NetBeans and designed to work with MySQL through XAMPP.

Tagline: "Test your knowledge. Trust your results."

Features:
- Admin login only for admin panel
- Student login using username and password only
- No registration system
- Only students can access the quiz
- 4 multiple-choice answers per question
- Score and percentage displayed after submission
- Minimal white and blue UI
- Runs with Apache Ant in NetBeans

Project structure:
- `src/` - Java source files
- `db/quizora_db.sql` - database schema
- `build.xml` - Ant build file
- `lib/` - place MySQL connector JAR here

Default accounts:
- Admin: `admin` / `admin123`
- Student: `student1` / `student123`
- Student: `student2` / `student123`
- Student: `student3` / `student123`

Quick start:
1. Start Apache and MySQL in XAMPP.
2. Download `mysql-connector-j-x.x.x.jar` from MySQL.
3. Copy the JAR into the `lib/` folder.
4. Open the project in NetBeans and run `build.xml` or use Ant.
5. Run the project from the `App` class.

Database setup:
- Import `db/quizora_db.sql` into phpMyAdmin or use the application setup function.

The app also creates the database and necessary tables automatically when started.
