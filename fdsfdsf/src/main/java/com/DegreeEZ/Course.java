package com.DegreeEZ; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.DegreeEZ.Prerequisite;

public class Course {
    private UUID id;
    private Subject subject;
    private int number;
    private String name;
    private ArrayList<Prerequisite> prerequisites;
    private int minGrade;
    private List<Semester> availability; 
    private int creditHours;
    private Semester semester;

    // Constructor
    public Course(UUID id, String name, Subject subject, int number, int minGrade, ArrayList<Semester> availability, int creditHours) {
        this.id = UUID.randomUUID(); // Generate a unique ID for each course
        this.name = name;
        this.subject = subject;
        this.number = number;
        this.minGrade = minGrade;
        this.availability = availability;
        this.creditHours = creditHours;

        this.prerequisites = new ArrayList<Prerequisite>();
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public ArrayList<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinGrade() {
        return minGrade;
    }

    public void setMinGrade(int minGrade) {
        this.minGrade = minGrade;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Semester> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Semester> availability) {
        this.availability = availability;
    }

    public boolean checkAvailability(Semester semester) {
        return this.availability.contains(semester);
    }

    public String toString() {
        return subject + " " + number + "(" + name + ")";
    }

    public String courseCode() {
        return subject.toString() + number;
    }

    public boolean prerequisitesSatisfied(ArrayList<CompletedCourse> completedCourses) {
        ArrayList<Prerequisite> temp_prereqs = (ArrayList<Prerequisite>) prerequisites.clone();
        for (CompletedCourse cc : completedCourses) {
            temp_prereqs.removeIf(p -> p.getCourses().contains(cc.getCourse()));
        }
        return temp_prereqs.size() == 0;
    }
}