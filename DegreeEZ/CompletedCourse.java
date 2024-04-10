package DegreeEZ;


import java.util.UUID;

public class CompletedCourse{
    private int grade;
    private Course course = null;

    public CompletedCourse(UUID courseID, int grade) {
        for (Course c : CourseList.getCourses()) {
            if (c.getId().equals(courseID)) {
                course = c;
            }
        }
        if (course == null) {
            System.err.println("Warning: Could not find a course matching ID " + courseID);
        }
        this.grade = grade;
    }

    // Getters and setters for the new fields
    public int getGrade() {
        return grade;
    }

    public Course getCourse() {
        return course;
    }

    public String toString() {
        return course.toString() + " Grade: " + grade;
    }

}
