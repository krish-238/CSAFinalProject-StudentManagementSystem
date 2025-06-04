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
        boolean alreadyEnrolled = false;
        for (int i = 0; i < this.students.size(); i++) {
            if (this.students.get(i).getId().equals(student.getId())) {
                alreadyEnrolled = true;
            }
        }
        if (!alreadyEnrolled) {
            this.students.add(student);
        }
    }

    /**
     * Removes a student from the class roster.
     * @param student the student to remove
     */
    public void removeStudent(RegularStudent student) {
        for (int i = 0; i < this.students.size(); i++) {
            if (this.students.get(i).getId().equals(student.getId())) {
                this.students.remove(i);
                break;
            }
        }
    }

    /**
     * Returns class information as a string.
     * @return string containing class details
     */
    public String getClassInfo() {
        String info = this.className + " (" + this.classCode + "), Teacher: " + this.teacher + ", Period: " + this.period;
        return info;
    }

    // Getters
    public String getClassCode() {
        return this.classCode;
    }
}