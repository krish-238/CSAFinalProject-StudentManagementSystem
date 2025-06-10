/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Grade Interface
 * 05/30/2025
 *
 * The Grade interface defines grade-related operations in the student management system,
 * requiring methods to assign, retrieve, and evaluate grades. It ensures consistent grade
 * handling for all classes managing student performance.
 */

/**
 * Interface defining grade-related operations.
 */
public interface Grade {
    /**
     * Assigns a grade value.
     * @param grade the grade to assign
     */
    void assignGrade(double grade);

    /**
     * Retrieves the current grade.
     * @return the grade value
     */
    double getGrade();

    /**
     * Checks if the grade is passing (70 or higher).
     * @return true if passing, false otherwise
     */
    boolean isPassing();
}