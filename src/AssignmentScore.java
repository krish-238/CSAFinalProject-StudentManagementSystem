/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Assignment Score Class
 * 06/11/2025
 *
 * This class represents a single student's score on an assignment, designed for
 * the standalone grade curving utility. It holds the student's ID, original score,
 * and the calculated curved score.
 */

public class AssignmentScore {
    private String studentID;
    private double originalScore;
    private double curvedScore;

    /**
     * Constructs an AssignmentScore object.
     * @param studentID The unique ID of the student.
     * @param originalScore The student's original score on the assignment.
     */
    public AssignmentScore(String studentID, double originalScore) {
        this.studentID = studentID;
        this.originalScore = originalScore;
        this.curvedScore = originalScore;
    }

    /**
     * Gets the student's ID.
     * @return The student ID.
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Gets the student's original score.
     * @return The original score.
     */
    public double getOriginalScore() {
        return originalScore;
    }

    /**
     * Gets the student's curved score.
     * @return The curved score.
     */
    public double getCurvedScore() {
        return curvedScore;
    }

    /**
     * Sets the curved score, ensuring it stays within the 0-100 range.
     * @param curvedScore The new curved score.
     */
    public void setCurvedScore(double curvedScore) {
        if (curvedScore > 100.0) {
            this.curvedScore = 100.0;
        } else if (curvedScore < 0.0) {
            this.curvedScore = 0.0;
        } else {
            this.curvedScore = curvedScore;
        }
    }
}
