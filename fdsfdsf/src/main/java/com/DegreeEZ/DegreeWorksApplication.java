package com.DegreeEZ;
import java.util.ArrayList;
import java.util.UUID;

import com.DegreeEZ.User;

public class DegreeWorksApplication {
    private User user;
        private static DegreeWorksApplication instance = null;


    private DegreeWorksApplication() {
    }


    public User login(String username, String password) {
        ArrayList<Student> allStudents = StudentList.getStudents();
        ArrayList<Advisor> allAdvisors = AdvisorList.getAdvisors();
        for (Student student : allStudents) {
            if (student.getUserName().equals(username) && student.getPassword().equals(password)) {
                this.user = student;
                return user;
            }
        }
        for (Advisor advisor : allAdvisors) {
            if (advisor.getUserName().equals(username) && advisor.getPassword().equals(password)) {
                this.user = advisor;
                return user;
            }
        }
        return null;
    }

    public void logout() {
        this.user = null;
    }

    public static DegreeWorksApplication getInstance() {
        if(instance == null){
            instance = new DegreeWorksApplication();
        }

        return instance;
    }

    public void setUser(User user) {
        this.user = user; 
    }
    

    public User createAccount(boolean isAdvisor, String firstName, String lastName, String username, String password, Major major, ArrayList<CompletedCourse> completedCourses, ArrayList<Course> enrolledCourses, ArrayList<Course> outstandingRequirements, UUID advisorUUID, ArrayList<String> advisorNotes, int semestersCompleted, String bio, ArrayList<UUID> studentUUIDs) {
        ArrayList<Student> allStudents = StudentList.getStudents();
        ArrayList<Advisor> allAdvisors = AdvisorList.getAdvisors();
        if (allStudents.stream().anyMatch(user -> user.getUserName().equals(username)) || allAdvisors.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.err.printf("Username %s already exists!%n", username);
            return null;
        }
        UUID uuid = UUID.randomUUID();
        User newUser;
        if (!isAdvisor) {
            newUser = new Student(uuid, firstName, lastName, username, password, major.getMajorID(), completedCourses, enrolledCourses, outstandingRequirements, advisorUUID, advisorNotes, semestersCompleted, bio);
            StudentList.getStudents().add((Student) newUser);
        } else{
            newUser = new Advisor(uuid, username, firstName, lastName, password, studentUUIDs);
            AdvisorList.getAdvisors().add((Advisor) newUser);
        }
        return newUser;
    }

    public User getUser() {
        return user;
    }
}



