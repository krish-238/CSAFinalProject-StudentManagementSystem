/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - Grade Curver Main
 * 06/11/2025
 *
 * This is the main entry point for the Grade Curving utility.
 * It provides a command-line interface to curve individual assignment grades from a CSV file,
 * and save the results to a new file.
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeCurverMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AutoCurver curver = new AutoCurver();

        System.out.println("--- Standalone Assignment Grade Curver ---");

        System.out.print("Enter the path to the input CSV file with scores (e.g., assignment_scores.csv): ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter the path for the new output file (e.g., curved_scores.csv): ");
        String outputFile = scanner.nextLine();

        printCurveDescriptions();

        System.out.print("Enter the curve type from the list above: ");
        String curveType = scanner.nextLine().trim().toLowerCase();

        double curveValue = 0.0;
        if (curveType.equals("flat") || curveType.equals("stddev") || curveType.equals("power")) {
            System.out.print("Enter the required numeric value for this curve: ");
            try {
                curveValue = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number. Using 0.0 for curve value.");
            } finally {
                scanner.nextLine();
            }
        }

        System.out.println("\nReading scores...");
        ArrayList<AssignmentScore> scores = curver.readScores(inputFile);

        if (!scores.isEmpty()) {
            System.out.println("Applying " + curveType + " curve...");
            curver.applyCurve(scores, curveType, curveValue);

            System.out.println("Saving curved scores...");
            curver.saveCurvedScores(scores, outputFile);

            System.out.println("\nProcess complete! Curved scores have been saved to: " + outputFile);
        } else {
            System.out.println("\nCould not process scores. Please check the input file and try again.");
        }

        scanner.close();
    }

    /**
     * Prints a detailed description of all available curve types.
     */
    private static void printCurveDescriptions() {
        System.out.println("\n--- Available Curve Types ---");
        System.out.println("  flat    : Adds a fixed number of points to every score.");
        System.out.println("            -> Value: The number of points to add (e.g., 5).");
        System.out.println("  sqrt    : Takes the square root of the score and multiplies by 10.");
        System.out.println("            -> No value needed.");
        System.out.println("  log     : Applies a logarithmic curve to compress higher scores.");
        System.out.println("            -> No value needed.");
        System.out.println("  exp     : Applies an exponential curve to boost lower scores more significantly.");
        System.out.println("            -> No value needed.");
        System.out.println("  power   : Raises the normalized score (0-1) to a given power.");
        System.out.println("            -> Value: The exponent to use (e.g., 0.5 to boost scores).");
        System.out.println("  sigmoid : A smooth 'S'-shaped curve that boosts scores around the midpoint (50).");
        System.out.println("            -> No value needed.");
        System.out.println("  zscore  : Standardizes scores and rescales them to a target mean and standard deviation.");
        System.out.println("            -> No value needed (uses default mean 75, stddev 10).");
        System.out.println("  stddev  : Shifts all scores to achieve a new target mean score.");
        System.out.println("            -> Value: The desired average score for the class (e.g., 85).");
        System.out.println("  ratio   : Assigns letter grades based on a fixed percentage distribution (e.g., top 10% get an A).");
        System.out.println("            -> No value needed.");
        System.out.println("-----------------------------\n");
    }
}