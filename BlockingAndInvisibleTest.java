import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BlockingAndInvisibleTest {

    @Test
    /**
     * Test becomeInvisibleToUser method
     * seller test
     * buyers: buyer1, buyer2
     * test becomes invisible to "buyer1"
     * "buyer1" appears in test's isInvisible.txt,
     * a file that stores a list of people test
     * has become invisible to
     */
    void becomeInvisibleToUser() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Initialize users for testing
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1"; //expected
        //test becomes invisible to buyer1
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while ((line = bfr.readLine()) != null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
        //Clean up the data folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //Add the placeholder files back
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * Test blockUser method
     * seller test
     * buyers: buyer1, buyer2
     * test blocks "buyer1"
     * "buyer1" appears on test's hasBlocked.txt,
     * a file that stores a list of person
     * test has blocked
     */
    void blockUser() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //generate seller "test" and buyers "buyer1" and "buyer2"
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1"; //expected
        test.blockUser("buyer1"); //block buyer1
        //Check in the hasBlocked.txt file to see if buyer1 is there
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testHasBlocked));
            String line;
            while ((line = bfr.readLine()) != null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
        //Clean up the data folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate the placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * Test unblockUser method
     * seller test
     * buyers: buyer1, buyer1
     * test blocks both buyers but then
     * unblock "buyer2"
     * test's hasBlocked.txt file only contains
     * "buyer1"
     */
    void unblockUser() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1"; //expected
        //Blocks both users but then unblock buyer2
        test.blockUser("buyer1");
        test.blockUser("buyer2");
        test.unblockUser("buyer2");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        //Read from hasBlocked.txt
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testHasBlocked));
            String line;
            while ((line = bfr.readLine()) != null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
        //Clean up the folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //Recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }//Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * Test becomeVisibleAgain
     * seller: test
     * buyers: buyer1, buyer2
     * test become invisible to both buyers,
     * but then becomeVisibleAgain to "buyer2"
     * test's isInvisible.txt file now only
     * contains "buyer1"
     */
    void becomeVisibleAgain() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        test.becomeInvisibleToUser("buyer2");
        test.becomeVisibleAgain("buyer2");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        //Read from isInvisible.txt
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while ((line = bfr.readLine()) != null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
        //Clean up the folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * test blockedList
     * seller: test
     * buyers: buyer1, buyer2
     * test blocks both buyers then unblocks
     * "buyer2"
     * blockedList contains only "buyer1"
     */
    void blockedList() {
        //Temporary delete placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.blockUser("buyer1");
        test.blockUser("buyer2");
        test.unblockUser("buyer2");
        String output = "";
        try {
            output = test.blockedList()[0];
        } catch (Exception e) {
            System.out.printf("Unexpected error occurred");
        }
        //Clean up the folder
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * Test invisibleList
     * seller: test
     * buyers: buyer1, buyer2
     * test becomes invisible to both buyers,
     * but then becomes visible again to
     * "buyer2"
     * invisibleList contains "buyer1" only
     */
    void invisibleList() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        test.becomeInvisibleToUser("buyer2");
        test.becomeVisibleAgain("buyer2");
        String output = "";
        try {
            output = test.invisibleList()[0];
        } catch (Exception e) {
            System.out.printf("Unexpected error occurred");
            ;
        }
        //Clean up folder
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        assertEquals(output, expected);
    }

    @Test
    /**
     * Testing getAvailableUsers method
     * seller test
     * buyers: buyer1, buyer2
     * Successfully return an array containing username
     * of both buyer
     */
    void getAvailableUsers() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyer buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String[] availableUsers = {"buyer1", "buyer2"};
        String[] output = new String[2];
        try {
            output = test.getAvailableUsers();
        } catch (IOException e) {
            System.out.println("Test Case ran into an unexpected error");
        }
        //Clean up folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        for (int i = 0; i < output.length; i++) {
            assertEquals(output[i], availableUsers[i]);
        }

    }

    @Test
    /**
     * Test2 getAvailableUsers
     * seller: test
     * buyers: buyer1, buyer2
     * buyer1 becomes invisible to seller test
     * seller test getAvailableUsers only return buyer2
     */
    void getAvailableUsersTest2() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        MarketUser buyer1 = new MarketUser("buyer1", false);
        buyer1.becomeInvisibleToUser("test");
        String[] availableUsers = {"buyer2"};
        String[] output = new String[1];
        try {
            output = test.getAvailableUsers();
        } catch (IOException e) {
            System.out.println("Test Case ran into an unexpected error");
        }
        //Clean up folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        for (int i = 0; i < output.length; i++) {
            assertEquals(output[i], availableUsers[i]);
        }

    }

    @Test
    /**
     * Testing getMessageAbleUser method
     * seller test
     * buyers: buyer1, buyer2
     * Successfully return an array containing username
     * of both buyer
     */
    void getMessageAbleUser() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //Generate seller test and buyers buyer1 and buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String[] getMessage_ableUser = {"buyer1", "buyer2"};
        String[] output = new String[2];
        try {
            output = test.getMessageAbleUser();
        } catch (IOException e) {
            System.out.println("Test Case ran into an unexpected error");
        }
        //Clean up folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        for (int i = 0; i < output.length; i++) {
            assertEquals(output[i], getMessage_ableUser[i]);
        }
    }

    @Test
    /**
     * Test2 getMessageAbleUser2
     * seller: test
     * buyers: buyer1, buyer2
     * buyer1 blocks to seller test
     * seller test getMessageAbleUser2 only return buyer2
     */
    void getMessageAbleUser2() {
        //Temporary remove placeholder files
        File toRemoveBuyer = new File("data/buyers/placeholderbuyer.txt");
        File toRemoveSeller = new File("data/sellers/placeholderseller.txt");
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        toRemoveBuyer.delete();
        toRemoveSeller.delete();
        //generate seller test and buyers buyer1 buyer2
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        MarketUser buyer1 = new MarketUser("buyer1", false);
        buyer1.blockUser("test");
        String[] messageAble = {"buyer2"};
        String[] output = new String[1];
        try {
            output = test.getMessageAbleUser();
        } catch (IOException e) {
            System.out.println("Test Case ran into an unexpected error");
        }
        //clear up folder
        File buyer1Dir = new File("data/buyers/buyer1");
        File buyer1hasBlocked = new File("data/buyers/buyer1/hasBlocked.txt");
        File buyer1isInvisible = new File("data/buyers/buyer1/isInvisible.txt");
        File buyer1metric = new File("data/buyers/buyer1/metrics.txt");
        buyer1hasBlocked.delete();
        buyer1isInvisible.delete();
        buyer1metric.delete();
        buyer1Dir.delete();

        File buyer2Dir = new File("data/buyers/buyer2");
        File buyer2hasBlocked = new File("data/buyers/buyer2/hasBlocked.txt");
        File buyer2isInvisible = new File("data/buyers/buyer2/isInvisible.txt");
        File buyer2metric = new File("data/buyers/buyer2/metrics.txt");
        buyer2hasBlocked.delete();
        buyer2isInvisible.delete();
        buyer2metric.delete();
        buyer2Dir.delete();

        File testDir = new File("data/sellers/test");
        File testHasBlocked = new File("data/sellers/test/hasBlocked.txt");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        File testMetrics = new File("data/sellers/test/metrics.txt");
        testHasBlocked.delete();
        testIsInvisible.delete();
        testMetrics.delete();
        testDir.delete();
        //recreate placeholder files
        try {
            toRemoveBuyer.createNewFile();
            toRemoveSeller.createNewFile();
        } catch (IOException e) {
            System.out.println("Unexpected error occurred");
        }
        //Comparing
        for (int i = 0; i < output.length; i++) {
            assertEquals(output[i], messageAble[i]);
        }

    }
}
