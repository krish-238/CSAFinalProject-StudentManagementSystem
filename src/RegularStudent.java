/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Regular Student Class
 * 05/31/2025
 *
 * The RegularStudent class represents a standard high school student in the student management
 * system, managing their enrollment, grades, and GPA. It provides methods to enroll in classes,
 * calculate GPA, and retrieve schedules, serving as the base for student functionality.
 */

import java.util.ArrayList;

/**
 * Represents a regular high school student, inheriting from SchoolEntity.
 */
public class RegularStudent extends SchoolEntity {
    private int gradeLevel;
    private double gpa;
    private ArrayList<Class> classes;

    public RegularStudent(String id, String name, int gradeLevel) {
        super(id, name);
        this.gradeLevel = gradeLevel;
        this.gpa = 0.0;
        this.classes = new ArrayList<Class>();
    }

    /**
     * Enrolls the student in a class, adding it to their schedule.
     * @param course the class to enroll in
     */
    public void enrollClass(Class course) {
        if (course != null && !classes.stream().anyMatch(c -> c.getClassCode().equals(course.getClassCode()))) {
            classes.add(course);
            course.addStudent(this);
        }
    }

    /**
     * Calculates the student's GPA based on grades (placeholder, updated via SystemManager).
     */
    public void calculateGPA() {
        // The main calculation is now handled by the overloaded version
        // to ensure the gpa is always calculated with the full grade history.
    }

    /**
     * Calculates the student's GPA based on a list of all grade records in the system.
     * @param allGradeRecords A list of all grade records.
     */
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
            // Standard GPA is on a 4.0 scale
            this.gpa = (totalPoints / numGrades) * 4.0 / 100.0;
        } else {
            this.gpa = 0.0;
        }
    }


    /**
     * Returns the student's class schedule as a string.
     * @return string listing enrolled classes
     */
    public String getSchedule() {
        String schedule = "Schedule for " + this.getName() + ":\n";
        for (int i = 0; i < this.classes.size(); i++) {
            schedule += this.classes.get(i).getClassInfo() + "\n";
        }
        return schedule;
    }

    // Getters and setters
    public int getGradeLevel() {
        return this.gradeLevel;
    }

    public double getGPA() {
        // BUG FIX: Was 'return this.getGPA()', causing a StackOverflowError.
        return this.gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public ArrayList<Class> getClasses() {
        return this.classes;
    }
}
