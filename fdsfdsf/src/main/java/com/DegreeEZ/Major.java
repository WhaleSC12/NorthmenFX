package com.DegreeEZ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Major {
    private UUID majorID;
    private String majorName;
    private ArrayList<Course> requiredCourses;
    private int electiveCreditsRequired;
    private ArrayList<Course> electiveOptions;

    public Major(UUID majorId,
                 String majorName,
                 ArrayList<Course> requiredCourses,
                 int electiveCreditsRequired,
                 ArrayList<Course> electiveOptions) { // need to implement elective cats still
        this.majorID = majorId;
        this.majorName = majorName;
        this.requiredCourses = requiredCourses;
        this.electiveCreditsRequired = electiveCreditsRequired;
        this.electiveOptions = electiveOptions;
    }

    public UUID getMajorID() {
        return majorID;
    }

    public void setMajorID(UUID majorID) {
        this.majorID = majorID;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public ArrayList<Course> getRequiredCourses() {
        return requiredCourses;
    }

    public void setRequiredCourses(ArrayList<Course> requiredCourses) {
        this.requiredCourses = requiredCourses;
    }

    public int getElectiveCreditsRequired() {
        return electiveCreditsRequired;
    }

    public void setElectiveCreditsRequired(int electiveCreditsRequired) {
        this.electiveCreditsRequired = electiveCreditsRequired;
    }

    public ArrayList<Course> getElectiveOptions() {
        return electiveOptions;
    }

    public void setElectiveOptions(ArrayList<Course> electiveOptions) {
        this.electiveOptions = electiveOptions;
    }

    public String toString() {
        return majorName;
    }

    
}