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

    @Test
    void appendMessage() {
    }

    @Test
    void testAppendMessage() {
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

        testSend.appendMessageExecute("OtherUser", "data/buyers/TempUser/", "data/sellers/OtherUser/", new Scanner(userInput));

        //declaring lines as random letters to ensure no null risk
        String line1 = "jklmn";
        String line2 = "abcde";

        // both tries read each file that was supposed to be written to
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

        //each expect is the final line of each file
        String expect1 = buyerFile.get(buyerFile.size() - 1).substring(buyerFile.get(buyerFile.size() - 1).indexOf("-") + 2);
        String expect2 = sellerFile.get(sellerFile.size() - 1).substring(sellerFile.get(sellerFile.size() - 1).indexOf("-") + 2);
        assertEquals(expect1, "Hello");
        assertEquals(expect2, "Hello");

        File f1 = new File("data/buyers/TempUser/TempUserOtherUser.txt");
        File f2 = new File("data/sellers/OtherUser/OtherUserTempUser.txt");
        File f5 = new File("data/buyers/TempUser/metrics.txt");
        f1.delete();
        f2.delete();
        f5.delete();
        File f3 = new File("data/buyers/TempUser/");
        File f4 = new File("data/sellers/OtherUser/");
        f3.delete();
        f4.delete();

    }

    @Test
    void editMessage() {
    }

    @Test
    void testEditMessage() {
    }

    @Test
    void editMessageExecute() {
    }

    @Test
    void deleteMessage() {
    }

    @Test
    void testDeleteMessage() {
    }

    @Test
    void deleteMessageExecute() {
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
