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

    import java.io.BufferedWriter;
    import java.io.FileWriter;
    import java.io.IOException;

    public class Main {
        public static void main(String[] args) {
            // Initialize SystemManager
            SystemManager manager = new SystemManager();

            // Create students
            RegularStudent student1 = new RegularStudent("S001", "Alice Smith", 10);
            APStudent student2 = new APStudent("S002", "Bob Johnson", 11);
            RegularStudent student3 = new RegularStudent("S003", "Charlie Brown", 10);

            manager.addStudent(student1);
            manager.addStudent(student2);
            manager.addStudent(student3);

            // Create classes
            Class mathClass = new Class("MATH101", "Geometry", "Ms. Davis", 3);
            Class apCalcClass = new Class("CALC-AP", "AP Calculus BC", "Mr. Newton", 2);
            manager.addClass(mathClass);
            manager.addClass(apCalcClass);

            // BUG FIX: Use the centralized enrollStudent method for accurate enrollment counting.
            manager.enrollStudent(student1, mathClass);
            manager.enrollStudent(student2, mathClass);
            manager.enrollStudent(student2, apCalcClass); // AP Student in an AP class
            manager.enrollStudent(student3, mathClass);

            // Assign grades
            manager.assignGrade("S001", "MATH101", 85.0);
            manager.assignGrade("S002", "MATH101", 92.0);
            manager.assignGrade("S002", "CALC-AP", 95.0);
            manager.assignGrade("S003", "MATH101", 68.0);

            System.out.println("--- Initial Report ---");
            System.out.println(manager.generateReport());

            // Create a sample CSV file for testing AutoCurver for the MATH101 class
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("math101_grades.csv"))) {
                writer.write("S001,MATH101,85.0\n");
                writer.write("S002,MATH101,92.0\n");
                writer.write("S003,MATH101,68.0\n");
            } catch (IOException e) {
                System.err.println("Error creating CSV: " + e.getMessage());
                return;
            }

            System.out.println("\n--- Curving MATH101 Grades (Square Root) ---");
            // Curve grades for the math class
            manager.curveGrades("math101_grades.csv", "sqrt", 0.0);

            System.out.println("\n--- Report After Curving ---");
            System.out.println(manager.generateReport());

            // Print final system status
            System.out.println(manager.checkSystemStatus());
        }
    }
