/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Auto Curver Class
 * 06/05/2025
 *
 * The AutoCurver class processes grade curving in the student management system, reading and
 * writing grades from CSV files. It provides methods to apply 9 different curves, adjusting
 * student grades based on specified parameters.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AutoCurver {
    private String csvFilePath;
    private String curveType;
    private double curveValue;

    public AutoCurver(String csvFilePath, String curveType, double curveValue) {
        if (curveType.equals("stddev") && (curveValue < 0.0 || curveValue > 100.0)) {
            throw new IllegalArgumentException("Invalid curve value for stddev: " + curveValue);
        }
        this.csvFilePath = csvFilePath;
        this.curveType = curveType;
        this.curveValue = curveValue;
    }

    /**
     * Reads grades from a CSV file and returns them as GradeRecord objects.
     * @return ArrayList of GradeRecord objects
     */
    public ArrayList<GradeRecord> readCSV() {
        ArrayList<GradeRecord> records = new ArrayList<>();
        // Using try-with-resources to ensure the reader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(this.csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3 || parts[0] == null || parts[1] == null || parts[2] == null) {
                    System.err.println("Invalid CSV line (null or incorrect format): " + line);
                    continue;
                }
                try {
                    String studentID = parts[0].trim();
                    String classCode = parts[1].trim();
                    double grade = Double.parseDouble(parts[2].trim());
                    if (grade < 0.0 || grade > 100.0) {
                        System.err.println("Invalid grade value in line: " + line);
                        continue;
                    }
                    records.add(new GradeRecord(studentID, classCode, grade));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid grade format in line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return records;
    }

    /**
     * Applies the specified curve to the grades.
     * @param records the GradeRecord objects to curve
     */
    public void applyCurve(ArrayList<GradeRecord> records) {
        if (records == null || records.isEmpty()) {
            System.err.println("Error: No grades to curve");
            return;
        }

        switch (this.curveType) {
            case "sqrt":
                for (GradeRecord record : records) {
                    double grade = record.getGrade();
                    double newGrade = Math.sqrt(Math.max(0, grade)) * 10;
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            case "log":
                for (GradeRecord record : records) {
                    double grade = record.getGrade();
                    double newGrade = 25 * Math.log(Math.max(0, grade) + 1);
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            case "exp":
                for (GradeRecord record : records) {
                    double grade = record.getGrade();
                    double newGrade = (Math.exp(Math.max(0, grade) / 25.0) - 1) * 20;
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            case "power":
                for (GradeRecord record : records) {
                    double grade = record.getGrade();
                    double normalized = Math.max(0, grade) / 100.0;
                    double newGrade = Math.pow(normalized, 2) * 100;
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            case "sigmoid":
                for (GradeRecord record : records) {
                    double grade = record.getGrade();
                    double exponent = -0.1 * (grade - 50);
                    double newGrade = 100 / (1 + Math.exp(exponent));
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            case "stddev":
            case "zscore":
                double sum = 0.0;
                for (GradeRecord record : records) {
                    sum += record.getGrade();
                }
                double mean = sum / records.size();
                double varianceSum = 0.0;
                for (GradeRecord record : records) {
                    double diff = record.getGrade() - mean;
                    varianceSum += diff * diff;
                }
                double stdDev = Math.sqrt(varianceSum / records.size());

                if (stdDev == 0) { // Avoid division by zero if all grades are the same
                    System.err.println("Cannot apply z-score or stddev curve; standard deviation is zero.");
                    return;
                }

                if (this.curveType.equals("stddev")) {
                    double targetMean = this.curveValue;
                    for (GradeRecord record : records) {
                        double grade = record.getGrade();
                        // Shifts the mean to the target value
                        double newGrade = grade - mean + targetMean;
                        record.assignGrade(Math.max(0.0, Math.min(100.0, newGrade)));
                    }
                } else { // zscore
                    double targetMean = 75.0;
                    double targetStdDev = 10.0;
                    for (GradeRecord record : records) {
                        double grade = record.getGrade();
                        double zScore = (grade - mean) / stdDev;
                        double newGrade = (zScore * targetStdDev) + targetMean;
                        record.assignGrade(Math.max(0.0, Math.min(100.0, newGrade)));
                    }
                }
                break;
            case "ratio":
                ArrayList<GradeRecord> sortedRecords = new ArrayList<>(records);
                sortedRecords.sort(Comparator.comparingDouble(GradeRecord::getGrade).reversed());
                int totalStudents = sortedRecords.size();
                int aCount = (int) (totalStudents * 0.10); // 10% A
                int bCount = (int) (totalStudents * 0.20); // 20% B
                int cCount = (int) (totalStudents * 0.40); // 40% C
                int dCount = (int) (totalStudents * 0.20); // 20% D

                for (int i = 0; i < sortedRecords.size(); i++) {
                    GradeRecord originalRecord = sortedRecords.get(i);
                    double newGrade = 50.0; // F
                    if (i < aCount) newGrade = 95.0; // A
                    else if (i < aCount + bCount) newGrade = 85.0; // B
                    else if (i < aCount + bCount + cCount) newGrade = 75.0; // C
                    else if (i < aCount + bCount + cCount + dCount) newGrade = 65.0; // D

                    // The original record from the `records` list needs to be updated.
                    // This is inefficient but preserves original logic.
                    for (GradeRecord recToUpdate : records) {
                        if (recToUpdate.getStudentID().equals(originalRecord.getStudentID()) &&
                                recToUpdate.getClassCode().equals(originalRecord.getClassCode())) {
                            recToUpdate.assignGrade(newGrade);
                            break;
                        }
                    }
                }
                break;
            case "flat":
                for (GradeRecord record : records) {
                    double newGrade = record.getGrade() + this.curveValue;
                    record.assignGrade(Math.min(newGrade, 100.0));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid curve type: " + this.curveType);
        }
    }

    /**
     * Saves the curved grades back to the CSV file.
     * @param records the GradeRecord objects to save
     */
    public void saveCurvedGrades(ArrayList<GradeRecord> records) {
        // Using try-with-resources to ensure the writer is closed automatically
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.csvFilePath))) {
            for (GradeRecord record : records) {
                String line = record.getStudentID() + "," + record.getClassCode() + "," + String.format("%.2f", record.getGrade());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
