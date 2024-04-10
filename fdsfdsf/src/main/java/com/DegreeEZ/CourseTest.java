package DegreeEZ;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void checkAvailability() {
        ArrayList<Semester> availableSemesters = new ArrayList<>(Arrays.asList(Semester.FALL, Semester.SPRING));
        Course course = new Course(UUID.randomUUID(), "Advanced Testing", Subject.CS, 499, 0, availableSemesters, 3);

        assertTrue(course.checkAvailability(Semester.FALL));
        assertFalse(course.checkAvailability(Semester.SUMMER));
    }

    @Test
    void prerequisitesSatisfied() {
        Course prerequisiteCourse = new Course(UUID.randomUUID(), "Prerequisite Course", Subject.CS, 101, 0, new ArrayList<>(), 3);
        ArrayList<CompletedCourse> completedCourses = new ArrayList<>();
        completedCourses.add(new CompletedCourse(prerequisiteCourse.getId(), 5)); // Assuming grade is out of 5

        Course targetCourse = new Course(UUID.randomUUID(), "Target Course", Subject.CS, 102, 0, new ArrayList<>(), 3);
        targetCourse.getPrerequisites().add(new Prerequisite(prerequisiteCourse)); // Assuming Prerequisite can be initialized with a Course

        assertTrue(targetCourse.prerequisitesSatisfied(completedCourses));
    }
}
