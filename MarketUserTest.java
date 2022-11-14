import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MarketUserTest {

    @Test
    void main() {

    }

    @Test
    void waitForValidInput() {
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
    }

    @Test
    void changeStoreName() {
    }

    @Test
    void changeNameInFile() {
    }

    @Test
    void message() {
    }

    @Test
    void writeCSV() {
        Path testUserFolder = null;
        Path testUserOutput = null;

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
