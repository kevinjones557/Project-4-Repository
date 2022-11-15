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

        Assert.assertEquals(expectedOutput, os.toString());

    }

    @Test
    void mainForSeller() {
    }

    @Test
    void mainForBuyer() {
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
    void message() {

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
