/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - AP Student Class
 * 06/03/2025
 *
 * The APStudent class extends RegularStudent to represent an AP student in the student management
 * system, handling weighted GPAs and AP course points. It supports advanced academic tracking
 * with methods for calculating weighted GPAs and adding AP points.
 */

/**
 * Represents an AP student, extending RegularStudent with AP-specific features.
 */
public class APStudent extends RegularStudent {
    private int apPoints;
    private double gpa; // Supports weighted GPA up to 5.0

    public APStudent(String id, String name, int gradeLevel) {
        super(id, name, gradeLevel);
        this.apPoints = 0;
        this.gpa = 0.0;
    }

    /**
     * Calculates the weighted GPA for AP courses (placeholder, updated via SystemManager).
     */
    public void calculateGPA() {
        this.gpa = 0.0; // Updated via SystemManager
    }

    /**
     * Adds AP points for completed AP courses.
     * @param points the points to add
     */
    public void addAPPoints(int points) {
        this.apPoints = this.apPoints + points;
    }

    // Getters and setters
    public int getApPoints() {
        return this.apPoints;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public double getGpa() {
        return this.gpa;
    }
}