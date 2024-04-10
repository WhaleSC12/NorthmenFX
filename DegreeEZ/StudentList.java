package DegreeEZ;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentList {
    private static StudentList instance = null;
    private ArrayList<Student> students;

    private StudentList() {
        students = DataLoader.loadStudents("students.json");
    }

    public static synchronized StudentList getInstance() {
        if (instance == null) {
            instance = new StudentList();
        }
        return instance;
    }

    public static synchronized Student getStudentByUUID(UUID id) {
        for (Student s : getInstance().students) {
            if (s.getUUID().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public static synchronized ArrayList<Student> getStudents() {
        return getInstance().students;
    }

    public static void clearInstance() {
        instance = null;
    }

    
    public void initStudents(List<Student> newStudents) {
        students.clear();

        for (Student student : newStudents) {
            students.add(new Student(student.getUUID(),
                                   student.getFirstName(),
                                   student.getLastName(),
                                   student.getUserName(),
                                   student.getPassword(),
                                   student.getMajor().getMajorID(),
                                   new ArrayList<>(student.getCompletedCourses()),
                                   new ArrayList<>(student.getEnrolledCourses()),
                                   new ArrayList<>(student.getOutstandingRequirements()),
                                   student.getAdvisorUuid()));
        }
    }

    public void loadStudentsFromFile(String filePath) {
        students = DataLoader.loadStudents(filePath);
    }

    public void setStudents(List<Student> newStudents) {
        this.students = new ArrayList<>(newStudents);
    }


}
