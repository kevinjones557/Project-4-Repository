import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MarketUserTest {
    @Test
    void waitForValidInput() {
        String input = 5 + System.lineSeparator() + "h" + System.lineSeparator() + 3 + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scan = new Scanner(inputStream);

        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        MarketUser mu = new MarketUser("testBuyer", false);
        mu.waitForValidInput(1, 4, scan);

        String expectedOutput = "Please enter a valid number:" + System.lineSeparator() +
                "Please enter a valid number:" + System.lineSeparator();

    }

    @Test
    void mainForSeller() {
        FileManager.generateDirectoryFromUsername("TempBuyer", false);
        FileManager.generateDirectoryFromUsername("TempSeller", true);
        FileManager.generateStoreForSeller("TempSeller", "Store");


        String input = 1 + System.lineSeparator() + 5 + System.lineSeparator() + 2 + System.lineSeparator() +
                2  + System.lineSeparator() + 1 + System.lineSeparator() + 3 + System.lineSeparator() +
                1 + System.lineSeparator() + 1 + System.lineSeparator() + "asdf" + System.lineSeparator() +
                2 + System.lineSeparator() + 1 + System.lineSeparator() + 7 + System.lineSeparator() +
                2 + System.lineSeparator() + 1 + System.lineSeparator() + 1 + System.lineSeparator() +
                "TempBuyer" + System.lineSeparator() + 7 + System.lineSeparator() + 1 + System.lineSeparator() +
                3 + System.lineSeparator() + 4 + System.lineSeparator();

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scan = new Scanner(inputStream);

        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        MarketUser mu = new MarketUser("TempSeller", true);
        mu.mainForSeller(scan);

        String expectedOutput = "Please select an option below:" + System.lineSeparator() +
                "1. Use block/invisible features" + System.lineSeparator() +
                "2. Continue to messaging" + System.lineSeparator() +
                "3. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Block a user" + System.lineSeparator() +
                "2. Unblock a user" + System.lineSeparator() +
                "3. Become invisible to a user" + System.lineSeparator() +
                "4. Become visible again" + System.lineSeparator() +
                "5. Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Use block/invisible features" + System.lineSeparator() +
                "2. Continue to messaging" + System.lineSeparator() +
                "3. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Message with personal account" + System.lineSeparator() +
                "2. Message with store account" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "1. Store" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a buyer" + System.lineSeparator() +
                "2. View a list of buyers" + System.lineSeparator() +
                "3. Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Message with personal account" + System.lineSeparator() +
                "2. Message with store account" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a buyer" + System.lineSeparator() +
                "2. View a list of buyers" + System.lineSeparator() +
                "3. Cancel" + System.lineSeparator() +

                "Please enter the name of a buyer:" + System.lineSeparator() +

                "Sorry, this buyer does not exist!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a buyer" + System.lineSeparator() +
                "2. View a list of buyers" + System.lineSeparator() +
                "3. Cancel" + System.lineSeparator() +

                "1. TempBuyer" + System.lineSeparator() +
                "2: Cancel" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +

                "Connected with TempBuyer!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. View Message Contents" + System.lineSeparator() +
                "2. Send a message" + System.lineSeparator() +
                "3. Edit a message" + System.lineSeparator() +
                "4. Delete a message" + System.lineSeparator() +
                "5. Import a .txt file" + System.lineSeparator() +
                "6. Export message history as a CSV file" + System.lineSeparator() +
                "7.Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Continue with current account" + System.lineSeparator() +
                "2. Switch accounts" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Message with personal account" + System.lineSeparator() +
                "2. Message with store account" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a buyer" + System.lineSeparator() +
                "2. View a list of buyers" + System.lineSeparator() +
                "3. Cancel" + System.lineSeparator() +

                "Please enter the name of a buyer:" + System.lineSeparator() +

                "Connected with TempBuyer!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. View Message Contents" + System.lineSeparator() +
                "2. Send a message" + System.lineSeparator() +
                "3. Edit a message" + System.lineSeparator() +
                "4. Delete a message" + System.lineSeparator() +
                "5. Import a .txt file" + System.lineSeparator() +
                "6. Export message history as a CSV file" + System.lineSeparator() +
                "7.Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Continue with current account" + System.lineSeparator() +
                "2. Switch accounts" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a buyer" + System.lineSeparator() +
                "2. View a list of buyers" + System.lineSeparator() +
                "3. Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Message with personal account" + System.lineSeparator() +
                "2. Message with store account" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator();

        assertEquals(expectedOutput.trim().replace("\r",""),
                os.toString().trim().replace("\r",""));

        MarketUser.deleteUsername("TempBuyer");
        MarketUser.deleteUsername("TempSeller");
    }

    @Test
    void mainForBuyer() {
        FileManager.generateDirectoryFromUsername("TempBuyer", false);
        FileManager.generateDirectoryFromUsername("TempSeller", true);
        FileManager.generateStoreForSeller("TempSeller", "Store");


        String input = 1 + System.lineSeparator() + 5 + System.lineSeparator() + 2 + System.lineSeparator() +
                1  + System.lineSeparator() + "asdf" + System.lineSeparator() + 2 + System.lineSeparator() +
                1 + System.lineSeparator() + 7 + System.lineSeparator() + 1 + System.lineSeparator() +
                "TempSeller" + System.lineSeparator() + 7 + System.lineSeparator() + 4 + System.lineSeparator();

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scan = new Scanner(inputStream);

        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        MarketUser mu = new MarketUser("TempBuyer", false);
        mu.mainForBuyer(scan);

        String expectedOutput = "Please select an option below:" + System.lineSeparator() +
                "1. Use block/invisible features" + System.lineSeparator() +
                "2. Continue to messaging" + System.lineSeparator() +
                "3. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Block a user" + System.lineSeparator() +
                "2. Unblock a user" + System.lineSeparator() +
                "3. Become invisible to a user" + System.lineSeparator() +
                "4. Become visible again" + System.lineSeparator() +
                "5. Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Use block/invisible features" + System.lineSeparator() +
                "2. Continue to messaging" + System.lineSeparator() +
                "3. Exit Messaging System" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a seller" + System.lineSeparator() +
                "2. View a list of stores" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +


                "Please enter the name of a seller:" + System.lineSeparator() +

                "Sorry, this seller does not exist!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a seller" + System.lineSeparator() +
                "2. View a list of stores" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "1. Store" + System.lineSeparator() +
                "2: Cancel" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +

                "Connected with Store!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. View Message Contents" + System.lineSeparator() +
                "2. Send a message" + System.lineSeparator() +
                "3. Edit a message" + System.lineSeparator() +
                "4. Delete a message" + System.lineSeparator() +
                "5. Import a .txt file" + System.lineSeparator() +
                "6. Export message history as a CSV file" + System.lineSeparator() +
                "7.Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a seller" + System.lineSeparator() +
                "2. View a list of stores" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator() +

                "Please enter the name of a seller:" + System.lineSeparator() +

                "Connected with TempSeller!" + System.lineSeparator() +
                "Please select an option below:" + System.lineSeparator() +
                "1. View Message Contents" + System.lineSeparator() +
                "2. Send a message" + System.lineSeparator() +
                "3. Edit a message" + System.lineSeparator() +
                "4. Delete a message" + System.lineSeparator() +
                "5. Import a .txt file" + System.lineSeparator() +
                "6. Export message history as a CSV file" + System.lineSeparator() +
                "7.Cancel" + System.lineSeparator() +

                "Please select an option below:" + System.lineSeparator() +
                "1. Search for a seller" + System.lineSeparator() +
                "2. View a list of stores" + System.lineSeparator() +
                "3. Continue to statistics" + System.lineSeparator() +
                "4. Exit Messaging System" + System.lineSeparator();

        assertEquals(expectedOutput.trim().replace("\r",""),
                os.toString().trim().replace("\r",""));

        MarketUser.deleteUsername("TempBuyer");
        MarketUser.deleteUsername("TempSeller");
    }

    @Test
    void deleteUsername() {
        Path testUserFolder = null;
        Path testUserMessage = null;

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testUserMessage = Files.createFile(Path.of("data/buyers/TempUser/convo.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String username = "TempUser";

        MarketUser.deleteUsername(username);

        assertEquals(false, Files.exists(testUserFolder));
    }

    @Test
    void changeUsername() {
        try {
            Path testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempBuyer"));
        } catch (IOException e) {
            System.out.println("Could not create folder");
        }

        String expected = "NewBuyer";
        String actual;

        MarketUser.changeUsername("TempBuyer", "NewBuyer");

        File newFile = new File("data/buyers/NewBuyer");
        if (newFile.exists()) {
            actual = "NewBuyer";
        } else {
            actual = "";
        }
        Assert.assertEquals(expected, actual);
        try {
            Files.delete(Paths.get("data/buyers/NewBuyer"));
        } catch (IOException e) {
            System.out.println("Unable to delete file and folder");
        }
    }

    @Test
    void changeStoreName() {
        try {
            Path testUserFolder = Files.createDirectory(Paths.get("data/sellers/TempSeller"));
            Path testStoreFolder = Files.createDirectory(Paths.get("data/sellers/TempSeller/Store"));
            Files.createFile(Paths.get("data/sellers/TempSeller/Store/StoreBuyer.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create folder");
        }

        ArrayList<String> expected = new ArrayList<>();
        expected.add("NewStore");
        expected.add("NewStoreBuyer.txt");
        ArrayList<String> actual = new ArrayList<>();

        MarketUser.changeStoreName("Store", "NewStore");

        File newFile = new File("data/sellers/TempSeller/NewStore");
        if (newFile.exists()) {
            actual.add("NewStore");
        } else {
            actual.add("");
        }

        String[] storeFile = newFile.list();
        actual.add(storeFile[0]);

        Assert.assertEquals(expected, actual);
        try {
            Files.delete(Paths.get("data/sellers/TempSeller/NewStore/NewStoreBuyer.txt"));
            Files.delete(Paths.get("data/sellers/TempSeller/NewStore"));
            Files.delete(Paths.get("data/sellers/TempSeller"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to delete file and folder");
        }
    }

    @Test
    void changeNameInFile() {
        try {
            File buyerFile = new File("data/buyers/TempBuyerTempSeller.txt");
            buyerFile.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(buyerFile, true));
            pw.write("TempBuyer 11/14 08:52:56- hello there");
            pw.close();

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        MarketUser.changeNameInFile("TempBuyer", "NewBuyer",
                "data/buyers/TempBuyerTempSeller.txt");

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("NewBuyer 11/14 08:52:56- hello there");

        File csvFile = new File("data/buyers/TempBuyerTempSeller.txt");
        ArrayList<String> actualResult = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(csvFile))) {
            actualResult = new ArrayList<>();
            String line = bfr.readLine();
            while (line != null) {
                actualResult.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e){
            System.out.println("Could not read file");
        }

        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(expectedResult.get(i), actualResult.get(i));
        }

        try {
            Files.delete(Paths.get("data/buyers/TempBuyerTempSeller.txt"));
        } catch (IOException e) {
            System.out.println("Unable to delete file and folder");
        }
    }

    @Test
    void writeCSV() {
        Path testUserFolder = null;

        MarketUser mu = new MarketUser("TempBuyer", false);

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempBuyer"));
            File buyerFile = new File(testUserFolder + "/TempBuyerTempSeller.txt");
            buyerFile.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(buyerFile, true));
            pw.write("TempBuyer 11/14 08:52:56- hello there");
            pw.close();

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        mu.writeCSV("TempSeller", "data");

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("Name:,Date:,Time:,Message:");
        expectedResult.add("TempBuyer,11/14,08:52:56,hello there");

        File csvFile = new File("data/TempBuyer.csv");
        ArrayList<String> actualResult = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new FileReader(csvFile))) {
            actualResult = new ArrayList<>();
            String line = bfr.readLine();
            while (line != null) {
                actualResult.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e){
            System.out.println("Could not read csv");
        }

        Assert.assertEquals(expectedResult, actualResult);

        try {
            Files.delete(Paths.get(testUserFolder + "/TempBuyerTempSeller.txt"));
            Files.delete(Paths.get("data/buyers/TempBuyer"));
            Files.delete(Paths.get("data/TempBuyer.csv"));
        } catch (IOException e) {
            System.out.println("Unable to delete file and folder");
        }
    }

    @Test
    void getAvailableUsers() {
    }

    @Test
    void getMessage_ableUser() {
    }

    @Test
    void getAvailableStores() {
    }

    @Test
    void getMessage_ableStores() {
    }

    @Test
    void checkIfMessageExists() {
        try {
            Files.createDirectory(Paths.get("data/buyers/TempBuyer"));
            Files.createDirectory(Paths.get("data/sellers/TempSeller"));
            Files.createDirectory(Paths.get("data/sellers/TempSeller/Store"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        MarketUser mu = new MarketUser("TempBuyer", false);
        mu.checkIfMessageExists("TempSeller", false);

        String expected = "truetruetruetrue";
        String actual = "";
        actual += (Files.exists(Paths.get("data/buyers/TempBuyer/TempBuyerTempSeller.txt")));
        actual += (Files.exists(Paths.get("data/sellers/TempSeller/TempSellerTempBuyer.txt")));

        mu.checkIfMessageExists("Store", true);
        actual += (Files.exists(Paths.get("data/buyers/TempBuyer/TempBuyerStore.txt")));
        actual += (Files.exists(Paths.get("data/sellers/TempSeller/Store/StoreTempBuyer.txt")));

        Assert.assertEquals(expected, actual);

        try {
            Files.delete(Paths.get("data/buyers/TempBuyer/TempBuyerTempSeller.txt"));
            Files.delete(Paths.get("data/sellers/TempSeller/TempSellerTempBuyer.txt"));
            Files.delete(Paths.get("data/buyers/TempBuyer/TempBuyerStore.txt"));
            Files.delete(Paths.get("data/sellers/TempSeller/Store/StoreTempBuyer.txt"));
            Files.delete(Paths.get("data/sellers/TempSeller/Store/TempBuyermetrics.txt"));

            Files.delete(Paths.get("data/sellers/TempSeller/Store"));
            Files.delete(Paths.get("data/sellers/TempSeller"));
            Files.delete(Paths.get("data/buyers/TempBuyer"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to delete file and folder");
        }
    }

    /**
     * This tests if append message chooses the right file paths
     * and if append message execute writes anything
     * as a result
     *
     * @author John Brooks
     */
    @Test
    void appendMessage() {
        MarketUser testSend = new MarketUser("TempUser", false);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //calling append message and giving it the input for execute
        String input = "Hello" + System.lineSeparator();
        InputStream userInput = new ByteArrayInputStream(input.getBytes());
        testSend.appendMessage("OtherUser", new Scanner(userInput));

        //reading file that is supposed to have some message
        String line1 = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }

        //checking if anything is there
        assertEquals(line1 != null, true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        File f4 = new File("data/buyers/TempUser/");
        File f5 = new File("data/sellers/OtherUser/");
        f4.delete();
        f5.delete();
    }

    /**
     * This tests if append message chooses the right file paths
     * and if append message execute writes anything in the case that a store is
     * involved instead of a seller only
     *
     * @author John Brooks
     */
    @Test
    void testAppendMessageWithStore() {
        MarketUser testSend = new MarketUser("TempUser", false);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveStoreFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;
        Path storeMetrics = null;
        Path buyStoreMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherStore.txt"));
            testReceiveFolder = Files.createDirectory(Path.of("data/sellers/OtherUser"));
            testReceiveStoreFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser/OtherStore"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
            storeMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/metrics.txt"));
            buyStoreMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric1 = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric1, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric2 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric2, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric3 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric3, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //calling append message with overloaded store parameters
        String input = "Hello" + System.lineSeparator();
        InputStream userInput = new ByteArrayInputStream(input.getBytes());
        testSend.appendMessage("OtherUser", "OtherStore", new Scanner(userInput));

        //reading message file
        String line1 = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherStore.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //asserting that the line read is something and that the message went through
        assertEquals((line1 != null), true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherStore.txt");
        File f2 = new File("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        File f4 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
        File f5 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
        f5.delete();
        File f6 = new File("data/buyers/TempUser/");
        File f7 = new File("data/sellers/OtherUser/OtherStore/");
        File f8 = new File("data/sellers/OtherUser/");
        f6.delete();
        f7.delete();
        f8.delete();
    }

    @Test
    void displayMessage() {
        Path testUserFolder = null;

        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        MarketUser mu = new MarketUser("TempBuyer", false);

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempBuyer"));
            File buyerFile = new File(testUserFolder + "/TempBuyerTempSeller.txt");
            buyerFile.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(buyerFile, true));
            pw.println("TempBuyer 11/14 08:52:56- hello there");
            pw.println("TempBuyer 11/14 08:52:56- another message");
            pw.println("TempBuyer 11/14 08:52:56- one last one");
            pw.close();
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        mu.displayMessage("TempSeller");

        String expectedResult = "TempBuyer 11/14 08:52:56- hello there" + System.lineSeparator() +
                "TempBuyer 11/14 08:52:56- another message" + System.lineSeparator() +
                "TempBuyer 11/14 08:52:56- one last one" + System.lineSeparator();

        Assert.assertEquals(expectedResult, os.toString());

        try {
            Files.delete(Paths.get(testUserFolder + "/TempBuyerTempSeller.txt"));
            Files.delete(Paths.get("data/buyers/TempBuyer"));
        } catch (IOException e) {
            System.out.println("Unable to delete file and folder");
        }
    }

    /**
     * This tests if append message execute writes inputted messages
     * correctly to both files and verifies that they are the same in
     * both files and that the timestamp was written in correctly
     *
     * @author John Brooks
     */
    @Test
    void appendMessageExecute() {
        MarketUser testSend = new MarketUser("TempUser", false);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //input message
        String input = "Hello" + System.lineSeparator();
        InputStream userInput = new ByteArrayInputStream(input.getBytes());

        //creates partial timestamp for later test and runs method with message input
        String timeStamp = new SimpleDateFormat(
                "MM/dd ").format(new java.util.Date());
        testSend.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput));

        //declaring lines as random letters to ensure no null risk
        String line1 = "jklmn";
        String line2 = "abcde";

        // both tries read one of the files that was supposed to be written to and adds them to array lists
        ArrayList<String> buyerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
            while (line1 != null) {
                buyerFile.add(line1);
                line1 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        ArrayList<String> sellerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherUserTempUser.txt"))) {
            line2 = bufferedReader.readLine();
            while (line2 != null) {
                sellerFile.add(line2);
                line2 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //isolates last line of both files to get message sent
        String lastLine1 = buyerFile.get(buyerFile.size() - 1);
        String lastLine2 = sellerFile.get(sellerFile.size() - 1);

        //each expect is the final line of each file indexed to after the timestamp to see the actual message
        String expect1 = lastLine1.substring(lastLine1.indexOf("-") + 2);
        String expect2 = lastLine2.substring(lastLine2.indexOf("-") + 2);

        //seeing if both indexed message are equal to the input
        assertEquals(expect1, "Hello");
        assertEquals(expect2, "Hello");
        //seeing if they both last lines equal each other
        assertEquals(lastLine1, lastLine2);

        //indexes each last line before dash to test timestamp
        String expectStamp1 = lastLine1.substring(0, lastLine1.indexOf("-"));
        String expectStamp2 = lastLine2.substring(0, lastLine2.indexOf("-"));
        //ensures timestamp exists in both of those lines
        boolean indexTrue1 = false;
        if (expectStamp1.indexOf(timeStamp) > -1){
            indexTrue1 = true;
        }
        boolean indexTrue2 = false;
        if (expectStamp2.indexOf(timeStamp) > -1){
            indexTrue2 = true;
        }
        //assertEquals to test that
        assertEquals(indexTrue1, true);
        assertEquals(indexTrue2, true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        File f4 = new File("data/buyers/TempUser/");
        File f5 = new File("data/sellers/OtherUser/");
        f4.delete();
        f5.delete();
    }

    /**
     * This tests if edit message chooses the right file paths
     * and if edit message execute edits anything
     * as a result
     *
     * @author John Brooks
     */
    @Test
    void editMessage() {
        MarketUser testSend = new MarketUser("TempUser", false);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //creating message to edit
        String input = "Hello" + System.lineSeparator();
        InputStream userInput = new ByteArrayInputStream(input.getBytes());
        testSend.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput));

        //providing editing input and calling editMessage
        String inputEdit = "1" + System.lineSeparator()
                + "Hi" + System.lineSeparator();
        InputStream userInputForEdit = new ByteArrayInputStream(inputEdit.getBytes());
        testSend.editMessage("OtherUser", new Scanner(userInputForEdit));

        //reading file
        String line1 = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //checking if the line was edited
        assertEquals(line1 != null, true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        File f4 = new File("data/buyers/TempUser/");
        File f5 = new File("data/sellers/OtherUser/");
        f4.delete();
        f5.delete();
    }

    /**
     * This tests if edit message chooses the right file paths
     * and if edit message execute edits anything
     * as a result in the case that a store is involved
     * and not just a seller
     *
     * @author John Brooks
     */
    @Test
    void testEditMessageWithStore() {
        MarketUser testSend = new MarketUser("TempUser", false);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveStoreFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;
        Path storeMetrics = null;
        Path buyStoreMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherStore.txt"));
            testReceiveFolder = Files.createDirectory(Path.of("data/sellers/OtherUser"));
            testReceiveStoreFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser/OtherStore"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
            storeMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/metrics.txt"));
            buyStoreMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric1 = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric1, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric2 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric2, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric3 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric3, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //writing message to be edited
        String input = "Hello" + System.lineSeparator();
        InputStream userInput1 = new ByteArrayInputStream(input.getBytes());
        testSend.appendMessageExecute("OtherStore", "data/buyers/TempUser/", "data/sellers/OtherUser/OtherStore/", new Scanner(userInput1));

        //calling edit message with overloaded store parameters
        String inputForEdit = "1" + System.lineSeparator()
                + "Hi" + System.lineSeparator();
        InputStream userInput2 = new ByteArrayInputStream(inputForEdit.getBytes());
        testSend.editMessage("OtherUser", "OtherStore", new Scanner(userInput2));

        //reading file
        String line1 = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherStore.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //checking to see that it was edited
        line1 = line1.substring(line1.indexOf("-") + 2);
        assertEquals(line1, "Hi");

        File f1 = new File("data/buyers/TempUser/TempUserOtherStore.txt");
        File f2 = new File("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        File f4 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
        File f5 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
        f5.delete();
        File f6 = new File("data/buyers/TempUser/");
        File f7 = new File("data/sellers/OtherUser/OtherStore/");
        File f8 = new File("data/sellers/OtherUser/");
        f6.delete();
        f7.delete();
        f8.delete();
    }

    /**
     * This tests edit message's execution and if it
     * correctly edits each file, if it edits them to the same thing,
     * and if it does so even if the files do not have the exact same
     * contents in the case of deletion
     *
     * @author John Brooks
     */
    @Test
    void editMessageExecute() {
        MarketUser testTempBuy = new MarketUser("TempUser", false);
        MarketUser testOtherUse = new MarketUser("OtherUser", true);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;
        Path otherTestMetrics = null;

        //creating the temporary message files that the edit method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
            otherTestMetrics = Files.createFile(Path.of("data/sellers/OtherUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric1 = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric1 = new FileOutputStream(metric1, true);
            PrintWriter writeMetricLine1 = new PrintWriter(outToMetric1);
            writeMetricLine1.println("Message Count: 0");
            writeMetricLine1.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric2 = new File("data/sellers/OtherUser/metrics.txt");
            FileOutputStream outToMetric2 = new FileOutputStream(metric2, true);
            PrintWriter writeMetricLine2 = new PrintWriter(outToMetric2);
            writeMetricLine2.println("Message Count: 0");
            writeMetricLine2.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //creates messages for the later editing
        String input1 = "Hello" + System.lineSeparator();
        InputStream userInput1 = new ByteArrayInputStream(input1.getBytes());
        testTempBuy.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput1));
        String input2 = "How are you?" + System.lineSeparator();
        InputStream userInput2 = new ByteArrayInputStream(input2.getBytes());
        testTempBuy.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput2));
        String input3 = "I am good" + System.lineSeparator();
        InputStream userInput3 = new ByteArrayInputStream(input3.getBytes());
        testOtherUse.appendMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(userInput3));
        String input4 = "How about you?" + System.lineSeparator();
        InputStream userInput4 = new ByteArrayInputStream(input4.getBytes());
        testOtherUse.appendMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(userInput4));

        //calling delete to make sure the edit works even when files are not the same
        String inputDelete = "1" + System.lineSeparator();
        InputStream deleteStream = new ByteArrayInputStream(inputDelete.getBytes());
        testOtherUse.deleteMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(deleteStream));

        //calling edit method to change
        String inputEdit = "11" + System.lineSeparator()
                + "3" + System.lineSeparator()
                + "I am great!" + System.lineSeparator();
        InputStream userInputForEdit = new ByteArrayInputStream(inputEdit.getBytes());
        testTempBuy.editMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInputForEdit));

        //declaring lines as random letters to ensure no null risk
        String line1 = "jklmn";
        String line2 = "abcde";

        // both of these "tries" read one of the files that was supposed to be edited to and adds them to array lists
        ArrayList<String> buyerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
            while (line1 != null) {
                buyerFile.add(line1);
                line1 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        ArrayList<String> sellerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherUserTempUser.txt"))) {
            line2 = bufferedReader.readLine();
            while (line2 != null) {
                sellerFile.add(line2);
                line2 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }

        //grabs lines that should have been edited
        String editedLine1 = buyerFile.get(2);
        String editedLine2 = sellerFile.get(1);
        //shaves them into the isolated message
        String editedMessage1 = editedLine1.substring(editedLine1.indexOf("-") + 2);
        String editedMessage2 = editedLine2.substring(editedLine2.indexOf("-") + 2);

        //assertEquals for messages themselves that changed
        assertEquals(editedMessage1, "I am great!");
        assertEquals(editedMessage2, "I am great!");

        //assertEquals to make sure other parts of message are still the same on each end
        assertEquals(editedLine1, editedLine2);


        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        File f4 = new File("data/sellers/OtherUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
        File f5 = new File("data/buyers/TempUser/");
        File f6 = new File("data/sellers/OtherUser/");
        f5.delete();
        f6.delete();
    }

    /**
     * This tests if delete message chooses the right file paths
     * and if delete message execute deletes anything
     * as a result
     *
     * @author John Brooks
     */
    @Test
    void deleteMessage() {
        MarketUser testTempBuy = new MarketUser("TempUser", false);
        MarketUser testOtherUse = new MarketUser("OtherUser", true);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //writing message to be deleted
        String input = "Hello" + System.lineSeparator();
        InputStream userInput = new ByteArrayInputStream(input.getBytes());
        testTempBuy.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput));

        //giving input to delete
        String inputDelete = "1" + System.lineSeparator();
        InputStream userInputDelete = new ByteArrayInputStream(inputDelete.getBytes());
        testOtherUse.deleteMessage("TempUser", new Scanner(userInputDelete));

        //reading file
        String line1 = "abcd";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherUserTempUser.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //checking that line1 is now null because of deletion
        assertEquals((line1 == null), true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        File f4 = new File("data/buyers/TempUser/");
        File f5 = new File("data/sellers/OtherUser/");
        f4.delete();
        f5.delete();
    }

    /**
     * This tests if delete message chooses the right file paths
     * and if delete message execute deletes anything
     * as a result when a store is involved
     *
     * @author John Brooks
     */
    @Test
    void testDeleteMessageWithStore() {
        MarketUser testTempBuy = new MarketUser("TempUser", false);
        MarketUser testOtherUse = new MarketUser("OtherStore", true);
        testOtherUse.setIsUserStore(true);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveStoreFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;
        Path storeMetrics = null;
        Path buyStoreMetrics = null;

        //creating the temporary message files that the append method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherStore.txt"));
            testReceiveFolder = Files.createDirectory(Path.of("data/sellers/OtherUser"));
            testReceiveStoreFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser/OtherStore"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
            storeMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/metrics.txt"));
            buyStoreMetrics = Files.createFile(Path.of("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric1 = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric1, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric2 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric2, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric3 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
            FileOutputStream outToMetric = new FileOutputStream(metric3, true);
            PrintWriter writeMetricLine = new PrintWriter(outToMetric);
            writeMetricLine.println("Message Count: 0");
            writeMetricLine.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //writing message to edit
        String input = "Hello" + System.lineSeparator();
        InputStream userInput1 = new ByteArrayInputStream(input.getBytes());
        testTempBuy.appendMessageExecute("OtherStore", "data/buyers/TempUser/", "data/sellers/OtherUser/OtherStore/", new Scanner(userInput1));

        //calling delete message with overloaded store parameters
        String inputForDelete = "1" + System.lineSeparator();
        InputStream userInput2 = new ByteArrayInputStream(inputForDelete.getBytes());
        testOtherUse.deleteMessage("OtherUser", "TempUser", new Scanner(userInput2));

        //reading file
        String line1 = "abcd";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt"))) {
            line1 = bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        //verifying that line is now null because of deletion
        assertEquals((line1 == null), true);

        File f1 = new File("data/buyers/TempUser/TempUserOtherStore.txt");
        File f2 = new File("data/sellers/OtherUser/OtherStore/OtherStoreTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        File f4 = new File("data/sellers/OtherUser/OtherStore/TempUsermetrics.txt");
        File f5 = new File("data/sellers/OtherUser/OtherStore/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
        f5.delete();
        File f6 = new File("data/buyers/TempUser/");
        File f7 = new File("data/sellers/OtherUser/OtherStore/");
        File f8 = new File("data/sellers/OtherUser/");
        f6.delete();
        f7.delete();
        f8.delete();
    }

    /**
     * This tests if delete message execute deletes the correct message
     * and only deletes it in the file of the active user
     *
     * @author John Brooks
     */
    @Test
    void deleteMessageExecute() {
        MarketUser testTempBuy = new MarketUser("TempUser", false);
        MarketUser testOtherUse = new MarketUser("OtherUser", true);

        Path testSenderFolder = null;
        Path testSenderFile = null;
        Path testReceiveFolder = null;
        Path testReceiveFile = null;
        Path testerMetrics = null;
        Path otherTestMetrics = null;

        //creating the temporary message files that the edit method will access to modify
        try {
            testSenderFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testSenderFile = Files.createFile(Path.of("data/buyers/TempUser/TempUserOtherUser.txt"));
            testReceiveFolder = Files.createDirectory(Paths.get("data/sellers/OtherUser"));
            testReceiveFile = Files.createFile(Path.of("data/sellers/OtherUser/OtherUserTempUser.txt"));
            testerMetrics = Files.createFile(Path.of("data/buyers/TempUser/metrics.txt"));
            otherTestMetrics = Files.createFile(Path.of("data/sellers/OtherUser/metrics.txt"));
        } catch (IOException e) {
            System.out.println("Could not create folders");
        }
        try {
            File metric1 = new File("data/buyers/TempUser/metrics.txt");
            FileOutputStream outToMetric1 = new FileOutputStream(metric1, true);
            PrintWriter writeMetricLine1 = new PrintWriter(outToMetric1);
            writeMetricLine1.println("Message Count: 0");
            writeMetricLine1.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }
        try {
            File metric2 = new File("data/sellers/OtherUser/metrics.txt");
            FileOutputStream outToMetric2 = new FileOutputStream(metric2, true);
            PrintWriter writeMetricLine2 = new PrintWriter(outToMetric2);
            writeMetricLine2.println("Message Count: 0");
            writeMetricLine2.close();
        } catch (IOException e) {
            System.out.println("File did not write correctly.");
        }

        //creates messages for deleting later
        String input1 = "Hello" + System.lineSeparator();
        InputStream userInput1 = new ByteArrayInputStream(input1.getBytes());
        testTempBuy.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput1));
        String input2 = "How are you?" + System.lineSeparator();
        InputStream userInput2 = new ByteArrayInputStream(input2.getBytes());
        testTempBuy.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput2));
        String input3 = "I am good" + System.lineSeparator();
        InputStream userInput3 = new ByteArrayInputStream(input3.getBytes());
        testOtherUse.appendMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(userInput3));
        String input4 = "How about you?" + System.lineSeparator();
        InputStream userInput4 = new ByteArrayInputStream(input4.getBytes());
        testOtherUse.appendMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(userInput4));

        //declaring lines as random letters to ensure no null risk
        String line1 = "jklmn";
        String line2 = "abcde";

        // both of these "tries" read one of the files that was supposed to be edited to and adds them to array lists
        ArrayList<String> buyerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
            while (line1 != null) {
                buyerFile.add(line1);
                line1 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        ArrayList<String> sellerFile = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherUserTempUser.txt"))) {
            line2 = bufferedReader.readLine();
            while (line2 != null) {
                sellerFile.add(line2);
                line2 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }

        //calling delete to make sure the edit works even when files are not the same
        String inputDelete = "2" + System.lineSeparator();
        InputStream deleteStream = new ByteArrayInputStream(inputDelete.getBytes());
        testOtherUse.deleteMessageExecute("TempUser", "data/sellers/OtherUser/", "data/buyers/TempUser/", new Scanner(deleteStream));

        ArrayList<String> buyerFileAfter = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/buyers/TempUser/TempUserOtherUser.txt"))) {
            line1 = bufferedReader.readLine();
            while (line1 != null) {
                buyerFileAfter.add(line1);
                line1 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }
        ArrayList<String> sellerFileAfter = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("data/sellers/OtherUser/OtherUserTempUser.txt"))) {
            line2 = bufferedReader.readLine();
            while (line2 != null) {
                sellerFileAfter.add(line2);
                line2 = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file");
        }

        sellerFile.remove(1);

        assertEquals(sellerFile, sellerFileAfter);
        assertEquals(buyerFile, buyerFileAfter);

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f3 = new File("data/buyers/TempUser/metrics.txt");
        File f4 = new File("data/sellers/OtherUser/metrics.txt");
        f1.delete();
        f2.delete();
        f3.delete();
        f4.delete();
        File f5 = new File("data/buyers/TempUser/");
        File f6 = new File("data/sellers/OtherUser/");
        f5.delete();
        f6.delete();
    }

    @Test
    void importFile() {
    }
    /*
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

     */
}
