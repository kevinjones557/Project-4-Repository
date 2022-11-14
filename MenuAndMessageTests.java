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

public class MenuAndMessageTests {
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
        // Each of the correct outputs
        public static final String USER_BUY = "Bingus";
        public static final String PASSWORD_BUY = "bing12345";
        public static final String EMAIL_BUY = "bingleton@bing.com";
        public static final String USER_SELL = "Clifford";
        public static final String PASSWORD_SELL = "BigRedDog1";
        public static final String EMAIL_SELL = "reddog@bing.com";
        public static final String STORE1_SELL = "BoneShop";
        public static final String STORE2_SELL  = "DogHouse";
        public static final String LOGIN_PROMPT = "Welcome! Please enter 1 to log in or 2 to create a new account.";
        public static final String USERNAME_PROMPT = "Please enter your username.";
        public static final String PASSWORD_PROMPT = "Please enter your password";
        public static final String TO_MESSAGING_PROMPT = "Would you like to enter messaging or make account changes?\n1. Messaging\n2. Account changes";
        public static final String CONTINUE_PROGRAM_PROMPT = "Would you like to continue using the program?\n1. Yes\n2. No";
        public static final String FAREWELL = "Thank you for using the messenger. Goodbye!";
        public static final String SELECT_OPTION = "Please select an option below:";
        public final String BLOCK_INVISIBLE_OPTION = "1. Use block/invisible features\n2. Continue to messaging\n3. Exit Messaging System";
        public final String SEARCH_LIST_BUYER = "1. Search for a buyer\n2. View a list of buyers\n3. Cancel";
        public final String SEARCH_LIST_SELLER = "1. Search for a seller\n2. View a list of stores\n" +
                "3. Continue to statistics\n4. Exit Messaging System";
        public final String MESSAGE_OPTIONS = "1. View Message Contents\n2. Send a message\n3. Edit a message\n" +
                "4. Delete a message\n5. Import a .txt file\n6. Export message history as a CSV file\n7.Cancel";
        public final String METRICS_PROMPT = "1. View statistics for stores\n2. Exit Messaging System";

        public final String STORE_OR_SELLER_ACCOUNT = "1. Message with personal account\n2. Message with store account" +
                "\n3. Continue to statistics\n4. Exit Messaging System";

        public final String SWITCH_ACCOUNT = "1. Continue with current account\n2. Switch accounts\n" +
                "3. Continue to statistics\n4. Exit Messaging System";


        @Test
        public void testExpectedOne() {

            // Set the input
            String input = "1" + System.lineSeparator()
                    + USER_BUY + System.lineSeparator()
                    + PASSWORD_BUY + System.lineSeparator()
                    + "1" + System.lineSeparator()
                    + "3" + System.lineSeparator()
                    + "2" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = LOGIN_PROMPT + System.lineSeparator()
                    + USERNAME_PROMPT + System.lineSeparator()
                    + PASSWORD_PROMPT + System.lineSeparator()
                    + "Welcome, Bingus!" + System.lineSeparator()
                    + "Would you like to enter messaging or make account changes?" + System.lineSeparator()
                    + "1. Messaging" + System.lineSeparator()
                    + "2. Account changes" + System.lineSeparator()
                    + SELECT_OPTION + System.lineSeparator()
                    + "1. Use block/invisible features" + System.lineSeparator()
                    + "2. Continue to messaging" + System.lineSeparator()
                    + "3. Exit Messaging System" + System.lineSeparator()
                    + "Would you like to continue using the program?" + System.lineSeparator()
                    + "1. Yes" + System.lineSeparator()
                    + "2. No" + System.lineSeparator()
                    + FAREWELL + System.lineSeparator();


            // Runs the program with the input values
            receiveInput(input);
            LogIn.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals(expected.trim(), output.trim());
        }
    }
}
