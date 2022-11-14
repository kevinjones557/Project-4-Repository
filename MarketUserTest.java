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
        Path testUserMessage = null;
        Path testUserOutput = null;

        MarketUser mu = new MarketUser("TempUser2", false);

        try {
            testUserFolder = Files.createDirectory(Paths.get("data/buyers/TempUser"));
            testUserMessage = Files.createFile(Path.of("data/buyers/TempUser/.txt"));

        } catch (IOException e) {
            System.out.println("Could not create folders");
        }

        String[] testContents = {"Bingus 11/14 14:20:06- Hi!!!!!\n" + System.lineSeparator()};

        ArrayList<String> expectedResult = new ArrayList<>();
        expectedResult.add("Name:,Date:,Time:,Message:" + System.lineSeparator());
        expectedResult.add("Bingus,11/14,14:20:06,Hi!!!!!" + System.lineSeparator());

        String filePath = "data/buyers";



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
