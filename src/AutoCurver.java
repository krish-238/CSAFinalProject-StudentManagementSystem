/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Auto Curver Class (Standalone)
 * 06/11/2025
 *
 * The AutoCurver class is a standalone utility to process grade curving for a single
 * assignment. It reads student scores from a CSV, applies a specified curve,
 * and saves the results to a new CSV file. It contains all 9 original curve types.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AutoCurver {

    /**
     * Constructs a new AutoCurver object.
     */
    public AutoCurver() {}

    /**
     * Reads assignment scores from a CSV file.
     * @param inputFilePath The path to the input CSV file.
     * @return An ArrayList of AssignmentScore objects.
     */
    public ArrayList<AssignmentScore> readScores(String inputFilePath) {
        ArrayList<AssignmentScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String id = parts[0].trim();
                    double score = Double.parseDouble(parts[1].trim());
                    scores.add(new AssignmentScore(id, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading scores from " + inputFilePath + ": " + e.getMessage());
        }
        return scores;
    }

    /**
     * Applies a curve to a list of assignment scores.
     * @param scores The list of AssignmentScore objects to curve.
     * @param curveType The type of curve to apply.
     * @param curveValue A value used by some curves.
     */
    public void applyCurve(ArrayList<AssignmentScore> scores, String curveType, double curveValue) {
        if (scores == null || scores.isEmpty()) {
            System.err.println("No scores to curve.");
            return;
        }

        if ("sqrt".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore(Math.sqrt(s.getOriginalScore()) * 10);
        } else if ("flat".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore(s.getOriginalScore() + curveValue);
        } else if ("log".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore(25 * Math.log(s.getOriginalScore() + 1));
        } else if ("exp".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore((Math.exp(s.getOriginalScore() / 25.0) - 1) * 20);
        } else if ("power".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore(Math.pow(s.getOriginalScore() / 100.0, curveValue) * 100);
        } else if ("sigmoid".equals(curveType)) {
            for (AssignmentScore s : scores) s.setCurvedScore(100 / (1 + Math.exp(-0.1 * (s.getOriginalScore() - 50))));
        } else if ("stddev".equals(curveType) || "zscore".equals(curveType)) {
            applyStandardDeviationCurves(scores, curveType, curveValue);
        } else if ("ratio".equals(curveType)) {
            applyRatioCurve(scores);
        } else {
            System.err.println("Unknown curve type: " + curveType);
        }
    }

    private void applyStandardDeviationCurves(ArrayList<AssignmentScore> scores, String curveType, double curveValue) {
        double sum = 0.0;
        for (AssignmentScore s : scores) sum += s.getOriginalScore();
        double mean = sum / scores.size();

        double varianceSum = 0.0;
        for (AssignmentScore s : scores) varianceSum += Math.pow(s.getOriginalScore() - mean, 2);
        double stdDev = Math.sqrt(varianceSum / scores.size());

        if (stdDev == 0) {
            System.err.println("Cannot apply z-score or stddev curve; standard deviation is zero.");
            return;
        }

        if ("stddev".equals(curveType)) {
            double targetMean = curveValue;
            for (AssignmentScore s : scores) s.setCurvedScore(s.getOriginalScore() - mean + targetMean);
        } else { // zscore
            double targetMean = 75.0;
            double targetStdDev = 10.0;
            for (AssignmentScore s : scores) {
                double zScore = (s.getOriginalScore() - mean) / stdDev;
                s.setCurvedScore((zScore * targetStdDev) + targetMean);
            }
        }
    }

    private void applyRatioCurve(ArrayList<AssignmentScore> scores) {
        ArrayList<AssignmentScore> sortedScores = new ArrayList<>(scores);
        sortedScores.sort(Comparator.comparingDouble(AssignmentScore::getOriginalScore).reversed());

        int totalStudents = scores.size();
        int aCount = (int) (totalStudents * 0.10);
        int bCount = (int) (totalStudents * 0.20);
        int cCount = (int) (totalStudents * 0.40);
        int dCount = (int) (totalStudents * 0.20);

        for(int i = 0; i < sortedScores.size(); i++) {
            AssignmentScore current = sortedScores.get(i);
            if (i < aCount) current.setCurvedScore(95.0);
            else if (i < aCount + bCount) current.setCurvedScore(85.0);
            else if (i < aCount + bCount + cCount) current.setCurvedScore(75.0);
            else if (i < aCount + bCount + cCount + dCount) current.setCurvedScore(65.0);
            else current.setCurvedScore(50.0);
        }
    }

    /**
     * Saves the original and curved scores to a new CSV file.
     * @param scores The list of scored AssignmentScore objects.
     * @param outputFilePath The path for the new output CSV file.
     */
    public void saveCurvedScores(ArrayList<AssignmentScore> scores, String outputFilePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write("StudentID,OriginalScore,CurvedScore\n");
            for (AssignmentScore score : scores) {
                String line = String.format("%s,%.2f,%.2f",
                        score.getStudentID(),
                        score.getOriginalScore(),
                        score.getCurvedScore());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving curved scores to " + outputFilePath + ": " + e.getMessage());
        }
    }
}
