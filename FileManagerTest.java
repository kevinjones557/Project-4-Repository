import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/** Test Class for the FileManager class.
 *
 * @author Destin Groves
 * @version November 14th, 2022
 */
class FileManagerTest {

    @Test
    void getDirectoryFromUsernameSeller() {

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        String testResult = "data/sellers/TempUser/";

        try {
            assertEquals(FileManager.getDirectoryFromUsername(testUser), testResult);
        } catch (UserNotFoundException e) {
            System.out.println("The User couldn't be found! Try again.");
        }
        testUserFolder.toFile().delete();
    }

    @Test
    void getDirectoryFromUsernameBuyer() {

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        String testResult = "data/buyers/TempUser/";

        try {
            assertEquals(FileManager.getDirectoryFromUsername(testUser), testResult);
        } catch (UserNotFoundException e) {
            System.out.println("The User couldn't be found! Try again.");
        }
        testUserFolder.toFile().delete();
    }

    @Test
    void checkUserExistsTrue() {

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(true, FileManager.checkUserExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void checkUserExistsTrue2() {

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(true, FileManager.checkUserExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void checkUserExistsFalse() {

        Path testUserFolder = null;


        String testUser = "TempUser";
        assertEquals(false, FileManager.checkUserExists(testUser));

    }

    @Test
    void checkSellerExistsTrue() {
        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(true, FileManager.checkSellerExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void checkSellerExistsFalse() {
        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(false, FileManager.checkSellerExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void checkBuyerExistsTrue() {

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(true, FileManager.checkBuyerExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void checkBuyerExistsFalse() {
        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }


        String testUser = "TempUser";
        assertEquals(false, FileManager.checkBuyerExists(testUser));

        testUserFolder.toFile().delete();
    }

    @Test
    void getStoresFromSeller() {
        Path testUserFolder = null;

        Path testUserStore1 = null;
        Path testUserStore2 = null;
        Path testUserStore3 = null;

        Path testUserMetrics = null;
        Path testStoreMetrics1 = null;
        Path testStoreMetrics2 = null;
        Path testStoreMetrics3 = null;

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));

            testUserStore1 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserStore2 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore2"));
            testUserStore3 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore3"));

            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));

            testStoreMetrics1 = Files.createFile(Path.of(testUserStore1 + "/metrics.txt"));
            testStoreMetrics2 = Files.createFile(Path.of(testUserStore2 + "/metrics.txt"));
            testStoreMetrics3 = Files.createFile(Path.of(testUserStore3 + "/metrics.txt"));


        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String testUser = "TempUser";
        ArrayList<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("TempStore");
        expectedOutput.add("TempStore2");
        expectedOutput.add("TempStore3");

        assertEquals(expectedOutput, FileManager.getStoresFromSeller(testUser));

        testStoreMetrics1.toFile().delete();
        testStoreMetrics2.toFile().delete();
        testStoreMetrics3.toFile().delete();
        testUserMetrics.toFile().delete();
        testUserStore1.toFile().delete();
        testUserStore2.toFile().delete();
        testUserStore3.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void getStoreDirectory() {
        String testUser = "TestUser";
        String testStore = "TempStore";
        String expectedOutput = "data/sellers/TestUser/TempStore/";
        assertEquals(expectedOutput, FileManager.getStoreDirectory(testUser, testStore));
    }

    @Test
    void generateDirectoryFromUsernameSeller() {
        String testUser = "TempUser";
        boolean isSeller = false;
        FileManager.generateDirectoryFromUsername(testUser, isSeller);

        Path testUserFolder = Path.of("data/buyers/TempUser");
        Path testUserBlocked = Path.of("data/buyers/TempUser/hasBlocked.txt");
        Path testUserInvisible = Path.of("data/buyers/TempUser/isInvisible.txt");
        Path testUserMetrics = Path.of("data/buyers/TempUser/metrics.txt");


        assertEquals(true, Files.exists(testUserFolder));
        assertEquals(true, Files.exists(testUserBlocked));
        assertEquals(true, Files.exists(testUserInvisible));
        assertEquals(true, Files.exists(testUserMetrics));

        testUserBlocked.toFile().delete();
        testUserInvisible.toFile().delete();
        testUserMetrics.toFile().delete();

        testUserFolder.toFile().delete();

    }

    @Test
    void generateStoreForSeller() {
        String testUser = "TempUser";
        String testStore = "TempStore";

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        FileManager.generateStoreForSeller(testUser, testStore);

        Path testUserStore = Path.of("data/sellers/TempUser/TempStore");
        Path testUserMetrics = Path.of("data/sellers/TempUser/TempStore/metrics.txt");


        assertEquals(true, Files.exists(testUserFolder));
        assertEquals(true, Files.exists(testUserStore));
        assertEquals(true, Files.exists(testUserMetrics));

        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();

    }

    @Test
    void generateMetricsAboutUser() {

        String testUser = "TempUser";
        String testStore = "TempStore";
        String testBuyer = "TestBuyer";

        Path testUserFolder = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        FileManager.generateStoreForSeller(testUser, testStore);
        FileManager.generateMetricsAboutUser(testBuyer, "data/sellers/TempUser/TempStore");

        Path testUserStore = Path.of("data/sellers/TempUser/TempStore");
        Path testUserMetrics = Path.of("data/sellers/TempUser/TempStore/metrics.txt");
        Path testBuyerMetrics = Path.of("data/sellers/TempUser/TempStore/TestBuyermetrics.txt");


        assertEquals(true, Files.exists(testBuyerMetrics));

        testBuyerMetrics.toFile().delete();
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();

    }

    @Test
    void mapStoresToSellers() {
        System.out.println("Remember to run this test with an empty sellers folder! Otherwise the test will fail.");
        Path testUserFolder = null;

        Path testUserStore1 = null;
        Path testUserStore2 = null;
        Path testUserStore3 = null;

        Path testUserMetrics = null;
        Path testStoreMetrics1 = null;
        Path testStoreMetrics2 = null;
        Path testStoreMetrics3 = null;

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));

            testUserStore1 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserStore2 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore2"));
            testUserStore3 = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore3"));

            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));

            testStoreMetrics1 = Files.createFile(Path.of(testUserStore1 + "/metrics.txt"));
            testStoreMetrics2 = Files.createFile(Path.of(testUserStore2 + "/metrics.txt"));
            testStoreMetrics3 = Files.createFile(Path.of(testUserStore3 + "/metrics.txt"));


        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        LinkedHashMap<String, String> expectedOutput = new LinkedHashMap<>();
        expectedOutput.put("TempStore", "TempUser");
        expectedOutput.put("TempStore2", "TempUser");
        expectedOutput.put("TempStore3", "TempUser");
        LinkedHashMap<String, String> output = FileManager.mapStoresToSellers();

        expectedOutput.forEach((expectedStore, expectedUser) -> {
            assertEquals(expectedUser, output.get(expectedStore));
        });


        testStoreMetrics1.toFile().delete();
        testStoreMetrics2.toFile().delete();
        testStoreMetrics3.toFile().delete();
        testUserMetrics.toFile().delete();
        testUserStore1.toFile().delete();
        testUserStore2.toFile().delete();
        testUserStore3.toFile().delete();
        testUserFolder.toFile().delete();
    }

    ArrayList<String> readFile(File file) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                line = bufferedReader.readLine();
            }
            return list;
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
            return null;
        }
    }

    boolean writeToFile(File file, String[] fileContents) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : fileContents) {
                bufferedWriter.write(line);
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while creating the test file");
            return false;
        }
    }
}