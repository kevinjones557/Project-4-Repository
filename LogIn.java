import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class to create accounts for users, log them in, and perform any necessary interaction with said accounts.
 * <p>
 *
 * @author @Adenr4615
 * @version 11/13/22
 */

public class LogIn {

    /**
     * Writes user's username to a file
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

    /**
     * Appends an additional line to a given user's file
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

    /**
     * Checks to see if the given store name is already in use
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
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
        return (true);
    }

    /**
     * Returns the stores that a user has registered under their username
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
                    return (null);
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
            e.printStackTrace();
            return (null);
        }
    }

    /**
     * Returns if the user is a seller
     *
     * @param user user's username
     * @return String representation of the user's isSeller status
     */
    public static String isSeller(String user) {
        try (BufferedReader br = new BufferedReader(new FileReader("users/" + user + "/" + user))) {
            int lineIndex = 0;
            String line = br.readLine();
            while (line != null) {
                if (lineIndex == 2) {
                    return (line);
                }
                line = br.readLine();
                lineIndex++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
        return (null);
    }

    /**
     * Allows users to change any given store name
     *
     * @param user user's username
     * @param scan Scanner to capture input
     */
    public static void changeStoreName(String user, Scanner scan) {
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
                        System.out.println("That store was not found under your account! " +
                                "Please enter one of the following stores:");
                        System.out.println(storesArray);
                    }
                }
                System.out.println("Please enter the new store name.");
                String newName = scan.nextLine();
                boolean nameInUse = checkStoreList(newName);
                if (newName.equals("") || !nameInUse || newName.length() < 4 || newName.length() > 16) {
                    while (newName.equals("") || !nameInUse || newName.length() < 4 || newName.length() > 16) {
                        if (nameInUse || newName.equals("")) {
                            System.out.println("Store name constraints: " +
                                    "\n- Cannot be blank " +
                                    "\n- Must be in between 4 and 16 characters inclusive " +
                                    "\nPlease enter a valid store name.");
                        } else {
                            System.out.println("Name is already in use! Please pick a different name.");
                        }
                        newName = scan.nextLine();
                        nameInUse = checkStoreList(newName);
                    }
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
                    MarketUser.changeStoreName(storeToChange, newName);
                    System.out.println("Name change successful!");
                } catch (Exception e) {
                    System.out.println("Name change was not successful!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An unknown error occurred!");
            }
        } else {
            System.out.println("You are not a seller!");
        }
    }

    /**
     * Allows user to log in to an individual store account
     * Is not currently in use because Kevin is handling this
     *
     * @param user user logging in to their store
     * @param scan Scanner to capture input
     * @return the new location of the user after they logged in to a store
     */
    public static String changeUserLocation(String user, Scanner scan) {
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
                        System.out.println("That store was not found under your account! " +
                                "Please enter one of the following stores:");
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

    /**
     * Changes the user's name to their new desired name
     *
     * @param user the user changing their name
     * @param scan Scanner to capture input
     * @return String representation of new username
     */
    public static String appendUsername(String user, Scanner scan) {
        System.out.println("Please enter your new username!");
        String newUser = scan.nextLine();
        File dir = new File("users/" + newUser);
        try {
            boolean done = false;
            while (!done) {
                if (!dir.createNewFile()) {
                    System.out.println("Username already exists! Please enter another username.");
                    newUser = scan.nextLine();
                    dir.delete();
                    dir = new File("users/" + newUser);
                } else if (newUser.equals("") || newUser.length() < 6
                        || newUser.length() > 16 || newUser.contains(" ")) {
                    System.out.println("Username constraints: " +
                            "\n- Cannot be blank " +
                            "\n- Must be in between 6 and 16 characters inclusive " +
                            "\n- Cannot contain spaces " +
                            "\nPlease enter a valid username.");
                    newUser = scan.nextLine();
                    dir.delete();
                } else {
                    done = true;
                    dir.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
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
            return (newUser);
        } catch (Exception e) {
            System.out.println("Name change was not successful!");
        }
        return (null);
    }

    /**
     * Removes a deleted user's stores from the store list
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
                e.printStackTrace();
                System.out.println("An unknown error occurred!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
    }

    /**
     * Removes the name of a store that gets renamed
     *
     * @param store    the store being deleted
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
                e.printStackTrace();
                System.out.println("An unknown error occurred!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
    }

    /**
     * Deletes user's account from both central database and local account information database
     *
     * @param user user's username
     * @param scan Scanner to capture input
     */
    public static boolean deleteUser(String user, Scanner scan) {
        System.out.println("Are you sure you want to delete your account? This action cannot be undone. " +
                "\nEnter 'yes' to confirm or 'no' to abort.");
        String response = "";
        boolean userInput = false;
        while (!userInput) {
            try {
                response = scan.nextLine();
                if (response.equals("yes") || response.equals("no")) {
                    userInput = true;
                } else {
                    System.out.println("Please enter 'yes' or 'no'!");
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
            return (true);
        } else {
            System.out.println("We're glad you decided to stay!");
            return (false);
        }
    }

    /**
     * Updates the store list by adding a store name that has been confirmed to not be in use already
     *
     * @param storeName the store name being appended to the file
     */
    public static void updateStoreList(String storeName) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/storeNames", true))) {
            pw.println(storeName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
    }

    /**
     * Encrypts the password of the user file when an account is created
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
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
    }

    /**
     * Takes a password input by a user attempting to log in and uses the key to encrypt it for comparison
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

    /**
     * Creates the file and directory for a user only if the account doesn't already exist
     *
     * @param user the user whose file is being created
     * @param scan scanner object to capture input
     */
    public static String createUser(String user, Scanner scan) {
        File f;
        File dir = new File("users/" + user);
        try {
            boolean done = false;
            while (!done) {
                if (!dir.createNewFile()) {
                    dir.delete();
                    System.out.println("Username already exists! Please enter another username.");
                    user = scan.nextLine();
                    dir = new File("users/" + user);
                } else if (user.equals("") || user.length() < 6 || user.length() > 16 || user.contains(" ")) {
                    dir.delete();
                    System.out.println("Username constraints: " +
                            "\n- Cannot be blank " +
                            "\n- Must be in between 6 and 16 characters inclusive " +
                            "\n- Cannot contain spaces " +
                            "\nPlease enter a valid username.");
                    user = scan.nextLine();
                    dir = new File("users/" + user);
                } else {
                    done = true;
                }
            }
            dir.delete();
            Files.createDirectory(Paths.get("users/" + user));
            f = new File("users/" + user + "/" + user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
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
                            System.out.println("Password length must be between 8 and 16 characters! " +
                                    "Please enter a valid password.");
                            password = scan.nextLine();
                        }
                    }
                    fileStatus = writeFile(user, password);
                    if (!fileStatus) {
                        System.out.println("An unknown error occurred! Please try again.");
                    }
                    System.out.println("Please enter your password again to confirm it.");
                    String passwordToCheck = scan.nextLine();
                    if (passwordToCheck.equals(password)) {
                        encryptFile(user);
                        done = true;
                    } else {
                        System.out.println("Passwords did not match! Please try again.");
                    }
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
                            if (storeName.equals("") || !nameChecked ||
                                    storeName.length() < 4 || storeName.length() > 16) {
                                while (storeName.equals("") || !nameChecked ||
                                        storeName.length() < 4 || storeName.length() > 16) {
                                    if (nameChecked || storeName.equals("")) {
                                        System.out.println("Store name constraints: " +
                                                "\n- Cannot be blank " +
                                                "\n- Must be in between 4 and 16 characters inclusive " +
                                                "\nPlease enter a valid store name.");
                                    } else {
                                        System.out.println("Name is already in use! " +
                                                "Please pick a different name.");
                                    }
                                    storeName = scan.nextLine();
                                    nameChecked = checkStoreList(storeName);
                                }
                            }
                            System.out.println("Are you sure you want to add this store to your account? " +
                                    "This action cannot be undone. " +
                                    "\nEnter 'yes' to confirm or 'no' to abort.");
                            String addStore = "";
                            boolean storeInput = false;
                            while (!storeInput) {
                                try {
                                    addStore = scan.nextLine();
                                    if (addStore.equals("yes") || addStore.equals("no")) {
                                        storeInput = true;
                                    } else {
                                        System.out.println("Please enter 'yes' or 'no'!");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Please enter valid input!");
                                }
                            }
                            if (addStore.equalsIgnoreCase("yes")) {
                                storeNames.add(storeName);
                                updateStoreList(storeName);
                                FileManager.generateStoreForSeller(user, storeName);
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
                                System.out.println("Sellers must have at least one store! " +
                                        "Please add a store before continuing.");
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
                        System.out.println("That's not a valid email! " +
                                "Please enter an email with a valid name and domain.");
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
        return (user);
    }

    /**
     * Reads the password of the file for comparison
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
            e.printStackTrace();
            System.out.println("An unknown error occurred!");
        }
        return (null);
    }

    /**
     * Allows users to log in OR calls methods above and builds a file of the following format for a new user:
     * <p>
     * username (case insensitive)
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
                } else {
                    System.out.println("That's not a valid input! " +
                            "Please enter 1 to log in or 2 to create a new account.");
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
                                    return (null);
                                } else if (input == 1) {
                                    continueUser = true;
                                } else {
                                    System.out.println("Please enter a valid input! " +
                                            "Press 1 to try again and 2 to exit.");
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter a valid input! " +
                                        "Press 1 to try again and 2 to exit.");
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
                        e.printStackTrace();
                        System.out.println("An unknown error occurred!");
                    }
                }
                if (loggedIn) {
                    return (user);
                }
            }
        } else {
            done = false;
            String user = null;
            System.out.println("Please enter a username in between 6 and 16 characters inclusive.");
            while (!done) {
                try {
                    user = scan.nextLine();
                    user = createUser(user, scan);
                    done = true;
                } catch (Exception e) {
                    System.out.println("Please enter a valid username!");
                }
            }
            return (user);
        }
        return (null);
    }

    /**
     * Runs all LogIn methods to either create a user or log one in
     *
     * @param args
     */
    public static void main(String[] args) {
        Placeholders.delete();
        Scanner scan = new Scanner(System.in);
        String user = userInteraction(scan);
        if (user != null) {
            boolean isSeller = false;
            if (isSeller(user).equals("true")) {
                isSeller = true;
            } else if (isSeller(user).equals("false")) {
                isSeller = false;
            }
            MarketUser currentUser = new MarketUser(user, isSeller);
            boolean running = true;
            boolean userDeleted = false;
            while (running) {
                System.out.println("Would you like to enter user interaction or make account changes? " +
                        "\n1. User Interaction " +
                        "\n2. Account changes");
                int input = -1;
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
                if (input == 1) {
                    currentUser.message();
                } else {
                    System.out.println("Options: " +
                            "\n1. Edit your name" +
                            "\n2. Delete your account" +
                            "\n3. Change a store name" +
                            "\n4. Exit");
                    input = -1;
                    inputTaken = false;
                    while (!inputTaken) {
                        try {
                            input = Integer.parseInt(scan.nextLine());
                            if (input == 1 || input == 2 || input == 3 || input == 4) {
                                inputTaken = true;
                            } else {
                                System.out.println("Please enter '1,' '2,' '3,' or '4' as input!");
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter '1,' '2,' '3,' or '4' as input!");
                        }
                    }
                    if (input == 2) {
                        userDeleted = deleteUser(user, scan);
                        if (userDeleted) {
                            running = false;
                        }
                    } else if (input == 1) {
                        user = appendUsername(user, scan);
                    } else if (input == 3) {
                        changeStoreName(user, scan);
                    } else {
                        running = false;
                    }
                }
                if (!userDeleted && running) {
                    System.out.println("Would you like to continue using the program? " +
                            "\n1. Yes " +
                            "\n2. No");
                    inputTaken = false;
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
                    if (input == 2) {
                        running = false;
                        System.out.println("Thank you for using the messenger. Goodbye!");
                    }
                } else {
                    System.out.println("Thank you for using the messenger. Goodbye!");
                }
            }
        } else {
            System.out.println("Thank you for using the messenger. Goodbye!");
        }
    }
}