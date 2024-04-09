package DegreeEZ;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StudentListTest {
    private StudentList studentList;

    @BeforeEach
    public void setup() throws IOException {
        studentList = StudentList.getInstance();
        List<Student> students = new ArrayList<>();
        students.add(new Student(UUID.randomUUID(), "ASmith", "Amanda", "asmith", "password", 1, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), null));
        students.add(new Student(UUID.randomUUID(), "JDoe", "John", "jdoe", "password1", 2, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), null));
        studentList.setStudents(students);
    }

    @AfterEach
    public void tearDown() throws IOException {
        File studentsFile = new File("students.json");

        if (studentsFile.exists()) {
            studentsFile.delete();
        }

        StudentList.clearInstance();
    }

    // test for students
    @Test
    void testGetStudents() {
        List<Student> students = studentList.getStudents();
        assertEquals(2, students.size(), "The number of students should be 2");

        UUID id1 = UUID.fromString("22919d58-ff8b-4b69-b9df-f960c7b8deb2"); // id for the first student
        UUID id2 = UUID.fromString("6885d4a6-f35c-4f85-8234-da864c38977f"); // id for the second student

        assertTrue(areEqual(students.get(0), StudentList.getStudentByUUID(id1)),
                "The first student should be returned by calling the 'getStudentByUUID' with id1.");
        assertTrue(areEqual(students.get(1), StudentList.getStudentByUUID(id2)),
                "The second student should be returned by calling the 'getStudentByUUID' with id2.");
    }

    private boolean areEqual(Student s1, Student s2) {
        return s1.equals(s2)
                && s1.getUUID().equals(s2.getUUID())
                && s1.getFirstName().equals(s2.getFirstName())
                && s1.getLastName().equals(s2.getLastName())
                && s1.getUserName().equals(s2.getUserName())
                && s1.getPassword().equals(s2.getPassword())
                && s1.getMajor().getMajorID() == s2.getMajor().getMajorID();
    }
}
