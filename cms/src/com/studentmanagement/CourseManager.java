package com.studentmanagement;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CourseManager {
    private final ArrayList<Course> courses;

    public CourseManager() {
        this.courses = new ArrayList<>();
    }

    public CourseManager(ArrayList<Course> initialCourses) {
        this.courses = new ArrayList<>();
        if (initialCourses != null) {
            for (Course course : initialCourses) {
                if (course != null && !containsCourseCode(this.courses, course.getCourseCode())) {
                    this.courses.add(course);
                }
            }
        }
    }

    public boolean addCourse(Course course) {
        if (course == null || courseExists(course.getCourseCode())) {
            return false;
        }
        return courses.add(course);
    }

    public boolean addCourse(String courseCode, String courseTitle, int units) {
        return addCourse(new Course(courseCode, courseTitle, units));
    }
 
    public boolean removeCourse(String courseCode) {
        Course course = findCourse(courseCode);
        if (course == null) {
            return false;
        }
        return courses.remove(course);
    }

    public Course deleteCourse(String courseCode) {
        Course course = findCourse(courseCode);
        if (course != null) {
            courses.remove(course);
        }
        return course;
    }

    public Course getCourse(String courseCode) {
        return findCourse(courseCode);
    }

    public Course findCourse(String courseCode) {
        if (courseCode == null) {
            return null;
        }
        for (Course currentCourse : courses) {
            if (courseCode.trim().equalsIgnoreCase(currentCourse.getCourseCode())) {
                return currentCourse;
            }
        }
        return null;
    }

    private static boolean containsCourseCode(List<Course> courseList, String courseCode) {
        if (courseCode == null) {
            return false;
        }
        for (Course course : courseList) {
            if (courseCode.equalsIgnoreCase(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }

    public boolean courseExists(String courseCode) {
        return findCourse(courseCode) != null;
    }

    public boolean containsCourse(String courseCode) {
        return courseExists(courseCode);
    }

    public ArrayList<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public ArrayList<Course> getAllCourses() {
        return getCourses();
    }

    public ArrayList<Course> listCourses() {
        return getCourses();
    }

    public int getCourseCount() {
        return courses.size();
    }

    public int size() {
        return courses.size();
    }

    public boolean isEmpty() {
        return courses.isEmpty();
    }

    public void clear() {
        courses.clear();
    }

    public void sortByCourseCode() {
        Collections.sort(courses, Comparator.comparing(Course::getCourseCode, String.CASE_INSENSITIVE_ORDER));
    }

    public void displayCourses() {
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public String listCoursesAsString() {
        StringBuilder builder = new StringBuilder();
        for (Course course : courses) {
            builder.append(course).append(System.lineSeparator());
        }
        return builder.toString();
    }

    public int calculateTotalUnits() {
        int total = 0;
        for (Course course : courses) {
            total += course.getUnits();
        }
        return total;
    }

    public void saveToFile(Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Course course : courses) {
            lines.add(course.getCourseCode() + "|" + course.getCourseTitle() + "|" + course.getUnits());
        }

        Path parent = filePath.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public void loadFromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        List<Course> loadedCourses = new ArrayList<>();
        CourseManager loadedManager = new CourseManager();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int firstSeparator = line.indexOf('|');
            int lastSeparator = line.lastIndexOf('|');
            if (firstSeparator <= 0 || lastSeparator == firstSeparator || lastSeparator == line.length() - 1) {
                throw new IOException("Invalid course data on line " + (i + 1) + ".");
            }

            try {
                String code = line.substring(0, firstSeparator);
                String title = line.substring(firstSeparator + 1, lastSeparator);
                int units = Integer.parseInt(line.substring(lastSeparator + 1).trim());
                Course course = new Course(code, title, units);
                if (!loadedManager.addCourse(course)) {
                    throw new IOException("Duplicate course code on line " + (i + 1) + ".");
                }
                loadedCourses.add(course);
            } catch (IllegalArgumentException ex) {
                throw new IOException("Invalid course data on line " + (i + 1) + ": " + ex.getMessage(), ex);
            }
        }

        courses.clear();
        courses.addAll(loadedCourses);
    }

    @Override
    public String toString() {
        return "CourseManager{" + "courses=" + courses + '}';
    }
}