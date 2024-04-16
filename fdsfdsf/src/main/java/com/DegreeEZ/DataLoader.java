package com.DegreeEZ;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DegreeEZ.Prerequisite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

class DataLoader {
    public static ArrayList<Student> loadStudents(String filePath) {
        String jsonString = readFileAsString(filePath);
        if (jsonString == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<Student> students = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject studentJSON = jsonArray.getJSONObject(i);
            String UUIDString = (String) studentJSON.keys().next();
            UUID uuid = UUID.fromString(UUIDString);
            JSONObject studentDetails = studentJSON.getJSONObject(UUIDString);

            String firstName = studentDetails.getString("student_firstName");
            String lastName = studentDetails.getString("student_lastName");
            String username = studentDetails.getString("student_username");
            String password = studentDetails.getString("student_password");
            UUID majorUUID = UUID.fromString(studentDetails.getString("student_major"));
            ArrayList<CompletedCourse> completedCourses = loadCompletedCoursesFromJSONArray(studentDetails.getJSONArray("completedCourses"));
            ArrayList<Course> enrolledCourses = loadCoursesFromJSONArray(studentDetails.getJSONArray("enrolledClasses"));
            ArrayList<Course> outstandingReqs = loadCoursesFromJSONArray(studentDetails.getJSONArray("outstandingReq"));
            UUID advisorUUID = UUID.fromString(studentDetails.getString("advisor"));

            students.add(new Student(uuid, firstName, lastName, username, password, majorUUID, completedCourses, enrolledCourses, outstandingReqs, advisorUUID));
        }
        return students;
    }

    public static ArrayList<Advisor> loadAdvisors(String filePath) {
        String jsonString = readFileAsString(filePath);
        if (jsonString == null) {
            return null;
        }
        JSONArray advisorsJSONArray = new JSONArray(jsonString);
        ArrayList<Advisor> advisors = new ArrayList<Advisor>();

        for (int i = 0; i < advisorsJSONArray.length(); i++) {
            JSONObject jsonObject = advisorsJSONArray.getJSONObject(i);
            String UUIDString = (String) jsonObject.keys().next();
            JSONObject advisorJson = jsonObject.getJSONObject(UUIDString);
            UUID uuid = UUID.fromString(UUIDString);
            String firstName = advisorJson.getString("advisor_firstName");
            String lastName = advisorJson.getString("advisor_lastName");
            String username = advisorJson.getString("advisor_username");
            String password = advisorJson.getString("advisor_password");
            JSONArray studentIdsJSON = advisorJson.getJSONArray("advisor_students");
            ArrayList<UUID> studentIDs = new ArrayList<>();
            for (int j = 0; j < studentIdsJSON.length(); j++) {
                studentIDs.add(UUID.fromString(studentIdsJSON.getString(j)));
            }
            advisors.add(new Advisor(uuid, username, firstName, lastName, password));
        }
        return advisors;
    }

    public static ArrayList<Major> loadMajors(String fileName) {
        String jsonString = readFileAsString(fileName);
        if (jsonString == null) {
            return null;
        }
        JSONArray majorsJSONArray = new JSONArray(jsonString);
        ArrayList<Major> majors = new ArrayList<>();

        for (int i = 0; i < majorsJSONArray.length(); i++) {
            JSONObject jsonObject = majorsJSONArray.getJSONObject(i);
            String UUIDString = (String) jsonObject.keys().next();
            JSONObject majorJson = jsonObject.getJSONObject(UUIDString);
            UUID uuid = UUID.fromString(UUIDString);
            String majorName = majorJson.getString("major_name");
            int electiveCreditsRequired = majorJson.getInt("electiveCreditsRequired");
            ArrayList<Course> requiredClasses = loadCoursesFromJSONArray(majorJson.getJSONArray("requiredClasses"));
            ArrayList<Course> electiveOptions = loadCoursesFromJSONArray(majorJson.getJSONArray("electiveOptions"));
            majors.add(new Major(uuid, majorName, requiredClasses, electiveCreditsRequired, electiveOptions));
        }
        return majors;
    }

    public static ArrayList<Course> loadCourses(String fileName) {
        String jsonString = readFileAsString(fileName);
        if (jsonString == null) {
            return null;
        }
        JSONArray coursesJSONArray = new JSONArray(jsonString);
        ArrayList<Course> courses = new ArrayList<Course>();

        for (int i = 0; i < coursesJSONArray.length(); i++) {
            JSONObject jsonObject = coursesJSONArray.getJSONObject(i);
            String UUIDString = (String) jsonObject.keys().next();
            UUID uuid = UUID.fromString(UUIDString);
            JSONObject courseJSON = jsonObject.getJSONObject(UUIDString);
            String name = courseJSON.getString("course_name");
            Subject subject = Subject.valueOf(courseJSON.getString("course_subject"));
            int number = Integer.parseInt(courseJSON.getString("course_number"));
            int creditHours = courseJSON.getInt("creditHours");
            int minGrade = courseJSON.getInt("minGrade");
            JSONArray availability_json_array = courseJSON.getJSONArray("availability");
            ArrayList<Semester> availability = new ArrayList<Semester>();

            for (int j = 0; j < availability_json_array.length(); j++) {
                availability.add(Semester.valueOf(availability_json_array.getString(j).toUpperCase()));
            }

            courses.add(new Course(uuid, name, subject, number, minGrade, availability, creditHours));
        }

        /* Second loop to load prerequisites */
        for (int i = 0; i < coursesJSONArray.length(); i++) {
            JSONObject jo = coursesJSONArray.getJSONObject(i);
            JSONObject courseJSON = jo.getJSONObject((String) jo.keys().next());
            JSONArray prerequisites_json_array = courseJSON.getJSONArray("course_prereq");
            for (int j = 0; j < prerequisites_json_array.length(); j++) {
                Prerequisite prerequisite = new Prerequisite();
                String prerequisite_string = prerequisites_json_array.getString(j);
                String[] codes = prerequisite_string.split(" or ");
                for (String code : codes) {
                    Course c = getCourseFromCode(courses, code);
                    prerequisite.getCourses().add(c);
                }
                courses.get(i).getPrerequisites().add(prerequisite);
            }
        }
        return courses;
    }

    private static String readFileAsString(String filePath){
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<Course> loadCoursesFromJSONArray(JSONArray arr) {
        ArrayList<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < arr.length(); i++) {
            String code = arr.getString(i);
            Course c = CourseList.getCourseByCode(code);
            courses.add(c);
        }
        return courses;

    }

    private static ArrayList<CompletedCourse> loadCompletedCoursesFromJSONArray(JSONArray arr) {
        ArrayList<CompletedCourse> courses = new ArrayList<CompletedCourse>();
        for (int i = 0; i < arr.length(); i++) {
            String code = arr.getString(i);
            Course c = CourseList.getCourseByCode(code);
            if (c == null) {
                continue;
            }
            CompletedCourse cc = new CompletedCourse(c.getId(), 0);
            courses.add(cc);
        }
        return courses;

    }

    private static Course getCourseFromCode(ArrayList<Course> courses, String code) {
        for (Course c : courses) {
            if (c.courseCode().equalsIgnoreCase(code.trim())) {
                return c;
            }
        }
        return null;
    }


    }