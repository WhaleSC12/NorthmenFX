package DegreeEZ;

import java.util.ArrayList;
import java.util.UUID;

public class CourseList {
    private static CourseList instance = null;
    private ArrayList<Course> courses;

    private CourseList() {
        courses = DataLoader.loadCourses("courses.json");
    }

    public static synchronized CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    public static synchronized void addCourse(Course c) {
        getInstance().courses.add(c);
    }

    public static synchronized ArrayList<Course> getCourses() {
        return getInstance().courses;
    }

    public static synchronized Course getCourseByUUID(UUID id) {
        for (Course c : getCourses()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public static synchronized Course getCourseByName(String name) {
        for (Course c : getCourses()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public static synchronized Course getCourseByCode(String code) {
        for (Course c : getCourses()) {
            if (c.courseCode().equalsIgnoreCase(code.trim())) {
                return c;
            }
        }
        System.err.println("Could not find course with code " + code);
        return null;
    }

}
