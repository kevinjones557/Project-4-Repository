import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LogIn {
    /**
     * Writes user's username to a file
     *
     * @param user String of the user's username
     * @return boolean of if file was successfully written or not
     */
    public static boolean writeFile(String user) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/" + user + "/" + user))) {
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
    public static boolean checkStoreList (String storeName) {
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

    /**
     * Updates the store list by adding a store name that has been confirmed to not be in use already
     *
     * @param storeName the store name being appended to the file
     */
    public static void updateStoreList (String storeName) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("users/storeNames", true))) {
            pw.println(storeName);
        } catch (Exception e) {
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
                        System.out.println("User already exists! Please enter another username.");
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
                    //TODO make user confirm store name
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
                                    input = scan.nextInt();
                                    scan.nextLine();
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
                            } else if (input == 2 && storeNames.isEmpty()){
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
            System.out.println("An unknown error occurred!");
        }
        return (null);
    }

    /**
     * Allows users to log in OR calls methods above and builds a file of the following format for a new user:
     *
     * username
     * password (encrypted)
     * isSeller (true or false)
     * [storeName(s)] array in String representation (included ONLY is user isSeller)
     * email address
     *
     * @return String of the user's name
     */
    public static String userInteraction() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome! Please enter 1 to log in or 2 to create a new account.");
        boolean done = false;
        int input = 0;
        while (!done) {
            try {
                input = scan.nextInt();
                scan.nextLine();
                if (input == 1 || input == 2) {
                    done = true;
                }
            } catch (Exception e) {
                System.out.println("That's not a valid input!");
            }
        }
        if (input == 1) {
            boolean userFound = false;
            String user = "";
            done = false;
            while (!done) {
                System.out.println("Please enter your username.");
                user = scan.nextLine();
                File F = new File("users/" + user + "/" + user);
                try {
                    if (F.createNewFile()) {
                        F.delete();
                        System.out.println("That user doesn't exist! Press 1 to try again and 2 to exit.");
                        boolean continueUser = false;
                        while (!continueUser) {
                            try {
                                input = scan.nextInt();
                                scan.nextLine();
                                if (input == 2) {
                                    done = true;
                                    break;
                                } else if (input == 1) {
                                    continueUser = true;
                                } else {
                                    System.out.println("Please enter a valid input!");
                                }
                            } catch (Exception e) {
                                System.out.println("Please enter a valid input!");
                            }
                        }
                    } else {
                        userFound = true;
                        done = true;
                    }
                } catch (Exception e) {
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
                                    input = scan.nextInt();
                                    scan.nextLine();
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

    /**
     * Runs all LogIn methods to either create a user or log one in; afterwards, creates String ArrayList of user information
     *
     * @param args
     */
    public static void main(String[] args) {
        String user = userInteraction();
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
    }
}
//TODO add edit and delete functionality
//TODO if user edits username, call MarketUser.changeUsername(oldUsername, newUsername) and then change username in yours as well
//TODO limit password length