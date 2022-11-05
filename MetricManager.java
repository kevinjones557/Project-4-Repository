import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Handles various metric data for Sellers
 *
 * @author Destin Groves
 */

public class MetricManager {
    /** Takes a Seller's message and extracts frequency of words. It writes this information to metrics.txt
     * Yes, this uses maps. It's great, means I don't need to make a new class to hold 2 values.
     * @param message The Seller's message to be parsed
     */
    public static void addMessageData(String username, String message) {
        int messageCount;
        Map<String, Integer> fileData = new LinkedHashMap<>();
        /* The formatting for the Metrics file goes as follows:

            Message Count: n
            Word Frequency:
            20 word
            80 wordly
            10 words

         */


        try (BufferedReader bfr = new BufferedReader(new FileReader(FileManager.getDirectoryFromUsername(username)))) {
            String line = bfr.readLine();
            messageCount = Integer.parseInt(line.substring(15));
            line = bfr.readLine();
            while (line != null) {
                String[] unmapped = line.split(" ");
                fileData.put(unmapped[1], Integer.parseInt(unmapped[0]));
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
            //This shouldn't be possible in the first place
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Integer> data = new LinkedHashMap<>();

        String[] splitString = message.split(" ");
        // Split along spaces
        for (String word : splitString) { // for every word in the string
            if (data.containsKey(word)) { // check if word exists in the map
                data.put(word, data.get(word) + 1); // if it does, increment
            } else {
                data.put(word, 1); // else, add it into the map
            }
        }
        data.forEach((word, count) -> {
            // TODO: implement
        });

    }

    public static void editMessageData(String username, String previousMessage, String newMessage) {
        // TODO: implement
    }

    public static void deleteMessageData(String username, String message) {
        // TODO: implement
    }

}
