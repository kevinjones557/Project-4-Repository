import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    //the structure below for testing was taken partially from the RunLocalTest methods that appear in homeworks and projects
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

        @Test
        public void testOne() {
            String input = "2" + System.lineSeparator() +
                    "testCaseRun" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "password" + System.lineSeparator() +
                    "yes" + System.lineSeparator() +
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
                    "Would you like to enter messaging or make account changes? \n" +
                    "1. Messaging \n" +
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
                    "Would you like to enter messaging or make account changes? \n" +
                    "1. Messaging \n" +
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

        @Test
        public void testTwo() {
            String input = "";

            String expected = "";

            receiveInput(input);
            LogIn.main(new String[0]);
            String output = getOutput();
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }
    }
}