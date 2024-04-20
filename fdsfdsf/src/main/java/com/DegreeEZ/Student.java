package com.DegreeEZ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class Student extends User {
    private Major major;
    private ArrayList<CompletedCourse> completedCourses;
    private ArrayList<Course> enrolledCourses;
    private ArrayList<Course> outstandingRequirements;
    private UUID advisorUUID;
    private ArrayList<String> advisorNotes;
    private int semestersCompleted;
    private String bio;

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
                   UUID advisorUUID,
                   ArrayList<String> advisorNotes,
                   int semestersCompleted,
                   String bio) {
        super(uuid, username, firstName, lastName, password);
        this.major = MajorList.getMajorByUUID(majorUUID);
        this.completedCourses = completedCourses;
        this.enrolledCourses = enrolledCourses;
        this.outstandingRequirements = outstandingRequirements;
        this.advisorUUID = advisorUUID;
        this.advisorNotes = new ArrayList<String>();
        this.semestersCompleted = semestersCompleted;
        this.bio = bio;
    }
    public Student(UUID uuid,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   UUID majorUUID){
        this(uuid,firstName,lastName,username,password,majorUUID,new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), null, null,0,"");
    }


    public Major getMajor() {
        return this.major;
    }

    public boolean isCurrentlyEnrolled(Course course) {
        return enrolledCourses.contains(course);
    }

    public CompletedCourse findCompletedCourse(UUID courseID) {
        for (CompletedCourse comp : completedCourses) {
            if (comp.getCourse().getId().equals(courseID)) {
                return comp;
            }
        }
        return null;
    }

     public ArrayList<Course> getCompletedCoursesAsCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        for (CompletedCourse comp : completedCourses) {
            courses.add(comp.getCourse());
        }
        return courses;
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

    public void addCompletedCourse(CompletedCourse completedCourse) {
        completedCourses.add(completedCourse);
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void addEnrolledCourse(Course enrolledCourse) {
        enrolledCourses.add(enrolledCourse);
    }

    public void addOutstandingRequirement(Course outstandingRequirement) {
        enrolledCourses.add(outstandingRequirement);
    }

    public void setOutstandingRequirements(ArrayList<Course> outstandingRequirements) {
        this.outstandingRequirements = outstandingRequirements;
    }

    public Advisor getAdvisor() {
        return AdvisorList.getAdvisorByUUID(advisorUUID);
    }

    public void setAdvisor(Advisor advisor) {
        advisorUUID = advisor.getUUID();
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
        for (Course c : major.getRequiredCourseList()) {
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

    public ArrayList<String> getAdvisorNotes() {
        return this.advisorNotes;
    }

    public void setAdvisorNote(ArrayList<String> advisorNotes) {
        this.advisorNotes = advisorNotes;
    }

    public void printAdvisorNotes() {
        for (String s : getAdvisorNotes()) {
            System.out.println(s);
        }
    }
    
    public void addAdvisorNote(String note) {
        if (advisorNotes == null) {
            advisorNotes = new ArrayList<>();
            advisorNotes.add(note);
        }
        advisorNotes.add(note);
    }


    public void setSemestersCompleted(int semestersCompleted) {
        this.semestersCompleted = semestersCompleted;
    }

    public int getSemestersCompleted() {
        return this.semestersCompleted;
    }

    public String getStudentYear() {
        if (this.semestersCompleted >= 0 && this.semestersCompleted <= 1) {
            return "Freshman";
        } else if (this.semestersCompleted <= 3) {
            return "Sophomore";
        } else if (this.semestersCompleted <= 5) {
            return "Junior";
        } else if (this.semestersCompleted <= 7) {
            return "Senior";
        } else {
            return "Graduate"; 
        }
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double calculateGPA() {
        if (completedCourses.isEmpty()) {
            return 0.0; // Return 0.0 if no courses have been completed
        }
    
        double totalPoints = 0;
        int totalCredits = 0;
    
        HashMap<String, Double> gradePoints = new HashMap<>();
        gradePoints.put("A", 4.0);
        gradePoints.put("B", 3.0);
        gradePoints.put("C", 2.0);
        gradePoints.put("D", 1.0);
        gradePoints.put("F", 0.0);
    
        for (CompletedCourse c : completedCourses) {
            double points = gradePoints.getOrDefault(c.getGrade(), 0.0);
            int credits = c.getCourse().getCreditHours();
            totalPoints += points * credits;
            totalCredits += credits;
        }
    
        if (totalCredits == 0) {
            return 0.0; // Prevent division by zero
        }
    
        return totalPoints / totalCredits;
    }
    



    public String toString() {
        return getFirstName() + " " + getLastName();
    }


    public void setAdvisorNote(Student student, String note) {
        student.addAdvisorNote(note);
    }

    
}
