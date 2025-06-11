/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - AP Student Class
 * 06/11/2025
 *
 * The APStudent class extends RegularStudent to represent an AP student in the student management
 * system, handling weighted GPAs and AP course points. It supports advanced academic tracking
 * with methods for calculating weighted GPAs and adding AP points.
 */

import java.util.ArrayList;

/**
 * Represents an AP student, extending RegularStudent with AP-specific AP features.
 */
public class APStudent extends RegularStudent {
    private int apPoints;

    public APStudent(String id, String name, int gradeLevel) {
        super(id, name, gradeLevel);
        this.apPoints = 0;
    }

    /**
     * Calculates the weighted GPA for AP courses (placeholder).
     */
    @Override
    public void calculateGPA() {
        // The main calculation is now handled by the overloaded version.
    }

    /**
     * Calculates the student's weighted GPA based on a list of all grade records.
     * @param allGradeRecords A list of all grade records.
     */
    @Override
    public void calculateGPA(ArrayList<GradeRecord> allGradeRecords) {
        double totalPoints = 0;
        int numGrades = 0;
        for (GradeRecord record : allGradeRecords) {
            if (record.getStudentID().equals(this.getId())) {
                totalPoints += record.getGrade();
                numGrades++;
            }
        }

        if (numGrades > 0) {
            // Weighted GPA for AP students is on a 5.0 scale
            setGpa((totalPoints / numGrades) * 5.0 / 100.0);
        } else {
            setGpa(0.0);
        }
    }

    /**
     * Adds AP points for completed AP courses.
     * @param points the points to add
     */
    public void addAPPoints(int points) {
        if (points >= 0) {
            this.apPoints += points;
        }
    }

    /**
     * Gets the student's accumulated AP points.
     * @return The total AP points.
     */
    public int getAPPoints() {
        return this.apPoints;
    }
}