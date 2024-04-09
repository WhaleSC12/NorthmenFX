package DegreeEZ;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdvisorListTest {

    private static AdvisorList advisorList;
    private static List<Advisor> sampleAdvisors;

    @BeforeAll
    public static void setup() throws IOException {
        File advisorsFile = new File("src/test/resources/advisors_test.json");
        advisorsFile.getParentFile().mkdirs();
        advisorsFile.createNewFile();

        sampleAdvisors = List.of(
                new Advisor(UUID.randomUUID(), "Advisor1", "advisor1_lastName", "advisor1@school.edu", "password"),
                new Advisor(UUID.randomUUID(), "Advisor2", "advisor2_lastName", "advisor2@school.edu", "password")
        );

        DataWriter.writeDataToFile(sampleAdvisors, "advisors_test.json");
    }

    @BeforeEach
    public void init() {
        advisorList = AdvisorList.getInstance();
    }

    @Test
    public void testGetInstance() {
        Assertions.assertNotNull(advisorList);
    }

    @Test
    public void testGetAdvisors() {
        Assertions.assertEquals(sampleAdvisors, advisorList.getAdvisors());
    }

    @Test
    public void testGetAdvisorByUUID() {
        Advisor advisor = sampleAdvisors.get(0);
        AdvisorList.getAdvisors().add(advisor);

        Advisor retrievedAdvisor = AdvisorList.getAdvisorByUUID(advisor.getUUID());
        Assertions.assertEquals(advisor, retrievedAdvisor);
    }

    @Test
    public void testLoadAdvisors() throws IOException {
        List<Advisor> loadedAdvisors = DataLoader.loadAdvisors("advisors_test.json");
        Assertions.assertEquals(sampleAdvisors, loadedAdvisors);
    }

    @Test
    public void testWriteAndReadAdvisors() throws IOException {
        Advisor newAdvisor = new Advisor(UUID.randomUUID(), "Advisor3", "advisor3_lastName", "advisor3@school.edu", "password");
        sampleAdvisors.add(newAdvisor);

        DataWriter.writeDataToFile(sampleAdvisors, "advisors_test.json");
        List<Advisor> loadedAdvisors = DataLoader.loadAdvisors("advisors_test.json");

        Assertions.assertEquals(sampleAdvisors, loadedAdvisors);
    }
}