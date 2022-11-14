import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MarketUserTest {
    @Test
    void waitForValidInput() {
        String input = "5" + System.lineSeparator() + "h" + System.lineSeparator() + 3 + System.lineSeparator();
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scan = new Scanner(inputStream);

        OutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        MarketUser mu = new MarketUser("testBuyer", false);
        mu.waitForValidInput(1, 4, scan);

        String expectedOutput = "Please enter a valid number:" + System.lineSeparator() +
                "Please enter a valid number:" + System.lineSeparator() +
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
        } catch (IOException e) {
            System.out.println("Could not create folder");
        }

        String expected = "NewStore";
        String actual;

        MarketUser.changeStoreName("Store", "NewStore");

        File newFile = new File("data/sellers/TempSeller/NewStore");
        if (newFile.exists()) {
            actual = "NewStore";
        } else {
            actual = "";
        }
        Assert.assertEquals(expected, actual);
        try {
            Files.delete(Paths.get("data/sellers/TempSeller/NewStore"));
            Files.delete(Paths.get("data/sellers/TempSeller"));
        } catch (IOException e) {
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

        for (int i = 0; i < expectedResult.size(); i++) {
            Assert.assertEquals(expectedResult.get(i), actualResult.get(i));
        }

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
    }

    @Test
    void appendMessageExecute() {
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
