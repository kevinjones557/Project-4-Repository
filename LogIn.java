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
                // Make file in correct directory
                //TODO FileManager.generateDirectoryFromUsername(user,true);
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
                System.out.println("An unknown error occurred line 116!");
            }
        }
        boolean fileStatus = writeFile(user);
        boolean done = false;
        if (fileStatus) {
            try {
                while (!done) {
                    System.out.println("Please enter a password.");
                    String password = scan.nextLine();
                    fileStatus = writeFile(user, password);
                    if (!fileStatus) {
                        break;
                    }
                    encryptFile(user);
                    done = true;
                }
                done = false;
                System.out.println("Are you a seller? Please enter 'yes' or 'no.'");
                while (!done) {
                    String isSeller = scan.nextLine();
                    while (!isSeller.equalsIgnoreCase("yes") &&
                            !isSeller.equalsIgnoreCase("no")) {
                        System.out.println("Please enter 'yes' or 'no'!");
                        isSeller = scan.nextLine();
                    }
                    if (isSeller.equalsIgnoreCase("yes")) {
                        isSeller = "true";
                        System.out.println("Please enter your store name.");
                        String storeName = scan.nextLine();
                        fileStatus = writeFile(user, isSeller);
                        fileStatus = writeFile(user, storeName);
                    } else {
                        isSeller = "false";
                        fileStatus = writeFile(user, isSeller);
                    }
                    if (!fileStatus) {
                        break;
                    }
                    done = true;
                }
            } catch (Exception e) {
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
     * username
     * password (encrypted)
     * isSeller (true or false)
     * storeName (included ONLY is user isSeller)
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
            System.out.println("\nUser information:");
            //TODO Users will be created/accessed by the following information:
            System.out.printf("Username: %s%n" +
                            "isSeller: %s%n",
                    fileContents.get(0),
                    fileContents.get(1));
            if (fileContents.size() == 3) {
                System.out.println("Store Name: " + fileContents.get(2));
            }
        } else {
            System.out.println("Goodbye!");
        }
    }
}
//TODO prompt for email as well (check for @)
//TODO add edit and delete functionality
//TODO if user edits username, call MarketUser.changeUsername(oldUsername, newUsername) and then change username in yours as well
//TODO if user is a seller prompt to add stores, make sure sellers can't add store that already exists
//TODO make sure username doesn't have spaces