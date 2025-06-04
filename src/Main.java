/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Main Class
 * 06/11/2025
 *
 * The Main class demonstrates the student management system’s functionality through a command-line
 * interface. It tests core operations like student enrollment, grade assignment, and grade curving,
 * showcasing the system’s capabilities.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Initialize SystemManager
        SystemManager manager = new SystemManager();

        // Create students
        RegularStudent student1 = new RegularStudent("S001", "Alice Smith", 10);
        APStudent student2 = new APStudent("S002", "Bob Johnson", 11);
        manager.addStudent(student1);
        manager.addStudent(student2);

        // Create a class
        Class mathClass = new Class("MATH101", "Geometry", "Ms. Brown", 3);
        manager.addClass(mathClass);

        // Enroll students
        student1.enrollClass(mathClass);
        student2.enrollClass(mathClass);
        manager.incrementEnrollments();
        manager.incrementEnrollments();

        // Assign grades
        manager.assignGradeToStudent("S001", "MATH101", 85.0);
        manager.assignGradeToStudent("S002", "MATH101", 90.0);

        // Create a sample CSV file for testing AutoCurver
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("grades.csv"));
            writer.write("S001,MATH101,85.0");
            writer.newLine();
            writer.write("S002,MATH101,90.0");
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error creating CSV: " + e.getMessage());
        }

        // Curve grades (square root curve)
        manager.curveGrades("grades.csv", "sqrt", 0.0);

        // Print report
        System.out.println(manager.generateReport());

        // Print system status
        System.out.println(manager.checkSystemStatus());
    }
}