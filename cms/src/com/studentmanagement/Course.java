package com.studentmanagement;

import java.util.Locale;
import java.util.Objects;

public class Course {
    private int units;
    private String courseTitle;
    private String courseCode;

    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = cleanCourseCode(courseCode);
        this.courseTitle = cleanCourseTitle(courseTitle);
        this.units = validateUnits(units);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = cleanCourseCode(courseCode);
    }

    private static String cleanCourseCode(String courseCode) {
        String cleanedCode = courseCode == null ? "" : courseCode.trim();
        if (cleanedCode.isEmpty() || cleanedCode.contains("|")
                || cleanedCode.contains("\n") || cleanedCode.contains("\r")) {
            throw new IllegalArgumentException("Course code cannot be empty or contain | or a line break.");
        }
        return cleanedCode.toUpperCase(Locale.ROOT);
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = cleanCourseTitle(courseTitle);
    }

    private static String cleanCourseTitle(String courseTitle) {
        String cleanedTitle = courseTitle == null ? "" : courseTitle.trim();
        if (cleanedTitle.isEmpty() || cleanedTitle.contains("\n") || cleanedTitle.contains("\r")) {
            throw new IllegalArgumentException("Course title cannot be empty or contain a line break.");
        }
        return cleanedTitle;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = validateUnits(units);
    }

    private static int validateUnits(int units) {
        if (units <= 0) {
            throw new IllegalArgumentException("Course units must be greater than zero.");
        }
        return units;
    }

    @Override
    public String toString() {
        return courseCode + " | " + courseTitle + " | " + units + " units";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Course course = (Course) obj;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }
}