import java.util.ArrayList;
import java.util.Scanner;

/**
 * Filtering class with static functions
 * to deal with filtering words
 * This is not the final version, rather
 * a complete one but with options to play
 * around and to be tested
 *
 * @author Vinh Pham Ngoc Thanh LC2
 * @version November 7 2022
 */
public class Filtering{
    //Test array
    private static String[] censoredWords = new String[]{"fuck", "shit", "cunt", "pussy", "dick"};

    /**
     * Check if the entered string is a legit word
     * We don't want user to enter something like
     * period, comma, semicolon or even spaces
     * to mess up our program
     * @param word
     * @return whether it's a legit word
     */
    public static boolean isLegitWord(String word) {
        char[] everyChar = word.toCharArray();
        for(char x: everyChar) {
            if(!Character.isAlphabetic(x)) {
                return false;
            }
        }
        return true;
    }

    /**
     * A helper class to replace a defined part
     * of the String
     * @param string
     * @param startEnd
     * @return new replaced String
     */
    public static String customReplace(String string, int[] startEnd) {
        String head = string.substring(0, startEnd[0]);
        String toChange = string.substring(startEnd[0], startEnd[1] + 1);
        String newPart = "*".repeat(toChange.toCharArray().length);
        String tail = string.substring(startEnd[1] + 1);
        String ans = head + newPart + tail;
        return ans;
    }

    /**
     * This is a lightFiler that only filtered
     * "shit" not "bullshit". The reason to have this
     * one is to avoid having "ate" as a censored word
     * but also displaying "concentrate" as "concentr***"
     * and "nitrate" as "nitr***". Looks sus
     *
     * @param message
     * @param censoredWords
     * @return LightFiltered message
     */
    public static String lightFilter(String message, String[] censoredWords) {
        //Locate where the actual words are
        ArrayList<int[]> wordPositions = new ArrayList<>();
        int start = 0;
        int end;
        while (start != message.length()) {
            if (!Character.isAlphabetic(message.charAt(start))) {
                if (start == message.length() - 1) {
                    break;
                }
                start++;
            }
            end = start;
            while (end != message.length() - 1 && Character.isAlphabetic(message.charAt(end + 1))) {
                end++;
            }
            if (Character.isAlphabetic(message.charAt(start))) {
                wordPositions.add(new int[]{start, end});
            }
            start = end + 1;
        }
        String ans = message;
        for (int[] startEnd : wordPositions) {
            for (String censoredWord : censoredWords) {
                if (censoredWord.equals(ans.substring(startEnd[0], startEnd[1] + 1).toLowerCase())) {
                    ans = customReplace(ans, startEnd);
                }
            }
        }
        return ans;
    }

    /**
     * A filter that basically function like
     * if you entered some letters together you
     * won't every see those together
     * @param message
     * @param censoredWords
     * @return
     */
    public static String absoluteFilter(String message, String[] censoredWords) {
        String ans = message;
        for (String censoredWord : censoredWords) {
            ans = ans.replace(censoredWord, "*".repeat(censoredWord.toCharArray().length));
        }
        return ans;
    }

    //Main method for testing
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter message");
            String message = sc.nextLine();
            System.out.println("Light filtered message: " + lightFilter(message, censoredWords));
            System.out.println("Absolute filtered message: " + absoluteFilter(message, censoredWords));

        }
    }
}

