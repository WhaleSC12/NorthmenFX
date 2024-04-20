package com.DegreeEZ; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.DegreeEZ.Prerequisite;

public class Course {
    private UUID id;
    private Subject subject;
    private String number;
    private String name;
    private ArrayList<Prerequisite> prerequisites;
    private List<Semester> availability; 
    private int creditHours;
    private int reccomendedSemester;
    private boolean isElective;

    // Constructor
    public Course(UUID id, String name, Subject subject, String number, ArrayList<Prerequisite> prerequisites, ArrayList<Semester> availability, int creditHours, int recommendedSemester, boolean isElective) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.number = number;
        this.availability = availability;
        this.creditHours = creditHours;
        this.reccomendedSemester = recommendedSemester;
        this.isElective = isElective;

        this.prerequisites = prerequisites;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public boolean getIsElective() {
        return isElective;
    }

    public void setElective(boolean torf) {
        isElective = torf;
    }

    public void setPrerequisites(ArrayList<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public ArrayList<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public String printPrerequisites() {
        if (prerequisites == null || prerequisites.isEmpty()) {
            return "No prerequisites.";
        }

        StringBuilder sb = new StringBuilder();
        for (Prerequisite prereq : prerequisites) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            Course prereqCourse = prereq.getCourse();
            sb.append(prereq.getMinGrade())
              .append(" in ")
              .append(prereqCourse.getName())
              .append(" (")
              .append(prereqCourse.getSubject())
              .append(" ")
              .append(prereqCourse.getNumber())
              .append(")");
        }
        return sb.toString();
    }

    public void setReccomendedSemester(int recSemester) {
        this.reccomendedSemester = recSemester;
    }

    public int getReccomendedSemester() {
        return reccomendedSemester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
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
        return subject + " " + number + " (" + name + ")";
    }

    public String getCourseCode() {
        return subject.toString() + number;
    }

}