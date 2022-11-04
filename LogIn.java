import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LogIn {
    //writes user's username to a file
    public static boolean writeFile(String user) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(user))){
            pw.println(user);
            return (true);
        } catch (Exception e) {
            return (false);
        }
    }

    //writes user's password to a file, returns true for successes and false for failures
    public static boolean writeFile(String user, String toAppend) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(user, true))){
            pw.println(toAppend);
            return (true);
        } catch (Exception e) {
            return (false);
        }
    }

    //encrypts the password of the user file when an account is created
    public static void encryptFile (String user) {
        try (BufferedReader br = new BufferedReader(new FileReader(user))) {
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
                toBeEncrypted[i] += 5;
            }
            writeFile(user);
            String finalPassword = "";
            for (int i = 0; i < toBeEncrypted.length; i++) {
                finalPassword += toBeEncrypted[i];
            }
            writeFile(user, finalPassword);
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
    }

    //takes a password input by a user attempting to log in and uses the key to encrypt it for comparison
    public static String encrypt (String input) {
        String finalInput = "";
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] += 5;
        }
        for (int i = 0; i < inputArray.length; i++) {
            finalInput += inputArray[i];
        }
        return (finalInput);
    }

    //creates the file for a user only if it doesn't already exist
    public static void createUser(String user, Scanner scan) {
        File f = new File(user);
        try {
            if (!f.createNewFile()) {
                while (!f.createNewFile()) {
                    System.out.println("User already exists! Please enter another username.");
                    user = scan.nextLine();
                    f = new File(user);
                }
            }
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
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

    //reads the password of the file for comparison
    public static String readPassword (String user) {
        try (BufferedReader br = new BufferedReader(new FileReader(user))) {
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

    /*allows users to log in OR calls methods above and builds a file of the following format for a new user:
        username
        password (encrypted)
        isSeller (true or false)
        storeName (included ONLY is user isSeller)
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
                File F = new File(user);
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
                System.out.println("Please enter your password");
                done = false;
                while (!done) {
                    try {
                        String passwordInput = scan.nextLine();
                        if (encrypt(passwordInput).equals(readPassword(user))) {
                            System.out.println("Success! Logged in!");
                            done = true;
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
                return (user);
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
        return(null);
    }

    //runs all LogIn methods to either create a user or log one in; afterwards, creates String ArrayList of user information
    public static void main(String[] args) {
        String user = userInteraction();
        ArrayList<String> fileContents = new ArrayList();
        try (BufferedReader bfr = new BufferedReader(new FileReader(user))) {
            int index = 0;
            String line = bfr.readLine();
            while (line != null) {
                index++;
                if (index != 2) {
                    fileContents.add(line);
                }
                line = bfr.readLine();
            }
            System.out.println("User information:");
        } catch (Exception e) {
            System.out.println("An unknown error occurred!");
        }
        //TODO Users will be created/accessed by the following information:
        System.out.println(fileContents);
    }
}
