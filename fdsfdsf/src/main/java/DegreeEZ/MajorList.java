package DegreeEZ;
import java.util.ArrayList;
import java.util.UUID;

public class MajorList {
    private static MajorList instance;
    private final ArrayList<Major> majors;

    private MajorList() {
        majors = DataLoader.loadMajors("majors.json");
    }
    
    public static synchronized MajorList getInstance() {
        if (instance==null) {
            instance = new MajorList();
        }
        return instance;
    }
    public static synchronized ArrayList<Major> getMajors() {
        return getInstance().majors;
    }

    public static synchronized Major getMajorByUUID(UUID id) {
        for (Major m : getMajors()) {
            if (m.getMajorID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public static synchronized Major getMajorByName(String name) {
        for (Major m : getMajors()) {
            if (m.getMajorName().equalsIgnoreCase(name.trim())) {
                return m;
            }
        }
        return null;
    }
}
