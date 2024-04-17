package DegreeEZ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

public class Prerequisite {
    private HashMap<Course,String> courseRequirements;

    public Prerequisite() {
        courseRequirements = new HashMap<Course,String>();
    }

    public void addCourseRequirement(Course course, String minGrade) {
        courseRequirements.put(course,minGrade);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Set<HashMap.Entry<Course, String>> entries = courseRequirements.entrySet();
        Iterator<HashMap.Entry<Course, String>> it = entries.iterator();
        while (it.hasNext()) {
            HashMap.Entry<Course, String> entry = it.next();
            result.append(entry.getKey().getName());
            result.append(" (Min Grade: ").append(entry.getValue()).append(")");
            if (it.hasNext()) {
                result.append(", "); // To separate entries with a comma
            }
        }
        return result.toString();
    }
    

    public HashMap<Course,String> getCourseRequirements() {
        return courseRequirements;
    }
}
