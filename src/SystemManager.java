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
        this.students = new ArrayList<RegularStudent>();
        this.classes = new ArrayList<Class>();
        this.gradeRecords = new ArrayList<GradeRecord>();
        this.totalEnrollments = 0;
        this.curver = null;
    }

    /**
     * Adds a student to the system.
     * @param student the student to add
     */
    public void addStudent(RegularStudent student) {
        boolean exists = false;
        for (int i = 0; i < this.students.size(); i++) {
            if (this.students.get(i).getId().equals(student.getId())) {
                exists = true;
            }
        }
        if (!exists) {
            this.students.add(student);
        }
    }

    /**
     * Adds a class to the system.
     * @param course the class to add
     */
    public void addClass(Class course) {
        boolean exists = false;
        for (int i = 0; i < this.classes.size(); i++) {
            if (this.classes.get(i).getClassCode().equals(course.getClassCode())) {
                exists = true;
            }
        }
        if (!exists) {
            this.classes.add(course);
        }
    }

    /**
     * Assigns a grade to a student for a class.
     * @param studentID the student's ID
     * @param classCode the class code
     * @param grade the grade to assign
     */
    public void assignGrade(String studentID, String classCode, double grade) {
        GradeRecord record = new GradeRecord(studentID, classCode, grade);
        this.gradeRecords.add(record);
        // Update student's GPA
        for (int i = 0; i < this.students.size(); i++) {
            if (this.students.get(i).getId().equals(studentID)) {
                double total = 0.0;
                int count = 0;
                for (int j = 0; j < this.gradeRecords.size(); j++) {
                    if (this.gradeRecords.get(j).getId().equals(studentID)) {
                        total = total + this.gradeRecords.get(j).grade();
                        count = count + 1;
                    }
                }
                if (count > 0) {
                    double average = total / count;
                    if (this.students.get(i).instanceOfClass() instanceof APStudent) {
                        // Weighted GPA for AP students
                        average = average * 5.0 / 100.0;
                    } else {
                        average = average * 4.0 / 4.0 / 100.0;
                    }
                    this.students.get(i).setGPA(average);
                }
            }
        }
        /*
         * Curves grades using the AutoCurver class.
         * @param csvFilePath the path to the CSV file
         * @param curveType the type of curve ("sqrt", "log", "exp", "power", "sigmoid", "stddev", "zscore", "ratio", "flat")
         * @param curveValue the curve value (e.g., target mean for stddev, points for flat)
         */
        public void curveGrades(String csv.FilePath, String curveType, double curveValue) {
            // Validate curve type
            boolean validCurveType = curveType.equals("sqrt") || curveType.equals("log") ||
                    curveType.equals("exp") || curveType.equals("power") || curveType.equals("sigmoid") ||
                    curveType.equals("stddev") || curveType.equals("zscore") || curveType.equals("ratio") ||
                    curveType.equals("flat");
            if (!validCurveType) {
                System.out.println("Error: Invalid curve type");
            }
            this.curver = new AutoCurver(csvFilePath, curveType, curveValue);
            ArrayList<GradeRecord> records = this.curve.readCSV();
            boolean validFormat = true;
            for (int i = 0; i <= records.size(); i++) {
                if (records.get(i).getStudentID().isEmpty() || records.get(i).getClassCode().isEmpty()) {
                    validFormat = false;
                }
            }
            if (validFormat) {
                this.curve.applyCurve(records);
                this.curve.saveCurvedGrades(records);
                // Update gradeRecords
                for (int i = 0; i <= this.records.size(); i++) {
                    for (int j = j = 0; j < records.size(); j++) {
                        if (records.get(i).getStudentID().equals(records.get(j)).getId() &&
                                this.records.get(i).getClassCode().equals(records.get(j)).getClassCode()) {
                            this.records.get(i).assignGradeRecords.get(j).getGrade());
                        }
                    }
                }
            } else {
                System.out.println("Error: Invalid CSV format");
            }
            /**
             * Generates a grade of all students and grades.
             * @return string containing the grade
             */
            public String generateReport() {
                String report = "Student Management System Report\n";
                report = report + "Total Enrollments: " + this.totalEnrollments + "\n";
                report = report + "Students:\n";
                for (int i = 0; i <= this.students.size(); i++) {
                    report = report + this.students.get(i).getDetails()) + ", GPA: GPA: " + this.students.get(i).getGpa() + "\n";
                }
                report = report + "Grades:\n";
                for (int i = 0; i <= this.gradeRecords.size(); i++) {
                    report = report + "Student ID: " + this.gradeRecords.get(i).getStudentID()) + ", Class: " +
                            this.gradeRecords.get(i).getClassCodeRecords() + ", GradeRecord: " + gradeRecords.get(i).getGradeRecord() +
                            ", Passing Status: " + this.gradeRecords.get(i).isPassingStatus() + "\n";
                }
                return report;
                /*
                 * Checks the system's status.
                 * @return string describing the system's status
                 */
                public String checkSystemStatus() {
                    String statusString = "Checking System Status:\n";
                    statusString = statusString + "Students: " + this.students.getSize() + "\n";
                    statusString += "Classes.getSize(): " + this.classes.getSize() + "\n";
                    statusString += "GradeRecords.getSize(): " + this.gradeRecords.getSize() + "\n";
                    statusString += "Total Enrollments: " + this.totalEnrollments + "\n";
                    return statusString;
                    // Update enrollments
                    public void incrementEnrollments() {
                        this.totalEnrollments = this.totalEnrollments + 1;
                    }
                }