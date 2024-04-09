package DegreeEZ;

import java.util.ArrayList;
import java.util.Iterator;

public class Prerequisite {
    private ArrayList<Course> courses;

    public Prerequisite() {
        courses = new ArrayList<Course>();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator<Course> it = courses.iterator();
        while (it.hasNext()) {
            result.append(it.next().getName());
            if (it.hasNext()) {
                result.append(" or ");
            }
        }
        return result.toString();
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}
