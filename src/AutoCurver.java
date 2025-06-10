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
import java.util.ArrayList;
import java.io.IOException;

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
        ArrayList<GradeRecord> records = new ArrayList<GradeRecord>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.csvFilePath));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length != 3 || parts[0] == null || parts[1] == null || parts[2] == null) {
                    System.out.println("Invalid CSV line (null or incorrect format): " + line);
                    line = reader.readLine();
                    continue;
                }
                try {
                    String studentID = parts[0].trim();
                    String classCode = parts[1].trim();
                    double grade = Double.parseDouble(parts[2].trim());
                    if (grade < 0.0 || grade > 100.0) {
                        System.out.println("Invalid grade value in line: " + line);
                        line = reader.readLine();
                        continue;
                    }
                    GradeRecord record = new GradeRecord(studentID, classCode, grade);
                    records.add(record);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid grade format in line: " + line);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return records;
    }

    /**
     * Applies the specified curve to the grades.
     * @param records the GradeRecord objects to curve
     */
    public void applyCurve(ArrayList<GradeRecord> records) {
        if (records.isEmpty()) {
            System.out.println("Error: No grades to curve");
            return;
        }
        if (this.curveType.equals("sqrt")) {
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                if (grade < 0) grade = 0;
                double newGrade = Math.sqrt(grade) * 10;
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("log")) {
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                if (grade < 0) grade = 0;
                double newGrade = 25 * Math.log(grade + 1);
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("exp")) {
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                if (grade < 0) grade = 0;
                double newGrade = (Math.exp(grade / 25.0) - 1) * 20;
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("power")) {
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                if (grade < 0) grade = 0;
                double normalized = grade / 100.0;
                double newGrade = Math.pow(normalized, 2) * 100;
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("sigmoid")) {
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                double exponent = -0.1 * (grade - 50);
                double newGrade = 100 / (1 + Math.exp(exponent));
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("stddev")) {
            double sum = 0.0;
            for (int i = 0; i < records.size(); i++) {
                sum += records.get(i).getGrade();
            }
            double mean = sum / records.size();
            double varianceSum = 0.0;
            for (int i = 0; i < records.size(); i++) {
                double diff = records.get(i).getGrade() - mean;
                varianceSum += diff * diff;
            }
            double stdDev = Math.sqrt(varianceSum / records.size());
            double targetMean = this.curveValue;
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                double newGrade = ((grade - mean) * stdDev / stdDev) + targetMean;
                if (newGrade > 100.0) newGrade = 100.0;
                if (newGrade < 0.0) newGrade = 0.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("zscore")) {
            double sum = 0.0;
            for (int i = 0; i < records.size(); i++) {
                sum += records.get(i).getGrade();
            }
            double mean = sum / records.size();
            double varianceSum = 0.0;
            for (int i = 0; i < records.size(); i++) {
                double diff = records.get(i).getGrade() - mean;
                varianceSum += diff * diff;
            }
            double stdDev = Math.sqrt(varianceSum / records.size());
            double targetMean = 75.0;
            double targetStdDev = 10.0;
            for (int i = 0; i < records.size(); i++) {
                double grade = records.get(i).getGrade();
                double zScore = (grade - mean) / stdDev;
                double newGrade = (zScore * targetStdDev) + targetMean;
                if (newGrade > 100.0) newGrade = 100.0;
                if (newGrade < 0.0) newGrade = 0.0;
                records.get(i).assignGrade(newGrade);
            }
        } else if (this.curveType.equals("ratio")) {
            // Sort grades in descending order
            ArrayList<GradeRecord> sortedRecords = new ArrayList<GradeRecord>(records);
            sortedRecords.sort((a, b) -> Double.compare(b.getGrade(), a.getGrade()));
            int totalStudents = sortedRecords.size();
            int aCount = (int) (totalStudents * 0.10); // 10% A
            int bCount = (int) (totalStudents * 0.20); // 20% B
            int cCount = (int) (totalStudents * 0.40); // 40% C
            int dCount = (int) (totalStudents * 0.20); // 20% D
            // Assign grades
            for (int i = 0; i < sortedRecords.size(); i++) {
                double newGrade = 50.0; // F
                if (i < aCount) {
                    newGrade = 90.0; // A
                } else if (i < aCount + bCount) {
                    newGrade = 80.0; // B
                } else if (i < aCount + bCount + cCount) {
                    newGrade = 70.0; // C
                } else if (i < aCount + bCount + cCount + dCount) {
                    newGrade = 60.0; // D
                }
                // Update original record
                for (int j = 0; j < records.size(); j++) {
                    if (records.get(j).getStudentID().equals(sortedRecords.get(i).getStudentID()) &&
                            records.get(j).getClassCode().equals(sortedRecords.get(i).getClassCode())) {
                        records.get(j).assignGrade(newGrade);
                    }
                }
            }
        } else if (this.curveType.equals("flat")) {
            for (int i = 0; i < records.size(); i++) {
                double newGrade = records.get(i).getGrade() + this.curveValue;
                if (newGrade > 100.0) newGrade = 100.0;
                records.get(i).assignGrade(newGrade);
            }
        } else {
            throw new IllegalArgumentException("Invalid curve type: " + this.curveType);
        }
    }

    /**
     * Saves the curved grades back to the CSV file.
     * @param records the GradeRecord objects to save
     */
    public void saveCurvedGrades(ArrayList<GradeRecord> records) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.csvFilePath));
            for (int i = 0; i < records.size(); i++) {
                String line = records.get(i).getStudentID() + "," + records.get(i).getClassCode() + "," + records.get(i).getGrade();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }
}