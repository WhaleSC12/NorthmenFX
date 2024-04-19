package com.DegreeEZ;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DegreeEZ.Prerequisite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

class DataLoader {
    public static ArrayList<Student> loadStudents(String filePath) {
        ArrayList<Course> allCourses = loadCourses("fdsfdsf/src/main/java/com/DegreeEZ/courses.json"); 
        String jsonString = readFileAsString(filePath);
        if (jsonString == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<Student> students = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject studentJSON = jsonArray.getJSONObject(i);
            UUID studentUuid = UUID.fromString(studentJSON.getString("student_uuid"));
            String firstName = studentJSON.getString("student_firstName");
            String lastName = studentJSON.getString("student_lastName");
            String username = studentJSON.getString("student_username");
            String password = studentJSON.getString("student_password");
            UUID majorUUID = UUID.fromString(studentJSON.getString("student_major"));
            int semestersCompleted = studentJSON.getInt("semestersCompleted"); 
            String bio = studentJSON.getString("bio");


            JSONArray completedCoursesArray = studentJSON.getJSONArray("completedCourses");
            ArrayList<CompletedCourse> completedCourses = loadCompletedCoursesFromJSONArray(completedCoursesArray);

            JSONArray enrolledClassesArray = studentJSON.getJSONArray("enrolledClasses");
            ArrayList<Course> enrolledCourses = loadCoursesFromJSONArray(enrolledClassesArray);

            JSONArray outstandingReqArray = studentJSON.getJSONArray("outstandingReq");
            ArrayList<Course> outstandingReqs = loadCoursesFromJSONArray(outstandingReqArray);

            UUID advisorUUID = UUID.fromString(studentJSON.getString("advisor"));
            JSONArray advisorNotesJSON = studentJSON.getJSONArray("advisorNotes");
            ArrayList<String> advisorNotes = new ArrayList<>();
            for (int j = 0; j < advisorNotesJSON.length(); j++) {
                advisorNotes.add(advisorNotesJSON.getString(j));
            }

            students.add(new Student(studentUuid, firstName, lastName, username, password, majorUUID, completedCourses, enrolledCourses, outstandingReqs, advisorUUID, advisorNotes,semestersCompleted,bio));
        }
        return students;
    }

    public static ArrayList<Advisor> loadAdvisors(String filePath) {
        String jsonString = readFileAsString(filePath);
        if (jsonString == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray(jsonString);
        ArrayList<Advisor> advisors = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject advisorJSON = jsonArray.getJSONObject(i);

            UUID uuid = UUID.fromString(advisorJSON.getString("advisor_uuid"));
            String firstName = advisorJSON.getString("advisor_firstName");
            String lastName = advisorJSON.getString("advisor_lastName");
            String username = advisorJSON.getString("advisor_username");
            String password = advisorJSON.getString("advisor_password");

            JSONArray studentUuidsJSON = advisorJSON.getJSONArray("advisor_students");
            ArrayList<UUID> studentUuids = new ArrayList<>();
            for (int j = 0; j < studentUuidsJSON.length(); j++) {
                studentUuids.add(UUID.fromString(studentUuidsJSON.getString(j)));
            }

            Advisor advisor = new Advisor(uuid, username, firstName, lastName, password, studentUuids);
            advisors.add(advisor);
        }
        return advisors;
    }

    public static ArrayList<Major> loadMajors(String fileName) {
        ArrayList<Course> allCourses = loadCourses("fdsfdsf/src/main/java/com/DegreeEZ/courses.json"); 
        String jsonString = readFileAsString(fileName);
        if (jsonString == null) {
            return null;
        }
        JSONArray majorsJSONArray = new JSONArray(jsonString);
        ArrayList<Major> majors = new ArrayList<>();

        for (int i = 0; i < majorsJSONArray.length(); i++) {
            JSONObject majorJson = majorsJSONArray.getJSONObject(i);
            UUID majorId = UUID.fromString(majorJson.getString("major_uuid"));
            String majorName = majorJson.getString("major_name");
            int electiveCreditsRequired = majorJson.getInt("electiveCreditsRequired");

            JSONArray requiredClassesJSON = majorJson.getJSONArray("requiredClasses");
            HashMap<Course, String> requiredCourses = new HashMap<>();

            for (int j = 0; j < requiredClassesJSON.length(); j++) {
                JSONObject reqClass = requiredClassesJSON.getJSONObject(j);
                UUID courseUuid = UUID.fromString(reqClass.getString("course_uuid"));
                String minGrade = reqClass.getString("course_minGrade");
                Course course = findCourseByUUID(allCourses, courseUuid);
                if (course != null) {
                    requiredCourses.put(course, minGrade);
                }
            }

            JSONArray electiveOptionsJSON = majorJson.getJSONArray("electiveOptions");
            ArrayList<Course> electiveOptions = new ArrayList<>();
            for (int k = 0; k < electiveOptionsJSON.length(); k++) {
                UUID electiveUuid = UUID.fromString(electiveOptionsJSON.getString(k));
                Course electiveCourse = findCourseByUUID(allCourses, electiveUuid);
                if (electiveCourse != null) {
                    electiveOptions.add(electiveCourse);
                }
            }

            Major major = new Major(majorId, majorName, requiredCourses, electiveCreditsRequired, electiveOptions);
            majors.add(major);
        }
        return majors;
    }

    private static Course findCourseByUUID(ArrayList<Course> courses, UUID uuid) {
        for (Course course : courses) {
            if (course.getId().equals(uuid)) {
                return course;
            }
        }
        return null;
    }

    public static ArrayList<Course> loadCourses(String fileName) {
        String jsonString = readFileAsString(fileName);
        if (jsonString == null) {
            return null;
        }
        JSONArray coursesJSONArray = new JSONArray(jsonString);
        ArrayList<Course> courses = new ArrayList<>();
        HashMap<UUID, Course> courseMap = new HashMap<>();

        for (int i = 0; i < coursesJSONArray.length(); i++) {
            JSONObject courseJSON = coursesJSONArray.getJSONObject(i);
            UUID uuid = UUID.fromString(courseJSON.getString("course_uuid"));
            String name = courseJSON.getString("course_name");
            Subject subject = Subject.valueOf(courseJSON.getString("course_subject"));
            int number = courseJSON.getInt("course_number");
            int creditHours = courseJSON.getInt("creditHours");
            JSONArray availabilityArray = courseJSON.getJSONArray("availability");
            ArrayList<Semester> availability = new ArrayList<>();
            for (int j = 0; j < availabilityArray.length(); j++) {
                availability.add(Semester.valueOf(availabilityArray.getString(j).toUpperCase()));
            }

            Course course = new Course(uuid, name, subject, number, new ArrayList<>(), availability, creditHours);
            courseMap.put(uuid, course);
            courses.add(course);
        }

        // Process prerequisites after all courses are loaded
        for (int i = 0; i < coursesJSONArray.length(); i++) {
            JSONObject courseJSON = coursesJSONArray.getJSONObject(i);
            UUID uuid = UUID.fromString(courseJSON.getString("course_uuid"));
            Course course = courseMap.get(uuid);
            JSONArray prereqsJSON = courseJSON.getJSONArray("course_prereq");
            ArrayList<Prerequisite> prerequisites = new ArrayList<>();

            for (int j = 0; j < prereqsJSON.length(); j++) {
                JSONObject prereqJSON = prereqsJSON.getJSONObject(j);
                UUID prereqUUID = UUID.fromString(prereqJSON.getString("prereq_uuid"));
                String minGrade = prereqJSON.getString("minGrade");
                Prerequisite prerequisite = new Prerequisite();
                prerequisite.addCourseRequirement(courseMap.get(prereqUUID), minGrade);
                prerequisites.add(prerequisite);
            }

            course.setPrerequisites(prerequisites);
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

    private static ArrayList<Course> loadCoursesFromJSONArray(JSONArray courseArray) {
        ArrayList<Course> courses = new ArrayList<>();
        for (int i = 0; i < courseArray.length(); i++) {
            UUID courseUuid = UUID.fromString(courseArray.getString(i));
            Course course = findCourseByUUID(CourseList.getCourses(), courseUuid);
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }

    private static ArrayList<CompletedCourse> loadCompletedCoursesFromJSONArray(JSONArray completedCourseArray) {
        ArrayList<CompletedCourse> completedCourses = new ArrayList<>();
        for (int i = 0; i < completedCourseArray.length(); i++) {
            JSONObject obj = completedCourseArray.getJSONObject(i);
            UUID courseUuid = UUID.fromString(obj.getString("completedCourse_uuid"));
            String grade = obj.getString("completedCourse_grade");
            int semesterTaken = obj.getInt("completedCourse_semesterTaken");
            Course course = findCourseByUUID(CourseList.getCourses(), courseUuid); // Assuming CourseList has a method to find courses by UUID
            if (course != null) {
                completedCourses.add(new CompletedCourse(course.getId(), grade, semesterTaken));
            }
        }
        return completedCourses;
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