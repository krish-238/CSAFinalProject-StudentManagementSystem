/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Class/Course Class
 * 05/30/2025
 *
 * The Class class represents a high school course in the student management system, storing course
 * details and managing student rosters. It provides methods to add or remove students and retrieve
 * course information, facilitating enrollment tracking.
 */

import java.util.ArrayList;

public class Class {
    private String classCode;
    private String className;
    private String teacher;
    private int period;
    private ArrayList<RegularStudent> students;

    public Class(String classCode, String className, String teacher, int period) {
        if (classCode == null || classCode.isEmpty() || className == null || className.isEmpty() ||
                teacher == null || teacher.isEmpty() || period < 1) {
            throw new IllegalArgumentException("Invalid class parameters");
        }
        this.classCode = classCode;
        this.className = className;
        this.teacher = teacher;
        this.period = period;
        this.students = new ArrayList<RegularStudent>();
    }

    /**
     * Adds a student to the class roster.
     * @param student the student to add
     */
    public void addStudent(RegularStudent student) {
        // Using a stream for a concise check to see if the student is already enrolled.
        boolean alreadyEnrolled = this.students.stream().anyMatch(s -> s.getId().equals(student.getId()));
        if (!alreadyEnrolled) {
            this.students.add(student);
        }
    }

    /**
     * Removes a student from the class roster.
     * @param student the student to remove
     */
    public void removeStudent(RegularStudent student) {
        students.removeIf(s -> s.getId().equals(student.getId()));
    }

    /**
     * Returns class information as a string.
     * @return string containing class details
     */
    public String getClassInfo() {
        return this.className + " (" + this.classCode + "), Teacher: " + this.getTeacher() + ", Period: " + this.period;
    }

    // Getters
    public String getClassCode() {
        return this.classCode;
    }

    public String getClassName() {
        return this.className;
    }

    public String getTeacher() {
        // BUG FIX: Added missing getter for the teacher field.
        return this.teacher;
    }

    public int getPeriod() {
        return this.period;
    }

    public ArrayList<RegularStudent> getStudents() {
        return this.students;
    }
}
