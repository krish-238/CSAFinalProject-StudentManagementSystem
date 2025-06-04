/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Grade Record Abstract Class
 * 06/03/2025
 *
 * The GradeRecord class implements the Grade interface to store a student’s grade for a specific
 * class in the student management system. It manages grade assignment, retrieval, and passing
 * status, supporting accurate grade tracking.
 */

public class GradeRecord implements Grade {
    private String studentID;
    private String classCode;
    private double grade;

    public GradeRecord(String studentID, String classCode, double grade) {
        this.studentID = studentID;
        this.classCode = classCode;
        this.grade = grade;
    }

    /**
     * Assigns a grade value to the record.
     * @param grade the grade to assign
     */
    public void assignGrade(double grade) {
        if (grade >= 0.0 && grade <= 100.0) {
            this.grade = grade;
        }
    }

    /**
     * Retrieves the current grade.
     * @return the grade value
     */
    public double getGrade() {
        return this.grade;
    }

    /**
     * Checks if the grade is passing (70 or higher).
     * @return true if passing, false otherwise
     */
    public boolean isPassing() {
        if (this.grade >= 70.0) {
            return true;
        } else {
            return false;
        }
    }

    // Getters
    public String getStudentID() {
        return this.studentID;
    }

    public String getClassCode() {
        return this.classCode;
    }
}