package com.studentmanagement;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final Path DATA_FILE = Paths.get("data", "courses.txt");

    public static void main(String[] args) {
        CourseManager manager = new CourseManager();
        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner, manager);
        }
    }

    private static void runMenu(Scanner scanner, CourseManager manager) {
        boolean running = true;
        while (running && scanner.hasNextLine()) {
            System.out.println("\n=== Student Course Management ===");
            printMenuOptions(0);
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addCourse(scanner, manager);
                    break;
                case "2":
                    viewCourses(manager);
                    break;
                case "3":
                    searchCourse(scanner, manager);
                    break;
                case "4":
                    System.out.println("Total units: " + manager.calculateTotalUnits());
                    break;
                case "5":
                    saveCourses(manager);
                    break;
                case "6":
                    loadCourses(manager);
                    break;
                case "7":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Enter a number from 1 to 7.");
            }
        }
    }

    private static void printMenuOptions(int index) {
        String[] options = {
            "1. Add Course",
            "2. View All Courses",
            "3. Search Course by Code",
            "4. Compute Total Units",
            "5. Save to File",
            "6. Load from File",
            "7. Exit"
        };
        if (index == options.length) {
            return;
        }
        System.out.println(options[index]);
        printMenuOptions(index + 1);
    }

    private static void addCourse(Scanner scanner, CourseManager manager) {
        String code = readNonEmptyLine(scanner, "Course code: ");
        String title = readNonEmptyLine(scanner, "Course title: ");
        if (code == null || title == null) {
            return;
        }

        int units = readPositiveInteger(scanner, "Credit units: ");
        if (units < 1) {
            return;
        }

        try {
            Course course = new Course(code, title, units);
            if (manager.addCourse(course)) {
                System.out.println("Course added.");
            } else {
                System.out.println("A course with that code already exists.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Could not add course: " + ex.getMessage());
        }
    }

    private static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (scanner.hasNextLine()) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
        return null;
    }

    private static int readPositiveInteger(Scanner scanner, String prompt) {
        while (scanner.hasNextLine()) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                int units = Integer.parseInt(value);
                if (units > 0) {
                    return units;
                }
                System.out.println("Units must be greater than zero.");
            } catch (NumberFormatException ex) {
                System.out.println("Enter a whole number for units.");
            }
        }
        return -1;
    }

    private static void viewCourses(CourseManager manager) {
        if (manager.isEmpty()) {
            System.out.println("No courses have been recorded.");
            return;
        }
        System.out.println("\nRecorded courses:");
        manager.displayCourses();
    }

    private static void searchCourse(Scanner scanner, CourseManager manager) {
        String code = readNonEmptyLine(scanner, "Enter course code to search: ");
        if (code == null) {
            return;
        }

        Course course = manager.findCourse(code);
        if (course == null) {
            System.out.println("No course found with code " + code + ".");
        } else {
            System.out.println("Course found: " + course);
        }
    }

    private static void saveCourses(CourseManager manager) {
        try {
            manager.saveToFile(DATA_FILE);
            System.out.println("Saved " + manager.getCourseCount() + " course(s) to " + DATA_FILE + ".");
        } catch (IOException ex) {
            System.out.println("Could not save courses: " + ex.getMessage());
        }
    }

    private static void loadCourses(CourseManager manager) {
        try {
            manager.loadFromFile(DATA_FILE);
            System.out.println("Loaded " + manager.getCourseCount() + " course(s) from " + DATA_FILE + ".");
        } catch (IOException ex) {
            System.out.println("Could not load courses: " + ex.getMessage());
        }
    }
}
