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

//test for major list
public class MajorListTest {

    private static MajorList majorList;

    @BeforeAll
    public static void setup() throws IOException {
        File majorsFile = new File("src/test/resources/majors_test.json");
        majorsFile.getParentFile().mkdirs();
        majorsFile.createNewFile();
    }

    /*
     * @BeforeEach
     * public void init() throws IOException {
     * List<Major> majors = Arrays.asList(
     * new Major(UUID.randomUUID(), "Computer Science", 100),
     * new Major(UUID.randomUUID(), "Mathematics", 50)
     * );
     * DataWriter.writeDataToFile(majors, "majors_test.json");
     * 
     * majorList = MajorList.getInstance();
     * }
     */
    @AfterEach
    public void cleanup() {
        File majorsFile = new File("src/test/resources/majors_test.json");
        majorsFile.delete();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(majorList);
    }

    @Test
    public void testGetMajors() {
        List<Major> majors = majorList.getMajors();
        assertTrue(majors.size() == 2);

        assertEquals(majors.get(0).getMajorID(), DataLoader.loadMajors("majors_test.json").get(0).getMajorID());
    }

    @Test
    public void testGetMajorByUUID() {
        Major major1 = majorList.getMajors().get(0);
        UUID id1 = major1.getMajorID();

        Major major2 = majorList.getMajorByUUID(id1);

        assertEquals(major1, major2);
    }

    @Test
    public void testGetMajorByName() {
        String name1 = majorList.getMajors().get(0).getMajorName();

        Major major1 = majorList.getMajorByName(name1);

        assertEquals(majorList.getMajors().get(0), major1);
    }

    @Test
    public void testLoadMajors() throws IOException {
        List<Major> majors = DataLoader.loadMajors("majors_test.json");

        assertEquals(majorList.getMajors().get(0).getMajorID(), majors.get(0).getMajorID());
        assertEquals(majorList.getMajors().get(1).getMajorID(), majors.get(1).getMajorID());
    }
}
