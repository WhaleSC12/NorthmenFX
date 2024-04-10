package com.DegreeEZ;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataWriter {

    public static void saveStudents(String filePath, List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                String studentJson = String.format(
                    "    {\n" +
                    "        \"%s\": {\n" +
                    "            \"student_firstName\": \"%s\",\n" +
                    "            \"student_lastName\": \"%s\",\n" +
                    "            \"student_username\": \"%s\",\n" +
                    "            \"student_password\": \"%s\",\n" +
                    "            \"student_major\": \"%s\",\n" +
                    "            \"enrolledClasses\": %s,\n" +
                    "            \"outstandingReq\": %s,\n" +
                    "            \"advisor\": \"%s\"\n" +
                    "        }\n" +
                    "    }%s",
                    student.getUUID().toString(), student.getFirstName(), student.getLastName(),
                    student.getUserName(), student.getPassword(), student.getMajor().getMajorID().toString(),
                    formatCoursesList(student.getEnrolledClasses()), formatCoursesList(student.getOutstandingRequirements()),
                    student.getAdvisorUuid().toString(), (i < students.size() - 1) ? "," : "");
                writer.write(studentJson);
                writer.newLine();
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveAdvisors(String filePath, List<Advisor> advisors) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("[\n");
            for (int i = 0; i < advisors.size(); i++) {
                Advisor advisor = advisors.get(i);
                String advisorJson = String.format(
                    "    {\n" +
                    "        \"%s\": {\n" +
                    "            \"advisor_firstName\": \"%s\",\n" +
                    "            \"advisor_lastName\": \"%s\",\n" +
                    "            \"advisor_username\": \"%s\",\n" +
                    "            \"advisor_password\": \"%s\",\n" +
                    "            \"advisor_students\": %s\n" +
                    "        }\n" +
                    "    }%s",
                    advisor.getUUID().toString(), advisor.getFirstName(), advisor.getLastName(),
                    advisor.getUserName(), advisor.getPassword(), formatUuidList(advisor.getStudentUuids()),
                    (i < advisors.size() - 1) ? "," : "");
                writer.write(advisorJson);
                writer.newLine();
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    private static String formatCoursesList(ArrayList<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return "[]";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            sb.append("\"").append(course.getName()).append("\"");
            if (i < courses.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        
        return sb.toString();
    }
    

    private static String formatUuidList(List<UUID> uuids) {
        if (uuids == null || uuids.isEmpty()) return "[]";
    
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < uuids.size(); i++) {
            UUID uuid = uuids.get(i);
            sb.append("\"").append(uuid.toString()).append("\"");
            if (i < uuids.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
    
        return sb.toString();
    }



}    