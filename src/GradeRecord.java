/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Grade Record Class
 * 06/11/2025
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
        this.grade = (grade >= 0.0 && grade <= 100.0) ? grade : 0.0;
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
        return this.grade >= 70.0;
    }

    /**
     * Gets the student ID associated with this grade record.
     * @return The student ID.
     */
    public String getStudentID() {
        return this.studentID;
    }

    /**
     * Gets the class code associated with this grade record.
     * @return The class code.
     */
    public String getClassCode() {
        return this.classCode;
    }
}
