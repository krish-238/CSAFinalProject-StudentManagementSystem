/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Main Class
 * 06/11/2025
 *
 * The Main class provides an interactive command-line interface (CLI) to manage the system.
 * It allows users to import/export data, manage students, and view reports. The grade
 * curving functionality is now a separate, standalone tool (GradeCurverMain.java).
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Optional;

public class Main {
    private static SystemManager manager = new SystemManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Student Management System!");

        while (true) {
            printMainMenu();
            int choice = getUserChoice();

            if (choice == 1) {
                handleStudentManagement();
            } else if (choice == 2) {
                importStudentsCSV();
            } else if (choice == 3) {
                exportStudentsCSV();
            } else if (choice == 4) {
                System.out.println(manager.generateReport());
            } else if (choice == 5) {
                System.out.println("Exiting system. Goodbye!");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Manage Students");
        System.out.println("2. Import Students from CSV");
        System.out.println("3. Export Students to CSV");
        System.out.println("4. Generate System Report");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleStudentManagement() {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. View Student Details");
            System.out.println("2. Add New Student");
            System.out.println("3. Edit Existing Student");
            System.out.println("4. Remove Student");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getUserChoice();

            if (choice == 1) {
                viewStudentDetails();
            } else if (choice == 2) {
                addStudentManually();
            } else if (choice == 3) {
                editStudent();
            } else if (choice == 4) {
                removeStudent();
            } else if (choice == 5) {
                return;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewStudentDetails() {
        ArrayList<RegularStudent> students = manager.getStudents();
        if (students.isEmpty()) {
            System.out.println("There are no students in the system.");
            return;
        }

        System.out.println("\n--- Student Roster ---");
        for (RegularStudent s : students) {
            System.out.println("ID: " + s.getId() + ", Name: " + s.getName());
        }

        System.out.print("\nEnter the ID of the student to view their grades: ");
        String studentId = scanner.nextLine();

        Optional<RegularStudent> studentOpt = manager.findStudentById(studentId);
        if (studentOpt.isEmpty()) {
            System.out.println("Error: No student found with that ID.");
            return;
        }

        RegularStudent student = studentOpt.get();
        ArrayList<GradeRecord> grades = manager.getGradesForStudent(studentId);

        System.out.println("\n--- Grade Report for " + student.getName() + " (ID: " + student.getId() + ") ---");
        System.out.printf("Current GPA: %.2f%n", student.getGPA());
        System.out.println("--------------------------------------------------");

        if (grades.isEmpty()) {
            System.out.println("This student has no grade records.");
        } else {
            for (GradeRecord grade : grades) {
                System.out.printf("Class Code: %-10s | Grade: %-6.2f | Passing: %s%n",
                        grade.getClassCode(), grade.getGrade(), (grade.isPassing() ? "Yes" : "No"));
            }
        }
        System.out.println("--------------------------------------------------");
    }

    private static void addStudentManually() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        if (manager.findStudentById(id).isPresent()) {
            System.out.println("Error: A student with this ID already exists.");
            return;
        }
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Grade Level (e.g., 9, 10, 11): ");
        int gradeLevel = getIntInput();
        System.out.print("Enter Student Type (AP or Regular): ");
        String type = scanner.nextLine();

        if ("AP".equalsIgnoreCase(type)) {
            manager.addStudent(new APStudent(id, name, gradeLevel));
        } else {
            manager.addStudent(new RegularStudent(id, name, gradeLevel));
        }
        System.out.println("Student added successfully!");
    }

    private static void editStudent() {
        System.out.print("Enter the ID of the student to edit: ");
        String id = scanner.nextLine();
        if (manager.findStudentById(id).isEmpty()) {
            System.out.println("Error: No student found with that ID.");
            return;
        }

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new grade level: ");
        int newGradeLevel = getIntInput();

        if (manager.editStudent(id, newName, newGradeLevel)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Failed to update student.");
        }
    }

    private static void removeStudent() {
        System.out.print("Enter the ID of the student to remove: ");
        String id = scanner.nextLine();
        if (manager.removeStudent(id)) {
            System.out.println("Student successfully removed from the system.");
        } else {
            System.out.println("Error: No student found with that ID.");
        }
    }

    private static void importStudentsCSV() {
        System.out.print("Enter the file path for the student CSV to import: ");
        String path = scanner.nextLine();
        manager.importStudentsFromCSV(path);
    }

    private static void exportStudentsCSV() {
        System.out.print("Enter the file path to export the student CSV to (e.g., export.csv): ");
        String path = scanner.nextLine();
        manager.exportStudentsToCSV(path);
    }

    private static int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a whole number: ");
                scanner.nextLine();
            }
        }
    }
}