package DegreeEZ;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

class CompletedCourseTest {

    private Course testCourse;
    private UUID testCourseId;

    @BeforeEach
    void setUp() {
        // Clearing CourseList and adding a test course
        CourseList.getInstance().getCourses().clear(); 
        testCourseId = UUID.randomUUID();
        testCourse = new Course(testCourseId, "Test Course", Subject.CS, 101, 0, null, 3); 
        CourseList.addCourse(testCourse);
    }

    @Test
    void completedCourseCreationWithValidCourseId() {
        int testGrade = 95;
        CompletedCourse completedCourse = new CompletedCourse(testCourseId, testGrade);

        assertNotNull(completedCourse.getCourse(), "Course should not be null");
        assertEquals(testCourse, completedCourse.getCourse(), "Courses should match");
        assertEquals(testGrade, completedCourse.getGrade(), "Grades should match");
    }

    @Test
    void completedCourseCreationWithInvalidCourseId() {
        CompletedCourse completedCourse = new CompletedCourse(UUID.randomUUID(), 85); // Random UUID not in CourseList

        assertNull(completedCourse.getCourse(), "Course should be null for an invalid ID");
    }

    @Test
    void toStringTest() {
        CompletedCourse completedCourse = new CompletedCourse(testCourseId, 90);
        String expectedString = testCourse.toString() + " Grade: " + 90;
        assertEquals(expectedString, completedCourse.toString(), "toString output should match expected format");
    }
}
