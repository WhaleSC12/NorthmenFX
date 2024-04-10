package DegreeEZ;

import java.util.ArrayList;
import java.util.UUID;

public class AdvisorList {
    private static AdvisorList instance = null;
    private ArrayList<Advisor> advisors;

    private AdvisorList() {
        advisors = (ArrayList<Advisor>) DataLoader.loadAdvisors("advisors.json");
    }

    public static synchronized AdvisorList getInstance() {
        if (instance == null) {
            instance = new AdvisorList();
        }
        return instance;
    }

    public static synchronized Advisor getAdvisorByUUID(UUID id) {
        for (Advisor a : getInstance().advisors) {
            if (a.getUUID().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public static synchronized ArrayList<Advisor> getAdvisors() {
        return getInstance().advisors;
    }

}
