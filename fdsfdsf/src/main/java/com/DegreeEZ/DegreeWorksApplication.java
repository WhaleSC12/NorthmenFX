package com.DegreeEZ;
import java.util.ArrayList;
import java.util.UUID;

import com.DegreeEZ.User;

public class DegreeWorksApplication {
    private User user;
        private static DegreeWorksApplication instance = null;


    private DegreeWorksApplication() {
    }

    private ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        userList.addAll(AdvisorList.getAdvisors());
        userList.addAll(StudentList.getStudents());
        return userList;
    }

    public User login(String username, String password) {
        ArrayList<User> userList = getAllUsers();
        for (User user : userList) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                this.user = user;
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

    public User createAccount(boolean isAdvisor, String firstName, String lastName, String username, String password, Major major) {
        ArrayList<User> userList = getAllUsers();
        if (userList.stream().anyMatch(user -> user.getUserName().equals(username))) {
            System.err.printf("Username %s already exists!%n", username);
            return null;
        }
        UUID uuid = UUID.randomUUID();
        User newUser;
        if (!isAdvisor) {
            newUser = new Student(uuid, firstName, lastName, username, password, major.getMajorID(), new ArrayList<CompletedCourse>(), new ArrayList<Course>(), new ArrayList<Course>(), null);
            StudentList.getStudents().add((Student) newUser);
        } else{
            newUser = new Advisor(uuid, firstName, lastName, username, password);
            AdvisorList.getAdvisors().add((Advisor) newUser);
        }
        return newUser;
    }

    public User getUser() {
        return user;
    }
}



