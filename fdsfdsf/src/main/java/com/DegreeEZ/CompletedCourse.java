package com.DegreeEZ;


import java.util.UUID;

public class CompletedCourse{
    private String grade;
    private Course course;
    private int semesterTaken; // Semester taken (1-8)

    public CompletedCourse(UUID courseID, String grade, int semesterTaken) {
        for (Course c : CourseList.getCourses()) {
            if (c.getId().equals(courseID)) {
                course = c;
            }
        }
        if (course == null) {
            System.err.println("Warning: Could not find a course matching ID " + courseID);
        }
        this.grade = grade;
        this.semesterTaken = semesterTaken;
    }

    // Getters and setters for the new fields
    public int getGrade() {
        return grade;
    }

    public Course getCourse() {
        return course;
    }

    public int getSemesterTaken() {
        return semesterTaken;
    }

    public void setSemesterTaken(int semesterTaken) {
        this.semesterTaken = semesterTaken;
    }

    public String toString() {
        return course.toString() + " Grade: " + grade;
    }

}
