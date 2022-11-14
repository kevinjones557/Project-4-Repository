import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockingandInvisibleTest {

    @Test
    void getAvailableUsers() {
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
        for(int i = 0; i < output.length; i++) {
            assertEquals(output[i], availableUsers[i]);
        }

    }

    @Test
    void getMessageAbleUser() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String[] getMessage_ableUser = {"buyer1", "buyer2"};
        String[] output = new String[2];
        try {
            output = test.getAvailableUsers();
        } catch (IOException e) {
            System.out.println("Test Case ran into an unexpected error");
        }
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
        for(int i = 0; i < output.length; i++) {
            assertEquals(output[i], getMessage_ableUser[i]);
        }
    }

    @Test
    void becomeInvisibleToUser() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }
    @Test
    void blockUser() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }

    @Test
    void unblockUser() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }

    @Test
    void becomeVisibleAgain() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }
    @Test
    void blockedList() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }

    @Test
    void invisibleList() {
        FileManager.generateDirectoryFromUsername("test", true);
        MarketUser test = new MarketUser("test", true);
        FileManager.generateDirectoryFromUsername("buyer1", false);
        FileManager.generateDirectoryFromUsername("buyer2", false);
        String expected = "buyer1";
        test.becomeInvisibleToUser("buyer1");
        File testIsInvisible = new File("data/sellers/test/isInvisible.txt");
        String output = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(testIsInvisible));
            String line;
            while((line = bfr.readLine())!= null) {
                output += line;
            }
            output = output.trim();
            bfr.close();
        } catch (IOException e) {
            System.out.println("Unexpected Error!");
        }
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
        assertEquals(output,expected);
    }

}