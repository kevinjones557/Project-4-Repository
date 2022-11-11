import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** A class to create accounts for users, log them in, and perform any necessary interaction with said accounts.
 *
 * author @Adenr4615
 * version 11/9/22
 * */

public class LogIn {

    private String userLocation;

    /** Writes user's username to a file
     *
     * @param user String of the user's username
     * @return boolean of if file was successfully written or not
     */
    public static boolean writeFile(String user) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/" + user + "/" + user, false))) {
            pw.println(user);
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return (false);
        }
    }

    /** Appends an additional line to a given user's file
     *
     * @param user     the user whose file is being appended
     * @param toAppend the parameter that is being appended to the file
     * @return boolean of if the file was successfully written or not
     */
    public static boolean writeFile(String user, String toAppend) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/" + user + "/" + user, true))) {
            pw.println(toAppend);
            return (true);
        } catch (Exception e) {
            return (false);
        }
    }

    /** Checks to see if the given store name is already in use
     *
     * @param storeName the name of the store being checked
     * @return boolean of if the store exists or not for handling in main
     */
    public static boolean checkStoreList(String storeName) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/storeNames"))) {
            ArrayList<String> fileContents = new ArrayList<>();
            String line = br.readLine();
            if (line == null) {
                return (true);
            }
            while (line != null) {
                fileContents.add(line);
                line = br.readLine();
            }
            for (String fileContent : fileContents) {
                if (fileContent.equals(storeName)) {
                    return (false);
                }
            }
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
        return (true);
    }

    /** Returns the stores that a user has registered under their username
     *
     * @param user user's username
     * @return String representation of the user's stores
     */
    public static String getUsersStores(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
            String stores = "";
            int lineIndex = 0;
            String line = br.readLine();
            while (line != null) {
                if (lineIndex == 2 && line.equals("false")) {
                    return(null);
                }
                if (lineIndex == 3) {
                    stores = line;
                }
                line = br.readLine();
                lineIndex++;
            }
            stores = stores.substring(1, stores.length() - 1);
            return (stores);
        } catch (Exception e) {
            return (null);
        }
    }

    /** Allows users to change any given store name
     *
     * @param user user's username
     * @param scan Scanner to capture input
     */
    public static void changeStoreName (String user, Scanner scan) {
        if (getUsersStores(user) != null) {
            String stores = getUsersStores(user);
            List<String> storesArray = Arrays.asList(stores.split(", "));
            System.out.println("Which store name would you like to change?");
            System.out.println(storesArray);
            String storeToChange = null;
            boolean storeFound = false;
            try {
                while (!storeFound) {
                    storeToChange = scan.nextLine();
                    for (String s : storesArray) {
                        if (s.equals(storeToChange)) {
                            storeFound = true;
                        }
                    }
                    if (!storeFound) {
                        System.out.println("That store was not found under your account! Please enter one of the following stores:");
                        System.out.println(storesArray);
                    }
                }
                System.out.println("Please enter the new store name.");
                String newName = scan.nextLine();
                boolean nameInUse = checkStoreList(newName);
                while (!nameInUse) {
                    System.out.println("That name is in use! Please enter another name.");
                    newName = scan.nextLine();
                    nameInUse = checkStoreList(newName);
                }
                storesArray.set(storesArray.indexOf(storeToChange), newName);
                try (BufferedReader br = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
                    ArrayList<String> fileContents = new ArrayList<>();
                    String line = br.readLine();
                    while (line != null) {
                        fileContents.add(line);
                        line = br.readLine();
                    }
                    fileContents.set(3, storesArray.toString());
                    writeFile(user);
                    for (int i = 0; i < fileContents.size(); i++) {
                        if (i != 0) {
                            writeFile(user, fileContents.get(i));
                        }
                    }
                    removeRenamedStore(storeToChange, newName);
                    System.out.println("Name change successful!");
                } catch (Exception e) {
                    System.out.println("Name change was not successful!");
                }
            } catch (Exception e) {
                System.out.println("An unknown error occurred!");
            }
        } else {
            System.out.println("You are not a seller!");
        }
    }

    /** Allows user to log in to an individual store account
     *
     * @param user user logging in to their store
     * @param scan Scanner to capture input
     * @return the new location of the user after they logged in to a store
     */
    public static String changeUserLocation (String user, Scanner scan) {
        if (getUsersStores(user) != null) {
            String stores = getUsersStores(user);
            List<String> storesArray = Arrays.asList(stores.split(", "));
            System.out.println("Which store would you like to log in to?");
            System.out.println(storesArray);
            String logInLocation = null;
            boolean storeFound = false;
            try {
                while (!storeFound) {
                    logInLocation = scan.nextLine();
                    for (String s : storesArray) {
                        if (s.equals(logInLocation)) {
                            storeFound = true;
                        }
                    }
                    if (!storeFound) {
                        System.out.println("That store was not found under your account! Please enter one of the following stores:");
                        System.out.println(storesArray);
                    }
                }
                System.out.println("Log in successful!");
                return (logInLocation);
            } catch (Exception e) {
                System.out.println("Log in was not successful!");
            }
        } else {
            System.out.println("You are not a seller!");
        }
        return (null);
    }

    /** Changes the user's name to their new desired name
     *
     * @param user the user changing their name
     * @param scan Scanner to capture input
     */
    public static void appendUsername(String user, Scanner scan) {
        System.out.println("Please enter your new username!");
        String newUser = scan.nextLine();
        File dir = new File("users/" + newUser);
        try {
            if (!dir.createNewFile()) {
                while (!dir.createNewFile()) {
                    System.out.println("Username already exists! Please enter another username.");
                    newUser = scan.nextLine();
                    dir = new File("users/" + newUser);
                }
            }
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
        dir.delete();
        if (newUser.equals("")) {
            while (newUser.equals("")) {
                System.out.println("New username cannot be blank! Please enter your new username.");
                newUser = scan.nextLine();
            }
        }
        try {
            //this method was retrieved with help from StackOverflow user @kr37
            Path source = Paths.get("users/" + user + "/" + user);
            Files.move(source, source.resolveSibling(newUser));
            source = Paths.get("users/" + user);
            Files.move(source, source.resolveSibling(newUser));
            MarketUser.changeUsername(user, newUser);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Name change was not successful!");
        }
        try (BufferedReader br = new BufferedReader(new FileReader("users/" + newUser + "/" + newUser))) {
            ArrayList<String> fileContents = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                fileContents.add(line);
                line = br.readLine();
            }
            writeFile(newUser);
            fileContents.remove(0);
            for (String s : fileContents) {
                writeFile(newUser, s);
            }
            System.out.println("Name change successful! Enjoy your new username, " + newUser + "!");
        } catch (Exception e) {
            System.out.println("Name change was not successful!");
        }
    }

    /** Removes a deleted user's stores from the store list
     *
     * @param storesString String representation of the user's stores
     */
    public static void appendStoreList(String storesString) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/storeNames"))) {
            List<String> stores = Arrays.asList(storesString.split(", "));
            List<String> fileContents = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                fileContents.add(line);
                line = br.readLine();
            }
            for (String s : stores) {
                if (fileContents.contains(s)) {
                    fileContents.remove(s);
                }
            }
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/storeNames", false))) {
                for (String s : fileContents) {
                    pw.println(s);
                }
            } catch (Exception e) {
                System.out.println("An unknown error occurred!");
            }
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
    }

    /** Removes the name of a store that gets renamed
     *
     * @param store the store being deleted
     * @param newStore the new store name overwriting the old store name
     */
    public static void removeRenamedStore(String store, String newStore) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/storeNames"))) {
            List<String> fileContents = new ArrayList<>();
            String line = br.readLine();
            while (line != null) {
                fileContents.add(line);
                line = br.readLine();
            }
            fileContents.set(fileContents.indexOf(store), newStore);
            try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/storeNames", false))) {
                for (String s : fileContents) {
                    pw.println(s);
                }
            } catch (Exception e) {
                System.out.println("An unknown error occurred!");
            }
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
    }

    /** Deletes user's account from both central database and local account information database
     *
     * @param user user's username
     * @param scan Scanner to capture input
     */
    public static void deleteUser(String user, Scanner scan) {
        System.out.println("Are you sure you want to delete your account? Enter 'yes' to confirm or 'no' to abort.");
        String response = "";
        boolean userInput = false;
        while (!userInput) {
            try {
                response = scan.nextLine();
                if (response.equals("yes") || response.equals("no")) {
                    userInput = true;
                }
            } catch (Exception e) {
                System.out.println("Please enter valid input!");
            }
        }
        if (response.equalsIgnoreCase("yes")) {
            String stores = getUsersStores(user);
            if (stores != null) {
                appendStoreList(stores);
            }
            MarketUser.deleteUsername(user);
            File userInfo = new File("users/" + user + "/" + user);
            userInfo.delete();
            File userDirectory = new File("users/" + user);
            userDirectory.delete();
            System.out.println("Thank you for your business, " + user + "!");
        } else {
            System.out.println("We're glad you decided to stay!");
        }
    }

    /** Updates the store list by adding a store name that has been confirmed to not be in use already
     *
     * @param storeName the store name being appended to the file
     */
    public static void updateStoreList(String storeName) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/storeNames", true))) {
            pw.println(storeName);
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
    }

    /** Encrypts the password of the user file when an account is created
     *
     * @param user the user whose password is being encrypted
     */
    public static void encryptFile(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
            String password = "";
            int index = 0;
            String line = br.readLine();
            while (line != null) {
                index++;
                if (index == 2) {
                    password = line;
                }
                line = br.readLine();
            }
            br.close();
            char[] toBeEncrypted = password.toCharArray();
            for (int i = 0; i < toBeEncrypted.length; i++) {
                if (i % 2 == 0) {
                    toBeEncrypted[i] += 5;
                } else {
                    toBeEncrypted[i] -= 5;
                }
            }
            writeFile(user);
            String finalPassword = "";
            for (char c : toBeEncrypted) {
                finalPassword += c;
            }
            writeFile(user, finalPassword);
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
    }

    /** Takes a password input by a user attempting to log in and uses the key to encrypt it for comparison
     *
     * @param input the password being encrypted
     * @return String of the encrypted password
     */
    public static String encrypt(String input) {
        String finalInput = "";
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < inputArray.length; i++) {
            if (i % 2 == 0) {
                inputArray[i] += 5;
            } else {
                inputArray[i] -= 5;
            }
        }
        for (char c : inputArray) {
            finalInput += c;
        }
        return (finalInput);
    }

    /** Creates the file and directory for a user only if the account doesn't already exist
     *
     * @param user the user whose file is being created
     * @param scan scanner object to capture input
     */
    public static void createUser(String user, Scanner scan) {
        File f;
        File dir = new File("users/" + user);
        if (!dir.exists()) {
            dir.mkdirs();
            f = new File("users/" + user + "/" + user);
        } else {
            try {
                if (!dir.createNewFile()) {
                    while (!dir.createNewFile()) {
                        System.out.println("Username already exists! Please enter another username.");
                        user = scan.nextLine();
                        dir = new File("users/" + user);
                    }
                }
                dir.mkdirs();
                f = new File("users/" + user + "/" + user);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An unknown error occurred!");
            }
        }
        boolean fileStatus = writeFile(user);
        boolean done = false;
        if (fileStatus) {
            try {
                while (!done) {
                    System.out.println("Please enter a password between 8 and 16 characters.");
                    String password = scan.nextLine();
                    if (password.length() < 8 || password.length() > 16) {
                        while (password.length() < 8 || password.length() > 16) {
                            System.out.println("Password length must be between 8 and 16 characters! Please enter a valid password.");
                            password = scan.nextLine();
                        }
                    }
                    fileStatus = writeFile(user, password);
                    if (!fileStatus) {
                        System.out.println("An unknown error occurred! Please try again.");
                    }
                    encryptFile(user);
                    done = true;
                }
                done = false;
                System.out.println("Are you a seller? Please enter 'yes' or 'no.'");
                String isSeller = scan.nextLine();
                while (!done) {
                    while (!isSeller.equalsIgnoreCase("yes") &&
                            !isSeller.equalsIgnoreCase("no")) {
                        System.out.println("Please enter 'yes' or 'no'!");
                        isSeller = scan.nextLine();
                    }
                    if (isSeller.equalsIgnoreCase("yes")) {
                        isSeller = "true";
                        FileManager.generateDirectoryFromUsername(user, true);
                        boolean doneStores = false;
                        ArrayList<String> storeNames = new ArrayList<>();
                        while (!doneStores) {
                            System.out.println("Please enter your store name.");
                            String storeName = scan.nextLine();
                            boolean nameChecked = checkStoreList(storeName);
                            //skips line underneath
                            if (storeName.equals("") || !nameChecked) {
                                while (storeName.equals("") || !nameChecked) {
                                    System.out.println("Store name is either blank or in use! Please enter a valid store name.");
                                    storeName = scan.nextLine();
                                    nameChecked = checkStoreList(storeName);
                                }
                            }
                            System.out.println("Are you sure you want to add this store to your account? Enter 'yes' to confirm or 'no' to abort.");
                            String addStore = "";
                            boolean storeInput = false;
                            while (!storeInput) {
                                try {
                                    addStore = scan.nextLine();
                                    if (addStore.equals("yes") || addStore.equals("no")) {
                                        storeInput = true;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter valid input!");
                                }
                            }
                            if (addStore.equalsIgnoreCase("yes")) {
                                storeNames.add(storeName);
                                updateStoreList(storeName);
                            }
                            int input = -1;
                            System.out.println("Enter '1' to add a store or '2' to finish adding stores.");
                            boolean inputTaken = false;
                            while (!inputTaken) {
                                try {
                                    input = Integer.parseInt(scan.nextLine());
                                    if (input == 1 || input == 2) {
                                        inputTaken = true;
                                    } else {
                                        System.out.println("Please enter '1' or '2' as input!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter '1' or '2' as input!");
                                }
                            }
                            if (input == 2 && !storeNames.isEmpty()) {
                                doneStores = true;
                            } else if (input == 2 && storeNames.isEmpty()) {
                                System.out.println("Sellers must have at least one store! Please add a store before continuing.");
                            }
                        }
                        fileStatus = writeFile(user, isSeller);
                        fileStatus = writeFile(user, storeNames.toString());
                    } else {
                        FileManager.generateDirectoryFromUsername(user, false);
                        isSeller = "false";
                        fileStatus = writeFile(user, isSeller);
                    }
                    if (!fileStatus) {
                        break;
                    }
                    done = true;
                }
                done = false;
                String email = null;
                System.out.println("Please enter an email to be associated with your account.");
                while (!done) {
                    email = scan.nextLine();
                    if (email.equals("") || !email.contains("@")) {
                        System.out.println("That's not a valid email! Please enter an email with a valid name and domain.");
                    } else {
                        done = true;
                    }
                }
                writeFile(user, email);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Please enter a valid String input!");
            }
        }
        if (!fileStatus) {
            System.out.println("An unknown error occurred!");
        }
        System.out.printf("Account created! Welcome, %s!%n", user);
    }

    /** Reads the password of the file for comparison
     *
     * @param user the user whose password is being read
     * @return String of the encrypted password
     */
    public static String readPassword(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
            String password = "";
            int index = 0;
            String line = br.readLine();
            while (line != null) {
                index++;
                if (index == 2) {
                    password = line;
                }
                line = br.readLine();
            }
            return (password);
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
        return (null);
    }

    /** Allows users to log in OR calls methods above and builds a file of the following format for a new user:
     *
     * username
     * password (encrypted)
     * isSeller (true or false)
     * [storeName(s)] array in String representation (included ONLY if user isSeller)
     * email address
     *
     * @return String of the user's name
     */
    public static String userInteraction(Scanner scan) {
        System.out.println("Welcome! Please enter 1 to log in or 2 to create a new account.");
        boolean done = false;
        int input = 0;
        while (!done) {
            try {
                input = Integer.parseInt(scan.nextLine());
                if (input == 1 || input == 2) {
                    done = true;
                }
            } catch (Exception e) {
                System.out.println("That's not a valid input! Please enter 1 to log in or 2 to create a new account.");
            }
        }
        if (input == 1) {
            boolean userFound = false;
            String user = "";
            done = false;
            while (!done) {
                System.out.println("Please enter your username.");
                user = scan.nextLine();
                if (user.equals("")) {
                    while (user.equals("")) {
                        System.out.println("Username cannot be blank! Please enter your username.");
                        user = scan.nextLine();
                    }
                }
                try {
                    File f = new File("users/" + user);
                    if (f.createNewFile()) {
                        f.delete();
                        System.out.println("That user doesn't exist! Press 1 to try again and 2 to exit.");
                        boolean continueUser = false;
                        while (!continueUser) {
                            try {
                                input = Integer.parseInt(scan.nextLine());
                                if (input == 2) {
                                    done = true;
                                    break;
                                } else if (input == 1) {
                                    continueUser = true;
                                } else {
                                    System.out.println("Please enter a valid input! Press 1 to try again and 2 to exit.");
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter a valid input! Press 1 to try again and 2 to exit.");
                            }
                        }
                    } else {
                        userFound = true;
                        done = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (userFound) {
                boolean loggedIn = false;
                System.out.println("Please enter your password");
                done = false;
                while (!done) {
                    try {
                        String passwordInput = scan.nextLine();
                        if (encrypt(passwordInput).equals(readPassword(user))) {
                            System.out.printf("Welcome, %s!%n", user);
                            done = true;
                            loggedIn = true;
                        } else {
                            boolean continuePassword = false;
                            while (!continuePassword) {
                                System.out.println("Incorrect password! Enter 1 to try again or 2 to exit.");
                                try {
                                    input = Integer.parseInt(scan.nextLine());
                                    if (input == 2) {
                                        done = true;
                                        break;
                                    } else if (input == 1) {
                                        continuePassword = true;
                                        System.out.println("Please enter your password");
                                    } else {
                                        System.out.println("Please enter a valid input!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter a valid input!");
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("An unknown error occurred!");
                    }
                }
                if (loggedIn) {
                    return (user);
                }
            }
        } else {
            done = false;
            System.out.println("Please enter a username.");
            while (!done) {
                try {
                    String user = scan.nextLine();
                    if (user.equals("")) {
                        while (user.equals("")) {
                            System.out.println("Username cannot be blank! Please enter a username.");
                            user = scan.nextLine();
                        }
                    }
                    if (user.contains(" ")) {
                        while (user.contains(" ")) {
                            System.out.println("Spaces are not permitted in usernames! Please enter a username without spaces.");
                            user = scan.nextLine();
                        }
                    }
                    createUser(user, scan);
                    done = true;
                    return (user);
                } catch (Exception e) {
                    System.out.println("Please enter a valid username!");
                }
            }
        }
        return (null);
    }

    /** Runs all LogIn methods to either create a user or log one in; afterwards, creates String ArrayList of user information
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String user = userInteraction(scan);
        if (user != null) {
            ArrayList<String> fileContents = new ArrayList();
            try (BufferedReader bfr = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
                int index = 0;
                String line = bfr.readLine();
                while (line != null) {
                    index++;
                    if (index != 2) {
                        fileContents.add(line);
                    }
                    line = bfr.readLine();
                }
            } catch (Exception e) {
                System.out.println("An unknown error occurred!");
            }
        } else {
            System.out.println("Goodbye!");
        }
        System.out.println("Enter '1' to edit your name, '2' to delete your account, '3' to change a store name, '4' to log in to one of your stores, or '5' to exit.");
        int input = -1;
        boolean inputTaken = false;
        while (!inputTaken) {
            try {
                input = Integer.parseInt(scan.nextLine());
                if (input == 1 || input == 2 || input == 3 || input == 4 || input == 5) {
                    inputTaken = true;
                } else {
                    System.out.println("Please enter '1,' '2,' '3,' '4,' or '5' as input!");
                }
            } catch (Exception e) {
                System.out.println("Please enter '1,' '2,' '3,' '4,' or '5' as input!");
            }
        }
        if (input == 2) {
            deleteUser(user, scan);
        } else if (input == 1) {
            appendUsername(user, scan);
        } else if (input == 3) {
            changeStoreName(user, scan);
        } else if (input == 4) {
            System.out.println("New user location: " + changeUserLocation(user, scan));
        } else {
            System.out.println("Goodbye!");
        }
    }
}
//TODO maybe add username and password confirmation?