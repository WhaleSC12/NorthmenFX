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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseListTest {
    private Course course;

    @BeforeEach
    void setUp() {
        List<Semester> availability = Arrays.asList(Semester.SUMMER, Semester.FALL, Semester.SPRING);

        course = new Course(
                UUID.randomUUID(),
                "Test Course",
                Subject.MATH,
                101,
                70,
                new ArrayList<>(availability),
                4
        );
    }

    @Test
    void testCourseCode() {
        assertEquals("MATH101", course.courseCode());
    }

    @Test
    void testCheckAvailability() {
        assertTrue(course.checkAvailability(Semester.FALL));
        assertFalse(course.checkAvailability(Semester.WINTER)); // Winter should not an option
    }

    @Test
    void testCompletedCourseToString() {
        Course course = new Course(UUID.randomUUID(), "Test Course", Subject.MATH, 101, 70, null, 4);
        CompletedCourse completedCourse = new CompletedCourse(course.getId(), 85);

        assertEquals("Test Course Grade: 85", completedCourse.toString());
    }

    @Test
    void testCompletedCourseGetGrade() {
        Course course = new Course(UUID.randomUUID(), "Test Course", Subject.MATH, 101, 70, null, 4);
        CompletedCourse completedCourse = new CompletedCourse(course.getId(), 90);

        assertEquals(90, completedCourse.getGrade());
    }

    @Test
    void testCompletedCourseGetCourse() {
        Course course = new Course(UUID.randomUUID(), "Test Course", Subject.MATH, 101, 70, null, 4);
        CompletedCourse completedCourse = new CompletedCourse(course.getId(), 80);

        assertEquals(course, completedCourse.getCourse());
    }
}
