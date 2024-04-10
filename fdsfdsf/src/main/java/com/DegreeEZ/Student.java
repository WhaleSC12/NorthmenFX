package com.DegreeEZ;

import java.util.ArrayList;
import java.util.UUID;


public class Student extends User {
    private Major major;
    private ArrayList<CompletedCourse> completedCourses;
    private ArrayList<Course> enrolledCourses;
    private ArrayList<Course> outstandingRequirements;
    private UUID advisorUUID;
    private String advisorNote;

    // Constructor
    public Student(UUID uuid,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   UUID majorUUID,
                   ArrayList<CompletedCourse> completedCourses,
                   ArrayList<Course> enrolledCourses,
                   ArrayList<Course> outstandingRequirements,
                   UUID advisorUUID) {
        super(uuid, username, firstName, lastName, password);
        major = MajorList.getMajorByUUID(majorUUID);
        this.completedCourses = completedCourses;
        this.enrolledCourses = enrolledCourses;
        this.outstandingRequirements = outstandingRequirements;
        this.advisorUUID = advisorUUID;
        this.advisorNote = "";
    }
    public Student(UUID uuid,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   UUID majorUUID){
        this(uuid,firstName,lastName,username,password,majorUUID,new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null);
    }

    public Major getMajor() {
        return this.major;
    }

    public ArrayList<Course> getEnrolledClasses() {
        return this.enrolledCourses;
    }

    public ArrayList<Course> getOutstandingRequirements() {
        return this.outstandingRequirements;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public ArrayList<CompletedCourse> getCompletedCourses() {
        return completedCourses;
    }

    public void setCompletedCourses(ArrayList<CompletedCourse> completedCourses) {
        this.completedCourses = completedCourses;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setOutstandingRequirements(ArrayList<Course> outstandingRequirements) {
        this.outstandingRequirements = outstandingRequirements;
    }

    public Advisor getAdvisor() {
        return AdvisorList.getAdvisorByUUID(advisorUUID);
    }

    public void setAdvisor(Advisor advisor) {
        this.advisorUUID = advisor.getUUID();
    }

    public UUID getAdvisorUuid() {
        return advisorUUID;
    }

    public int getCompletedCredits() {
        int credits = 0;
        for (CompletedCourse c: completedCourses) {
            credits += c.getCourse().getCreditHours();
        }
        return credits;
    }

    public int getTotalRequiredCredits() {
        int credits = 0;
        for (Course c : major.getRequiredCourses()) {
            credits += c.getCreditHours();
        }
        credits += major.getElectiveCreditsRequired();
        return credits;
    }

    public boolean courseNeededInFuture(Course c) {
        for (Course c2 : getEnrolledCourses()) {
            if (c2.getId().equals(c.getId())) {
                return false;
            }
        }

        for (CompletedCourse cc : completedCourses) {
            if (cc.getCourse().getId().equals(c.getId())) {
                return false;
            }
        }
        return true;
    }

    public String getAdvisorNote() {
        return advisorNote;
    }

    public void setAdvisorNote(String advisorNote) {
        this.advisorNote = advisorNote;
    }

    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
