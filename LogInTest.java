import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class to test every part of the LogIn class along with file output.
 * To avoid any errors, these tests MUST be run WITHOUT ANY EXISTING US0ERS
 * or else the output might be changed from the expected output in these cases, and they may fail.
 * These are JUnit4 tests.
 * <p>
 *
 * @author riley197
 * @version 11/13/22
 */

public class LogInTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        System.out.printf("Test Count: %d.\n", result.getRunCount());
        if (result.wasSuccessful()) {
            System.out.printf("Excellent - all local tests ran successfully.\n");
        } else {
            System.out.printf("Tests failed: %d.\n", result.getFailureCount());
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Runs the testing for the LogIn class - template code obtained from @PurdueCS
     * <p>
     *
     * @author @riley197
     * @version 11/16/22
     */

    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        /**
         * Tests store creation, name changing, account deletion, and incorrect input
         */

        @Test
        public void testOne() {
            Placeholders.delete();
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "email" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "newStore" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();

            String expected = "Welcome! Please enter 1 to log in or 2 to create a new account.\n" +
                    "Please enter a username in between 6 and 16 characters inclusive.\n" +
                    "Please enter a password between 8 and 16 characters.\n" +
                    "Please enter your password again to confirm it.\n" +
                    "Are you a seller? Please enter 'yes' or 'no.'\n" +
                    "Please enter your store name.\n" +
                    "Store name constraints: \n" +
                    "- Cannot be blank \n" +
                    "- Must be in between 4 and 16 characters inclusive \n" +
                    "Please enter a valid store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Sellers must have at least one store! Please add a store before continuing.\n" +
                    "Please enter your store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter '1' or '2' as input!\n" +
                    "Please enter your store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter an email to be associated with your account.\n" +
                    "That's not a valid email! Please enter an email with a valid name and domain.\n" +
                    "Account created! Welcome, testCaseRun!\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Which store name would you like to change?\n" +
                    "[firstStore, secondStore]\n" +
                    "Please enter the new store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Name change successful!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Thank you for using the messenger. Goodbye!";

            receiveInput(input);
            LogIn.main(new String[0]);
            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        /**
         * Tests incorrect input for account creation and password confirmation
         */

        @Test
        public void testTwo() {
            String input = "2" + System.lineSeparator() +
                    "test" + System.lineSeparator() +
                    "namethatiswaytoolong" + System.lineSeparator() +
                    "user name" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "pass" + System.lineSeparator() +
                    "passssssssssssssssss" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "pass" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "email" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "4" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();

            String expected = "Welcome! Please enter 1 to log in or 2 to create a new account.\n" +
                    "Please enter a username in between 6 and 16 characters inclusive.\n" +
                    "Username constraints: \n" +
                    "- Cannot be blank \n" +
                    "- Must be in between 6 and 16 characters inclusive \n" +
                    "- Cannot contain spaces \n" +
                    "Please enter a valid username.\n" +
                    "Username constraints: \n" +
                    "- Cannot be blank \n" +
                    "- Must be in between 6 and 16 characters inclusive \n" +
                    "- Cannot contain spaces \n" +
                    "Please enter a valid username.\n" +
                    "Username constraints: \n" +
                    "- Cannot be blank \n" +
                    "- Must be in between 6 and 16 characters inclusive \n" +
                    "- Cannot contain spaces \n" +
                    "Please enter a valid username.\n" +
                    "Please enter a password between 8 and 16 characters.\n" +
                    "Password length must be between 8 and 16 characters! Please enter a valid password.\n" +
                    "Password length must be between 8 and 16 characters! Please enter a valid password.\n" +
                    "Please enter your password again to confirm it.\n" +
                    "Passwords did not match! Please try again.\n" +
                    "Please enter a password between 8 and 16 characters.\n" +
                    "Please enter your password again to confirm it.\n" +
                    "Are you a seller? Please enter 'yes' or 'no.'\n" +
                    "Please enter an email to be associated with your account.\n" +
                    "That's not a valid email! Please enter an email with a valid name and domain.\n" +
                    "Account created! Welcome, testCaseRun!\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Please enter '1' or '2' as input!\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Thank you for using the messenger. Goodbye!\n";

            receiveInput(input);
            LogIn.main(new String[0]);
            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        /**
         * Tests store creation, changing store names, changing account name,
         * some incorrect input, and all parts of the control flow for the UI
         */

        @Test
        public void testThree() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "newName" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "store3" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes";

            String expected = "Welcome! Please enter 1 to log in or 2 to create a new account.\n" +
                    "Please enter a username in between 6 and 16 characters inclusive.\n" +
                    "Please enter a password between 8 and 16 characters.\n" +
                    "Please enter your password again to confirm it.\n" +
                    "Are you a seller? Please enter 'yes' or 'no.'\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "Please enter your store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter your store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter your store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter an email to be associated with your account.\n" +
                    "Account created! Welcome, testCaseRun!\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Please enter your new username!\n" +
                    "Username already exists! Please enter another username.\n" +
                    "Name change successful! Enjoy your new username, newName!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Which store name would you like to change?\n" +
                    "[store1, store2]\n" +
                    "Please enter the new store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Name change successful!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Please enter '1,' '2,' '3,' or '4' as input!\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "We're glad you decided to stay!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Thank you for using the messenger. Goodbye!";

            receiveInput(input);
            LogIn.main(new String[0]);
            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        /**
         * Tests file contents after seller account is created and
         * ensures they are written as expected
         */

        @Test
        public void testFour() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "email" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "firstStore" + System.lineSeparator() +
                    "secondStore" + System.lineSeparator() +
                    "newStore" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "4" + System.lineSeparator();

            List<String> fileContents = new ArrayList<>();
            List<String> expectedContents = new ArrayList<>(Arrays.asList("testCaseRun", "u\\xn|jw_",
                    "true", "[newStore, secondStore]", "email@domain.com"));
            receiveInput(input);
            LogIn.main(new String[0]);
            try (BufferedReader bfr = new BufferedReader(new FileReader("users/testCaseRun/testCaseRun"))) {
                String line = bfr.readLine();
                while (line != null) {
                    fileContents.add(line);
                    line = bfr.readLine();
                }
            } catch (Exception e) {
                System.out.println("Test 4 was not successful!");
            }
            assertEquals(expectedContents, fileContents);

            input = "1" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();
            receiveInput(input);
            LogIn.main(new String[0]);
        }

        /**
         * Tests file contents when a buyer is created
         */

        @Test
        public void testFive() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "4" + System.lineSeparator();

            List<String> fileContents = new ArrayList<>();
            List<String> expectedContents = new ArrayList<>(Arrays.asList("testCaseRun", "u\\xn|jw_",
                    "false", "email@domain.com"));
            receiveInput(input);
            LogIn.main(new String[0]);
            try (BufferedReader bfr = new BufferedReader(new FileReader("users/testCaseRun/testCaseRun"))) {
                String line = bfr.readLine();
                while (line != null) {
                    fileContents.add(line);
                    line = bfr.readLine();
                }
            } catch (Exception e) {
                System.out.println("Test 4 was not successful!");
            }
            assertEquals(expectedContents, fileContents);

            input = "1" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();
            receiveInput(input);
            LogIn.main(new String[0]);
        }

        /**
         * Another file test with different input to further ensure file writing
         * is successful after changing account name and store names
         */

        @Test
        public void testSix() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "mfiji!JHfje78g" + System.lineSeparator() +
                    "mfiji!JHfje78g" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "anotherEmail@gmail.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "newName" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "newStore1" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "newStore1" + System.lineSeparator() +
                    "newStore2" + System.lineSeparator() +
                    "2" + System.lineSeparator();

            List<String> fileContents = new ArrayList<>();
            List<String> expectedContents = new ArrayList<>(Arrays.asList("newName", "ranen\u001COCkej2=b", "true",
                    "[newStore1, newStore2]", "anotherEmail@gmail.com"));
            receiveInput(input);
            LogIn.main(new String[0]);
            try (BufferedReader bfr = new BufferedReader(new FileReader("users/newName/newName"))) {
                String line = bfr.readLine();
                while (line != null) {
                    fileContents.add(line);
                    line = bfr.readLine();
                }
            } catch (Exception e) {
                System.out.println("Test 5 was not successful!");
            }
            assertEquals(expectedContents, fileContents);

            input = "1" + System.lineSeparator() +
                    "newName" + System.lineSeparator() +
                    "mfiji!JHfje78g" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();
            receiveInput(input);
            LogIn.main(new String[0]);
        }

        /**
         * Tests account creation, logging in, multiple incorrect inputs, and
         * account deletion
         */

        @Test
        public void testSeven() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "email@domain.com" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "newName" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "3" + System.lineSeparator() +
                    "store1" + System.lineSeparator() +
                    "store2" + System.lineSeparator() +
                    "store3" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "5" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "no" + System.lineSeparator() +
                    "2";

            String expected = "Welcome! Please enter 1 to log in or 2 to create a new account.\n" +
                    "Please enter a username in between 6 and 16 characters inclusive.\n" +
                    "Please enter a password between 8 and 16 characters.\n" +
                    "Please enter your password again to confirm it.\n" +
                    "Are you a seller? Please enter 'yes' or 'no.'\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "Please enter your store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter your store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter your store name.\n" +
                    "Are you sure you want to add this store to your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Enter '1' to add a store or '2' to finish adding stores.\n" +
                    "Please enter an email to be associated with your account.\n" +
                    "Account created! Welcome, testCaseRun!\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Please enter your new username!\n" +
                    "Name change successful! Enjoy your new username, newName!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Which store name would you like to change?\n" +
                    "[store1, store2]\n" +
                    "Please enter the new store name.\n" +
                    "Name is already in use! Please pick a different name.\n" +
                    "Name change successful!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Please enter '1,' '2,' '3,' or '4' as input!\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Please enter 'yes' or 'no'!\n" +
                    "We're glad you decided to stay!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "We're glad you decided to stay!\n" +
                    "Would you like to continue using the program? \n" +
                    "1. Yes \n" +
                    "2. No\n" +
                    "Thank you for using the messenger. Goodbye!\n" +
                    "Welcome! Please enter 1 to log in or 2 to create a new account.\n" +
                    "Please enter your username.\n" +
                    "That user doesn't exist! Press 1 to try again and 2 to exit.\n" +
                    "Please enter your username.\n" +
                    "Please enter your password\n" +
                    "Welcome, newName!\n" +
                    "Would you like to enter user interaction or make account changes? \n" +
                    "1. User Interaction \n" +
                    "2. Account changes\n" +
                    "Options: \n" +
                    "1. Edit your name\n" +
                    "2. Delete your account\n" +
                    "3. Change a store name\n" +
                    "4. Exit\n" +
                    "Are you sure you want to delete your account? This action cannot be undone. \n" +
                    "Enter 'yes' to confirm or 'no' to abort.\n" +
                    "Thank you for using the messenger. Goodbye!";

            receiveInput(input);
            LogIn.main(new String[0]);

            input = "1" + System.lineSeparator() +
                    "new" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "newName" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "yes" + System.lineSeparator();

            receiveInput(input);
            LogIn.main(new String[0]);
            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }

        /**
         * Confirms that the deletion algorithm worked by checking the directory
         * contents to see if it is empty
         */

        @Test
        public void testEight() {
            String[] directoryContents = null;
            String[] expectedContents = {"storeNames"};
            try {
                File f = new File("users/");
                directoryContents = f.list();
            } catch (Exception e) {
                System.out.println("Test 7 was not successful!");
            }
            assertEquals(Arrays.deepToString(expectedContents), Arrays.deepToString(directoryContents));
            Placeholders.create();
        }
    }
}