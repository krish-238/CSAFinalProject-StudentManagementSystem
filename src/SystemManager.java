/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - System Manager Class
 * 06/06/2025
 *
 * The SystemManager class is the central coordinator of the student management system,
 * managing students, classes, grades, and curving operations. It provides methods to add
 * students, assign grades, curve grades via AutoCurver, and generate reports, orchestrating
 * all system functionality.
 */

import java.util.ArrayList;

public class SystemManager {
    private ArrayList<RegularStudent> students;
    private ArrayList<Class> classes;
    private ArrayList<GradeRecord> gradeRecords;
    private int totalEnrollments;
    private AutoCurver curver;

    public SystemManager() {
        this.students = new ArrayList<>();
        this.classes = new ArrayList<>();
        this.gradeRecords = new ArrayList<>();
        this.totalEnrollments = 0;
        this.curver = null;
    }

    /**
     * Adds a student to the system.
     * @param student the student to add
     */
    public void addStudent(RegularStudent student) {
        if (student != null && students.stream().noneMatch(s -> s.getId().equals(student.getId()))) {
            this.students.add(student);
        }
    }

    /**
     * Adds a class to the system.
     * @param course the class to add
     */
    public void addClass(Class course) {
        if (course != null && classes.stream().noneMatch(c -> c.getClassCode().equals(course.getClassCode()))) {
            this.classes.add(course);
        }
    }

    /**
     * Enrolls a student in a class and updates the total enrollment count.
     * @param student The student to enroll.
     * @param course The class to enroll the student in.
     */
    public void enrollStudent(RegularStudent student, Class course) {
        if (student != null && course != null) {
            // Check if student is already in the class to avoid duplicate enrollment counts
            if (student.getClasses().stream().noneMatch(c -> c.getClassCode().equals(course.getClassCode()))) {
                student.enrollClass(course);
                this.totalEnrollments++;
            }
        }
    }

    /**
     * Assigns a grade to a student for a class.
     * @param studentID the student's ID
     * @param classCode the class code
     * @param grade the grade to assign
     */
    public void assignGrade(String studentID, String classCode, double grade) {
        // BUG FIX: Centralized GPA calculation and removed flawed logic.
        gradeRecords.add(new GradeRecord(studentID, classCode, grade));

        // After adding a grade, find the relevant student and trigger a GPA recalculation.
        for (RegularStudent student : this.students) {
            if (student.getId().equals(studentID)) {
                student.calculateGPA(this.gradeRecords);
                break; // Exit loop once student is found and updated
            }
        }
    }

    /**
     * Curves grades using the AutoCurver class.
     * @param csvFilePath the path to the CSV file
     * @param curveType the type of curve ("sqrt", "log", "exp", "power", "sigmoid", "stddev", "zscore", "ratio", "flat")
     * @param curveValue the curve value (e.g., target mean for stddev, points for flat)
     */
    public void curveGrades(String csvFilePath, String curveType, double curveValue) {
        this.curver = new AutoCurver(csvFilePath, curveType, curveValue);
        ArrayList<GradeRecord> curvedRecords = this.curver.readCSV();

        if (curvedRecords == null || curvedRecords.isEmpty()) {
            System.err.println("Error: No valid records to curve from " + csvFilePath);
            return;
        }

        this.curver.applyCurve(curvedRecords);
        this.curver.saveCurvedGrades(curvedRecords);

        // BUG FIX: Safely update the main gradeRecords list and recalculate GPAs.
        for (GradeRecord curvedRecord : curvedRecords) {
            // Update the grade in the central gradeRecords list
            for (GradeRecord originalRecord : this.gradeRecords) {
                if (originalRecord.getStudentID().equals(curvedRecord.getStudentID()) &&
                        originalRecord.getClassCode().equals(curvedRecord.getClassCode())) {
                    originalRecord.assignGrade(curvedRecord.getGrade());
                    break;
                }
            }
        }

        // After all grades are updated, recalculate GPA for all affected students.
        for (RegularStudent student : this.students) {
            student.calculateGPA(this.gradeRecords);
        }
    }


    /**
     * Generates a report of all students and grades.
     * @return string containing the report
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder("Student Management System Report\n");
        report.append("====================================\n");
        report.append("Total Enrollments: ").append(this.totalEnrollments).append("\n\n");
        report.append("Students:\n");
        report.append("---------\n");
        for (RegularStudent student : this.students) {
            report.append(student.getDetails())
                    .append(String.format(", GPA: %.2f", student.getGPA()))
                    .append("\n");
        }
        report.append("\nGrades:\n");
        report.append("-------\n");
        for (GradeRecord record : this.gradeRecords) {
            report.append("Student ID: ").append(record.getStudentID())
                    .append(", Class: ").append(record.getClassCode())
                    .append(String.format(", Grade: %.2f", record.getGrade()))
                    .append(", Passing: ").append(record.isPassing() ? "Yes" : "No")
                    .append("\n");
        }
        report.append("====================================\n");
        return report.toString();
    }

    /**
     * Checks the system's status.
     * @return string describing the system's status
     */
    public String checkSystemStatus() {
        StringBuilder statusString = new StringBuilder("Checking System Status:\n");
        statusString.append("Students: ").append(this.students.size()).append("\n");
        statusString.append("Classes: ").append(this.classes.size()).append("\n");
        statusString.append("Grade Records: ").append(this.gradeRecords.size()).append("\n");
        statusString.append("Total Enrollments: ").append(this.totalEnrollments).append("\n");
        return statusString.toString();
    }
}
