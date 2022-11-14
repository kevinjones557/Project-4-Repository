import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/** Because using pre-existing test case files was too mainstream!
 *  Custom Test Case class, which tests each meaningful different input for each static method
 * @author Destin Groves, CS180BLK
 * @version November 14, 2022
 */

class MetricManagerTest {
    // dear god these took a while

    @Test
    void addDeleteMessageDataAddTest() {
        Path testUserFolder = null;
        Path testUserStore = null;
        Path testUserMetrics = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUserStore = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        ArrayList<String> expectedContent = new ArrayList<>();
        expectedContent.add("Message Count: 2");
        expectedContent.add("2 Word");


        writeToFile(testUserMetrics.toFile(), fileContents);
        MetricManager.addDeleteMessageData("TempUser", null, "Word", false);
        assertEquals(readFile(testUserMetrics.toFile()), expectedContent);
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void addDeleteMessageDataDeleteTest() {
        Path testUserFolder = null;
        Path testUserStore = null;
        Path testUserMetrics = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUserStore = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        ArrayList<String> expectedContent = new ArrayList<>();
        expectedContent.add("Message Count: 0");
        expectedContent.add("0 Word");


        writeToFile(testUserMetrics.toFile(), fileContents);
        MetricManager.addDeleteMessageData("TempUser", null, "Word", true);
        assertEquals(readFile(testUserMetrics.toFile()), expectedContent);
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void editMessageDataTest() {
        Path testUserFolder = null;
        Path testUserStore = null;
        Path testUserMetrics = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUserStore = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        ArrayList<String> expectedContent = new ArrayList<>();
        expectedContent.add("Message Count: 1");
        expectedContent.add("0 Word");
        expectedContent.add("1 Words");


        writeToFile(testUserMetrics.toFile(), fileContents);
        MetricManager.editMessageData("TempUser", null, "Word", "Words");
        assertEquals(readFile(testUserMetrics.toFile()), expectedContent);
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void sellerMetricsUITest1() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path testUserFolder = null;
        Path testUserStore = null;
        Path testUserMetrics = null;
        Path testStoreMetrics = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUserStore = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));
            testStoreMetrics = Files.createFile(Path.of(testUserStore + "/metrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        String[] fileContents2 = {"Message Count: 2" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "1 The" + System.lineSeparator()};

        writeToFile(testUserMetrics.toFile(), fileContents);
        writeToFile(testStoreMetrics.toFile(), fileContents2);

        String input = "1\n1\n\n";

        String testResult = "Metrics Dashboard" + System.lineSeparator() +
                "1. View Store Metrics" + System.lineSeparator() +
                "2. View Stores Sorted" + System.lineSeparator() +
                "3. View Personal Metrics" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "Store Metrics" + System.lineSeparator() +
                "1. TempStore" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "TempStore's Metrics:" + System.lineSeparator() +
                "Message Count: 2" + System.lineSeparator() +
                "2 Word" + System.lineSeparator() +
                "1 The" + System.lineSeparator() +
                "Press Enter to return to the main menu." + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        LinkedHashMap<String, String> testStoreData = new LinkedHashMap<>();
        testStoreData.put("TempStore", "TempUser");


        MetricManager.sellerMetricsUI("TempUser", new Scanner(userInput), testStoreData);
        String output = out.toString();
        assertEquals(testResult.trim(), output.trim());

        testStoreMetrics.toFile().delete();
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void sellerMetricsUITest2() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

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

        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        String[] fileContents2 = {"Message Count: 2" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "1 The" + System.lineSeparator()};

        String[] fileContents3 = {"Message Count: 4" + System.lineSeparator(),
                "5 Test" + System.lineSeparator(),
                "3 Cases" + System.lineSeparator()};

        String[] fileContents4 = {"Message Count: 8" + System.lineSeparator(),
                "6 Test" + System.lineSeparator(),
                "3 Cases" + System.lineSeparator()};

        writeToFile(testUserMetrics.toFile(), fileContents);
        writeToFile(testStoreMetrics1.toFile(), fileContents2);
        writeToFile(testStoreMetrics2.toFile(), fileContents3);
        writeToFile(testStoreMetrics3.toFile(), fileContents4);

        String input = "2\n\n";

        String testResult = "Metrics Dashboard" + System.lineSeparator() +
                "1. View Store Metrics" + System.lineSeparator() +
                "2. View Stores Sorted" + System.lineSeparator() +
                "3. View Personal Metrics" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "List of your Stores, sorted by messages received." + System.lineSeparator() +
                "TempStore received 2 messages" + System.lineSeparator() +
                "TempStore2 received 4 messages" + System.lineSeparator() +
                "TempStore3 received 8 messages" + System.lineSeparator() +
                "Press Enter to return to the main menu." + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        LinkedHashMap<String, String> testStoreData = new LinkedHashMap<>();

        testStoreData.put("TempStore", "TempUser");
        testStoreData.put("TempStore3", "TempUser");
        testStoreData.put("TempStore2", "TempUser");

        MetricManager.sellerMetricsUI("TempUser", new Scanner(userInput), testStoreData);
        String output = out.toString();
        assertEquals(testResult.trim(), output.trim());

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
    void sellerMetricsUITest3() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path testUserFolder = null;
        Path testUserStore = null;
        Path testUserMetrics = null;
        Path testStoreMetrics = null;
        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUserStore = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));
            testStoreMetrics = Files.createFile(Path.of(testUserStore + "/metrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        String[] fileContents2 = {"Message Count: 2" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "1 The" + System.lineSeparator()};

        writeToFile(testUserMetrics.toFile(), fileContents);
        writeToFile(testStoreMetrics.toFile(), fileContents2);

        String input = "3\n\n";

        String testResult = "Metrics Dashboard" + System.lineSeparator() +
                "1. View Store Metrics" + System.lineSeparator() +
                "2. View Stores Sorted" + System.lineSeparator() +
                "3. View Personal Metrics" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "Your Metrics:" + System.lineSeparator() +
                "Message Count: 1" + System.lineSeparator() +
                "1 Word" + System.lineSeparator() +
                "Press Enter to return to the main menu." + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        LinkedHashMap<String, String> testStoreData = new LinkedHashMap<>();
        testStoreData.put("TempStore", "TempUser");


        MetricManager.sellerMetricsUI("TempUser", new Scanner(userInput), testStoreData);
        String output = out.toString();
        assertEquals(output.trim(), testResult.trim());

        testStoreMetrics.toFile().delete();
        testUserMetrics.toFile().delete();
        testUserStore.toFile().delete();
        testUserFolder.toFile().delete();
    }

    @Test
    void buyerMetricsUITest1() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Path testUserFolder = null;
        Path testUser1Store = null;
        Path testUser2Store = null;

        Path testUserMetrics = null;
        Path testStore1Metrics = null;
        Path testBuyerMetrics1 = null;
        Path testBuyerMetrics2 = null;

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempUser"));
            testUser1Store = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore"));
            testUser2Store = Files.createDirectory(Paths.get("data/sellers/TempUser/TempStore2"));
            testUserMetrics = Files.createFile(Path.of(testUserFolder + "/metrics.txt"));
            testStore1Metrics = Files.createFile(Path.of(testUser1Store + "/metrics.txt"));
            testBuyerMetrics1 = Files.createFile(Path.of(testUser1Store + "/TestBuyermetrics.txt"));
            testBuyerMetrics2 = Files.createFile(Path.of(testUser2Store + "/TestBuyermetrics.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        String[] fileContents2 = {"Message Count: 2" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "1 The" + System.lineSeparator()};

        String[] fileContents3 = {"Message Count: 5" + System.lineSeparator(),
                "7 who" + System.lineSeparator(),
                "1 cares" + System.lineSeparator()};

        String[] fileContents4 = {"Message Count: 7" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "2 Wordle" + System.lineSeparator()};

        writeToFile(testUserMetrics.toFile(), fileContents);
        writeToFile(testStore1Metrics.toFile(), fileContents2);
        writeToFile(testBuyerMetrics1.toFile(), fileContents3);
        writeToFile(testBuyerMetrics2.toFile(), fileContents4);

        String input = "1\n\n";

        String testResult = "Metrics Dashboard" + System.lineSeparator() +
                "1. View Your Store Conversation Data" + System.lineSeparator() +
                "2. View All Stores Sorted" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "List Stores you've messaged, sorted by your messages sent them." + System.lineSeparator() +
                "TempStore received 5 messages" + System.lineSeparator() +
                "TempStore2 received 7 messages" + System.lineSeparator() +
                "Press Enter to return to the main menu." + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        LinkedHashMap<String, String> testStoreData = new LinkedHashMap<>();
        testStoreData.put("TempStore", "TempUser");
        testStoreData.put("TempStore2", "TempUser");


        MetricManager.buyerMetricsUI("TestBuyer", new Scanner(userInput), testStoreData);
        String output = out.toString();
        assertEquals(testResult.trim(), output.trim());

        testStore1Metrics.toFile().delete();
        testBuyerMetrics1.toFile().delete();
        testBuyerMetrics2.toFile().delete();
        testUserMetrics.toFile().delete();
        testUser1Store.toFile().delete();
        testUser2Store.toFile().delete();
        testUserFolder.toFile().delete();

    }

    @Test
    void buyerMetricsUITest2() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

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

        String[] fileContents = {"Message Count: 1" + System.lineSeparator(),
                "1 Word" + System.lineSeparator()};

        String[] fileContents2 = {"Message Count: 2" + System.lineSeparator(),
                "2 Word" + System.lineSeparator(),
                "1 The" + System.lineSeparator()};

        String[] fileContents3 = {"Message Count: 4" + System.lineSeparator(),
                "5 Test" + System.lineSeparator(),
                "3 Cases" + System.lineSeparator()};

        String[] fileContents4 = {"Message Count: 8" + System.lineSeparator(),
                "6 Test" + System.lineSeparator(),
                "3 Cases" + System.lineSeparator()};

        writeToFile(testUserMetrics.toFile(), fileContents);
        writeToFile(testStoreMetrics1.toFile(), fileContents2);
        writeToFile(testStoreMetrics2.toFile(), fileContents3);
        writeToFile(testStoreMetrics3.toFile(), fileContents4);

        String input = "2\n\n";

        String testResult = "Metrics Dashboard" + System.lineSeparator() +
                "1. View Your Store Conversation Data" + System.lineSeparator() +
                "2. View All Stores Sorted" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator() +
                "List all Stores, sorted by messages received." + System.lineSeparator() +
                "TempStore received 2 messages" + System.lineSeparator() +
                "TempStore2 received 4 messages" + System.lineSeparator() +
                "TempStore3 received 8 messages" + System.lineSeparator() +
                "Press Enter to return to the main menu." + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        LinkedHashMap<String, String> testStoreData = new LinkedHashMap<>();

        testStoreData.put("TempStore", "TempUser");
        testStoreData.put("TempStore3", "TempUser");
        testStoreData.put("TempStore2", "TempUser");

        MetricManager.buyerMetricsUI("TestBuyer", new Scanner(userInput), testStoreData);
        String output = out.toString();
        assertEquals(testResult.trim(), output.trim());

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
    void displayMenuTest() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));


        String testHeader = "Header";
        String[] testOptions = {"Opt1", "Opt2", "Opt3"};
        String input = "0\n";
        String testResult = "Header" + System.lineSeparator() +
                "1. Opt1" + System.lineSeparator() +
                "2. Opt2" + System.lineSeparator() +
                "3. Opt3" + System.lineSeparator() +
                "0. Exit" + System.lineSeparator();

        InputStream userInput = new ByteArrayInputStream(input.getBytes());
        assertEquals(MetricManager.DisplayMenu(testHeader, testOptions, new Scanner(userInput)), 0);
        String output = out.toString();
        assertEquals(output.trim(), testResult.trim());
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