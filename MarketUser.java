import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;


public class MarketUser implements User{
    private String username;
    private boolean isSeller;

    private boolean isUserStore;

    public LinkedHashMap<String,String> storeNameMap;

    public static void main(String[] args) {
        MarketUser mu = new MarketUser("storeTest",true, true);
        mu.message();
        MarketUser.changeStoreName("Walmart","Target");
    }

    /** Constructor creates new object with a username and tells object if it is a seller or not.
     * @param username The username associated with the user directory you wish to find
     * @param isSeller Tells class whether this instance is a seller or not
     */
    public MarketUser(String username, boolean isSeller, boolean isUserStore) {
        this.username = username;
        this.isSeller = isSeller;
        this.isUserStore = isUserStore;
    }

    /** A static method that will change the names of files and directories to match username
     * @param username the username that is currently stored everywhere
     */
    public static void deleteUsername(String username) {
        try {
            File f = new File(FileManager.getDirectoryFromUsername(username));
            String[] allFiles = f.list();
            for (String file : allFiles) {
                Files.delete(Paths.get((FileManager.getDirectoryFromUsername(username) + "/" + file)));
            }
            Files.delete(Paths.get(FileManager.getDirectoryFromUsername(username)));
        } catch (UserNotFoundException e) {
            System.out.println("Sorry, couldn't delete this user.");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /** A static method that will change the names of files and directories to match username
     * @param oldUsername the username that is currently stored everywhere
     * @param newUsername the new username that everything will be changed to
     */
    public static void changeUsername(String oldUsername, String newUsername) {
        File sellerDirectories = new File("data/sellers/");
        File buyerDirectories = new File("data/buyers/");
        String[] sellers = sellerDirectories.list();
        String[] buyers = buyerDirectories.list();
        for (String seller : sellers) {
            File currentSeller = new File("data/sellers/" + seller);
            String[] allFiles = currentSeller.list();
            for (String filename : allFiles) {
                File possibleStore = new File("data/sellers/" + seller + "/" + filename);
                if (possibleStore.isDirectory()) {
                    String[] storeFiles = possibleStore.list();
                    for (String storeFile : storeFiles) {
                        int indexOldUsername = storeFile.indexOf(oldUsername);
                        if (indexOldUsername >= 0) {
                            changeNameInFile(oldUsername, newUsername, "data/sellers/" + seller + "/"
                                    + filename + "/" + storeFile);
                            String newFilename;
                            newFilename = filename.substring(0,indexOldUsername) + newUsername + ".txt";
                            try {
                                Files.move(Paths.get("data/sellers/" + seller + "/"
                                        + filename + "/" + storeFile), Paths.get("data/sellers/" + seller + "/"
                                        + filename + "/" + newFilename));
                            } catch (IOException e) {
                                System.out.println("Sorry, failed to rename user!");
                            }
                        }
                    }
                } else {
                    int indexOldUsername = filename.indexOf(oldUsername);
                    if (indexOldUsername >= 0) {
                        changeNameInFile(oldUsername, newUsername, "data/sellers/" + seller + "/" + filename);
                        String newFilename;
                        if (indexOldUsername == 0) {
                            newFilename = newUsername + filename.substring(oldUsername.length());
                        } else {
                            newFilename = filename.substring(0, indexOldUsername) + newUsername + ".txt";
                        }
                        try {
                            Files.move(Paths.get("data/sellers/" + seller + "/" + filename), Paths.get("data/sellers/"
                                    + seller + "/" + newFilename));
                        } catch (IOException e) {
                            System.out.println("Sorry, failed to rename user!");
                        }
                    }
                }
            }
            if (seller.equals(oldUsername)) {
                try {
                    Files.move(Paths.get("data/sellers/" + seller), Paths.get("data/sellers/" + newUsername));
                } catch (IOException e) {
                    System.out.println("Sorry, failed to rename user!");
                }

            }
        }
        for (String buyer : buyers) {
            File currentBuyer = new File("data/buyers/" + buyer);
            String[] allFiles = currentBuyer.list();
            for (String filename : allFiles) {
                int indexOldUsername = filename.indexOf(oldUsername);
                if (indexOldUsername >= 0) {
                    changeNameInFile(oldUsername, newUsername, "data/buyers/" + buyer + "/" + filename);
                    String newFilename;
                    if (indexOldUsername == 0) {
                        newFilename = newUsername + filename.substring(oldUsername.length());
                    } else {
                        newFilename = filename.substring(0,indexOldUsername) + newUsername + ".txt";
                    }
                    try {
                        Files.move(Paths.get("data/buyers/" + buyer + "/" + filename), Paths.get("data/buyers/"
                                + buyer + "/" + newFilename));
                    } catch (IOException e) {
                        System.out.println("Sorry, failed to rename user!");
                    }
                }
            }
            if (buyer.equals(oldUsername)) {
                try {
                    Files.move(Paths.get("data/buyers/" + buyer), Paths.get("data/buyers/" + newUsername));
                } catch (IOException e) {
                    System.out.println("Sorry, failed to rename user!");
                }
            }
        }
        /* at this point the directory has been renamed, now go through all directories, find files that contain old
        username, go through files and change all the names and rename files */
    }

    public static void changeStoreName(String oldStoreName, String newStoreName) {
        File sellerDirectories = new File("data/sellers/");
        File buyerDirectories = new File("data/buyers/");
        String[] sellers = sellerDirectories.list();
        String[] buyers = buyerDirectories.list();
        for (String buyer : buyers) {
            File currentBuyer = new File("data/buyers/" + buyer);
            String[] allFiles = currentBuyer.list();
            for (String filename : allFiles) {
                int indexOldStoreName = filename.indexOf(oldStoreName);
                if (indexOldStoreName >= 0) {
                    changeNameInFile(oldStoreName, newStoreName, "data/buyers/" + buyer + "/" + filename);
                    String newFilename;
                    newFilename = filename.substring(0, indexOldStoreName) + newStoreName + ".txt";
                    try {
                        Files.move(Paths.get("data/buyers/" + buyer + "/" + filename), Paths.get("data/buyers/"
                                + buyer + "/" + newFilename));
                    } catch (IOException e) {
                        System.out.println("Sorry, failed to rename user!");
                    }
                }
            }
        }
        for (String seller : sellers) {
            File currentSeller = new File("data/sellers/" + seller);
            String[] allFiles = currentSeller.list();
            for (String filename : allFiles) {
                File possibleStore = new File("data/sellers/" + seller + "/" + filename);
                if (possibleStore.isDirectory() && filename.equals(oldStoreName)) {
                    String[] storeFiles = possibleStore.list();
                    for (String storeFile : storeFiles) {
                        int indexOldUsername = storeFile.indexOf(oldStoreName);
                        if (indexOldUsername >= 0) {
                            changeNameInFile(oldStoreName, newStoreName, "data/sellers/" + seller + "/"
                                    + filename + "/" + storeFile);
                            String newFilename;
                            newFilename = newStoreName + storeFile.substring(oldStoreName.length());
                            try {
                                Files.move(Paths.get("data/sellers/" + seller + "/"
                                        + filename + "/" + storeFile), Paths.get("data/sellers/" + seller + "/"
                                        + filename + "/" + newFilename));
                            } catch (IOException e) {
                                System.out.println("Sorry, failed to rename user!");
                            }
                        }
                    }
                    try {
                        Files.move(Paths.get("data/sellers/" + seller + "/" + oldStoreName),
                                Paths.get("data/sellers/" + seller + "/" + newStoreName));
                    } catch (Exception e) {
                        System.out.println("Sorry, unable to rename store!");
                    }
                }
            }
        }
    }


    /** A static method that will go through given file and replace old username with new username
     * @param oldUsername the username that is currently stored everywhere
     * @param newUsername the new username that everything will be changed to
     * @param filepath the file path of the file being modified
     */

    public static void changeNameInFile(String oldUsername, String newUsername, String filepath) {
        try (BufferedReader bfr = new BufferedReader(new FileReader(filepath))) {
            String line = bfr.readLine();
            ArrayList<String> fileContents = new ArrayList<>();
            while (line != null) {
                if (line.substring(0,oldUsername.length()).equals(oldUsername)) {
                    line = newUsername + line.substring(oldUsername.length());
                }
                fileContents.add(line);
                line = bfr.readLine();
            }
            PrintWriter pw = new PrintWriter(new FileOutputStream(filepath,false));
            for (String fileLine : fileContents) {
                pw.println(fileLine);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Method called when user is logged in, using System.out.println() will ask user if they want to message someone,
     * who they want to message, and what they want to message.
     */

    public void message() {
        storeNameMap = FileManager.mapStoresToSellers();

        Integer selection;
        String recipient = "";
        String proceed;
        boolean keepGoing = true;
        boolean isRecipientStore = false;
        Scanner scan = new Scanner(System.in);
        String buyOrSell = (isSeller)? "potential buyer" : "store or seller";
        System.out.println("Do you wish to contact, block, or unblock a " + buyOrSell + "? (Yes,No)");
        proceed = scan.nextLine();
        if (proceed.equalsIgnoreCase("yes")) {
            do {
                if (isSeller) { // if it is a seller, enter this statement
                    do { // keep prompting for a recipient until they either select a valid recipient, or cancel
                        System.out.println("Enter '1' to search for a buyer, enter '2' to see a list of buyers," +
                                "or enter any number to cancel:");
                        selection = null;
                        do {
                            try {
                                selection = scan.nextInt();
                                scan.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid number:");
                                scan.nextLine();
                            }
                        } while (selection == null);
                        if (selection == 1) { // if the user wants to search for a buyer, enter this statement
                            System.out.println("Enter the username of a buyer:");
                            recipient = scan.nextLine();
                            if (FileManager.checkBuyerExists(recipient)) {
                                break; // at this point we know that the variable 'recipient' contains a valid username
                            } else {
                                System.out.println("Sorry! This buyer does not exist!");
                            }
                        } else if (selection == 2) { // if the user wants to see a list of people to contact
                            try {
                                String[] allAvailableUsers = getAvailableUsers();
                                for (int i = 0; i < allAvailableUsers.length; i++) {
                                    System.out.println((i + 1) + ". " + allAvailableUsers[i]);
                                }
                                System.out.println(allAvailableUsers.length + 1 + ": Cancel");
                                System.out.println("Make a selection:");
                                selection = null;
                                do {
                                    try {
                                        selection = scan.nextInt();
                                        scan.nextLine();
                                        if ((selection < 1 || selection > allAvailableUsers.length + 1)) {
                                            System.out.println("Please enter a valid number:");
                                            selection = null;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Please enter a valid number:");
                                        scan.nextLine();
                                    }
                                } while (selection == null);
                                if (selection != allAvailableUsers.length + 1) {
                                    recipient = allAvailableUsers[selection - 1];
                                    break; // at this point we again know that the variable 'recipient' contains a valid username
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Sorry, there was an error reading the users, please try again!");
                            }
                        } else {
                            proceed = ""; // set proceed to not 'yes' so that we return out of function
                            break;
                        }
                    } while (true);
                } else { // lots of repeated code here, but I think needed because of slightly different print statements
                    do { // keep prompting for a recipient until they either select a valid recipient, or cancel
                        System.out.println("Enter '1' to search for a seller, enter '2' to see a list of stores, " +
                                "or enter any number to cancel:");
                        selection = null;
                        do {
                            try {
                                selection = scan.nextInt();
                                scan.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Please enter a valid number:");
                                scan.nextLine();
                            }
                        } while (selection == null);
                        if (selection == 1) { // if the user wants to search for a store, enter this statement
                            System.out.println("Enter the name of a seller:");
                            recipient = scan.nextLine();
                            if (FileManager.checkSellerExists(recipient)) {
                                break; // at this point we know that the variable 'recipient' contains a valid username
                            } else {
                                System.out.println("Sorry! This seller does not exist!");
                            }
                        } else if (selection == 2) { // if the user wants to see a list of people to contact
                            try {
                                String[] allAvailableStores = getAvailableStores();
                                for (int i = 0; i < allAvailableStores.length; i++) {
                                    System.out.println((i + 1) + ". " + allAvailableStores[i]);
                                }
                                System.out.println(allAvailableStores.length + 1 + ": Cancel");
                                System.out.println("Make a selection:");
                                selection = null;
                                do {
                                    try {
                                        selection = scan.nextInt();
                                        scan.nextLine();
                                        if ((selection < 1 || selection > allAvailableStores.length + 1)) {
                                            System.out.println("Please enter a valid number:");
                                            selection = null;
                                        }
                                    } catch (InputMismatchException e) {
                                        System.out.println("Please enter a valid number:");
                                        scan.nextLine();
                                    }
                                } while (selection == null);
                                if (selection != allAvailableStores.length + 1) {
                                    recipient = allAvailableStores[selection - 1];
                                    isRecipientStore = true;
                                    break; // at this point we again know that the variable 'recipient' contains a valid username
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Sorry, there was an error reading the users, please try again!");
                            }
                        } else {
                            proceed = ""; // set proceed to not 'yes' so that we return out of function
                            break;
                        }
                    } while (true);

                }
                // after this statement we know that String recipient contains a valid value
                checkIfMessageExists(recipient, isRecipientStore); // this will check if message has already been created and create if not

                System.out.printf("Connected with %s!\nPlease select an option:\n", recipient);
                boolean stayConnected;
                do {
                    System.out.println("1. View message history\n" +
                            "2. Send a message\n" +
                            "3. Edit a message\n" +
                            "4. Delete a message\n" +
                            "5. Block this " + ((this.isSeller) ? "buyer\n" : "store\n") +
                            "6. Unblock this " + ((this.isSeller) ? "buyer\n" : "store\n") +
                            "7. Import a .txt file\n" +
                            "8. Export message as a .csv file\n" +
                            "9. Cancel");
                    selection = -1;
                    do {
                        try {
                            if (selection != -1) {
                                System.out.println("Please enter a valid number:");
                            }
                            selection = scan.nextInt();
                            scan.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid number:");
                            scan.nextLine();
                        }
                    } while ((selection < 1 || selection > 9));
                    switch (selection) {
                        case 1:
                            displayMessage(recipient);
                            break;
                        case 2:
                            if (!isRecipientStore && !isUserStore) {
                                appendMessage(recipient);
                            } else if (isRecipientStore) {
                                appendMessage(storeNameMap.get(recipient),recipient);
                            } else {
                                appendMessage(storeNameMap.get(username),recipient);
                            }
                            break;
                        case 3:
                            if (!isRecipientStore && !isUserStore) {
                                editMessage(recipient);
                            } else if (isRecipientStore) {
                                editMessage(storeNameMap.get(recipient),recipient);
                            } else {
                                editMessage(storeNameMap.get(username),recipient);
                            }
                            break;
                        case 4:
                            if (!isRecipientStore && !isUserStore) {
                                deleteMessage(recipient);
                            } else if (isRecipientStore) {
                                deleteMessage(storeNameMap.get(recipient),recipient);
                            } else {
                                deleteMessage(storeNameMap.get(username),recipient);
                            }
                            break;
                        case 5:
                            boolean alreadyBlocked = blockUser(recipient);
                            if(alreadyBlocked) {
                                System.out.println("Current user has already blocked " + recipient);
                            } else {
                                System.out.println("Successfully blocked " + recipient);
                            }
                            break;
                        case 6:
                            unblockUser(recipient);
                            break;
                        case 7:
                            String path;
                            System.out.println("Please enter the path to the text file you would like to import or " +
                                    "type 'cancel' to Cancel.");
                            do {
                                path = scan.nextLine();
                                if (path.equalsIgnoreCase("cancel")) {
                                    break;
                                }
                                if (path.endsWith(".txt")) {
                                    break;
                                }
                                System.out.println("Please enter a valid path");
                            } while (!path.endsWith(".txt"));
                            if (!path.equalsIgnoreCase("cancel")) {
                                importFile(path, recipient, isRecipientStore);
                            }
                        case 8:
                            System.out.println("Enter the path where you would like the csv file to be stored:");
                            String csvPath = scan.nextLine();
                            writeCSV(recipient, csvPath);

                    }
                    System.out.println("Would you like to complete another action with this user? (Yes,No)");
                    stayConnected = scan.nextLine().equals("yes");
                } while (stayConnected);
                buyOrSell = (isSeller) ? "potential buyer" : "store";
                System.out.println("Would you like to contact, block, or unblock another " + buyOrSell + "? (Yes,No)?");
                keepGoing = (scan.nextLine()).equalsIgnoreCase("yes");
            } while (keepGoing);
        }
        System.out.println("Thank you for using the messaging system!");
    }

    /**
     * Export message data as csv file
     * @param recipient person whom the message is with
     * @param path where the csv file will end up
     */

    public void writeCSV(String recipient, String path) {
        if (!path.endsWith("/")) {
            path += "/";
        }
        String filepath;
        File f = new File(path + username + ".csv");
        if (isUserStore) {
            filepath = FileManager.getStoreDirectory(storeNameMap.get(username),username);
        } else if (isSeller) {
            filepath = "data/sellers/" + username + "/";
        } else {
            filepath = "data/buyers/" + username + "/";
        }
        try {
            f.createNewFile();
            PrintWriter pw = new PrintWriter(new FileWriter(f,false));
            pw.println("Name:,Date:,Time:,Message:");
            BufferedReader bfr = new BufferedReader(new FileReader(filepath + username + recipient + ".txt"));
            String line = bfr.readLine();
            while (line != null) {
                String name = line.substring(0,line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
                String date = line.substring(0,line.indexOf(" "));
                line = line.substring(line.indexOf(" ") + 1);
                String time = line.substring(0,line.indexOf(" ") - 1);
                String message = line.substring(line.indexOf(" ") + 1);
                pw.println(name + "," + date + "," + time + "," + message);
                line = bfr.readLine();
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get a list of users that this user can message
     * @return an array of available people for messaging
     * @throws IOException if error
     */
    public String[] getAvailableUsers() throws IOException {
        ArrayList<String> available = new ArrayList<>();
        if(!isSeller){
            File sellersDir = new File("data/sellers");
            String[] sellers = sellersDir.list();
            for(String seller: sellers) {
                File sellerFolder = new File("data/sellers/" + seller);
                String[] sellerFiles = sellerFolder.list();
                File invisibleFilePath = new File("data/sellers/" + seller + "/" + sellerFiles[0] + "/isInvisible.txt");
                BufferedReader bfr = new BufferedReader(new FileReader(invisibleFilePath));
                String line;
                boolean invisible = false;
                while((line = bfr.readLine())!= null) {
                    if(line.equals(this.username)) {
                        invisible = true;
                        break;
                    }
                }
                bfr.close();
                if(!invisible) {
                    available.add(seller);
                }
            }
        } else {
            File buyersDir = new File("data/buyers");
            String[] buyers = buyersDir.list();
            for(String buyer: buyers) {
                File invisibleFilePath = new File("data/buyers/" + buyer + "/isInvisible");
                BufferedReader bfr = new BufferedReader(new FileReader(invisibleFilePath));
                String line;
                boolean invisible = false;
                while((line = bfr.readLine())!= null) {
                    if(line.equals(this.username)) {
                        invisible = true;
                        break;
                    }
                }
                bfr.close();
                if(!invisible) {
                    available.add(buyer);
                }
            }
        }
        //Just turn ArrayList into array classic 180 stuff
        String[] availables = new String[available.size()];
        for(int i = 0; i < availables.length; i++) {
            availables[i] = available.get(i);
        }
        return availables;
    }

    /**
     * Get a list of users that this user can message
     * @return an array of available people for messaging
     * @throws IOException in case of error
     */
    public String[] getAvailableStores() throws IOException {
        boolean invisible = true;
        String[] possibleSellers = getAvailableUsers();
        File sellers = new File("data/sellers");
        ArrayList<String> possibleStores = new ArrayList<>();
        String[] sellerNames = sellers.list();
        for (String name : sellerNames) {
            for (String seller : possibleSellers) {
                if (seller.equals(name)) {
                    invisible = false;
                    break;
                }
            }
            if (!invisible) {
                File stores = new File("data/sellers/" + name);
                String[] storeNames = stores.list();
                for (String store : storeNames) {
                    File storeFile = new File("data/sellers/" + name + "/" + store);
                    if (storeFile.isDirectory()) {
                        possibleStores.add(store);
                    }
                }
            }
        }
        String[] availableStores = new String[possibleStores.size()];
        availableStores = possibleStores.toArray(availableStores);
        return availableStores;
    }

    /** Method to see if conversation has started
     * Check if <username><recipient>.txt exists because if it exists then <recipient><username>.txt also exists
     * if nto create both files
     * @param recipient The username associated with the person the user wishes to measure
     */
    public void checkIfMessageExists(String recipient, boolean isRecipientStore) {// check if <username><recipient>.txt exits in directory or not
        if (!isRecipientStore) {
            String path1 = "";
            String path2 = "";
            if (isSeller) {
                path1 = "data/sellers/" + username + "/";
                path2 = "data/buyers/" + recipient + "/";
                if (isUserStore) {
                    path1 = "data/sellers/" + storeNameMap.get(username) + "/" + username + "/";
                }
            } else {
                path1 = "data/buyers/" + username + "/";
                path2 = "data/sellers/" + recipient + "/";
            }
            try {
                File fUser = new File(path1 + username + recipient + ".txt");
                boolean didCreate = fUser.createNewFile();
                if (didCreate) {
                    File fRecipient = new File(path2 + recipient + username + ".txt");
                    fRecipient.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                File fUser = new File(FileManager.getDirectoryFromUsername(username)
                        + "/" + username + recipient + ".txt");
                boolean didCreate = fUser.createNewFile();
                if (didCreate) {
                    String sellerName = storeNameMap.get(recipient);
                    File fRecipient = new File("data/sellers/" + sellerName
                            + "/" + recipient +"/" + recipient + username + ".txt");
                    fRecipient.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates filepath to message files and calls append execution
     *
     * @param recipient user to send to
     *
     * @author John Brooks
     */

    public void appendMessage(String recipient) {

        String fileRecipient = "";
        String fileSender = "";

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
        appendMessageExecute(recipient, fileSender, fileRecipient);
    }

    /**
     * Creates filepath to message files and calls append execution, overloaded in the case of
     * messaging a store
     *
     * @param seller seller associated with store
     * @param recipient name of store or recipient
     *
     * @author John Brooks
     */

    public void appendMessage(String seller, String recipient) {
        String fileRecipient;
        String fileSender;
        if (!isUserStore) { // this means recipient is the storeName
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + seller + "/" + recipient + "/";
            appendMessageExecute(recipient, fileSender, fileRecipient);
        } else {
            fileSender = "data/sellers/" + seller + "/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
            appendMessageExecute(recipient, fileSender, fileRecipient);
        }
    }

    /**
     * Simply displays message contents
     *
     * @param recipient receiver of message
     * @author Kevin Jones
     */
    public void displayMessage(String recipient) {
        String path = "";
        if (isUserStore) {
            path = FileManager.getStoreDirectory(storeNameMap.get(username),username) + "/";
        } else {
            try {
                path = FileManager.getDirectoryFromUsername(recipient);
            } catch (UserNotFoundException u) {
                System.out.println("Unable to load message1");
            }
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(path + username + recipient + ".txt"))) {
            String line = bfr.readLine();
            while (line != null) {
                System.out.println(line);
                line = bfr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to load message");
        }
    }

    /**
     * Executes appending by prompting for and adding message to both files given
     *
     * @param recipient receiver of message
     * @param fileSender is path to Senders file
     * @param fileRecipient is path to Receivers file
     *
     * @author John Brooks
     */
    public void appendMessageExecute(String recipient, String fileSender, String fileRecipient) {
        String message;
        String printFile;

        File senderF = new File(fileSender + username + recipient + ".txt");
        File recipientF = new File(fileRecipient + recipient + username + ".txt");
        if (senderF.exists() && recipientF.exists()) {
            try {
                //display prior to adding
                BufferedReader display = new BufferedReader(new FileReader(senderF));
                printFile = display.readLine();
                while (printFile != null) {
                    System.out.println(printFile);
                    printFile = display.readLine();
                }
                FileOutputStream fosSend = new FileOutputStream(senderF, true);
                PrintWriter messageSenderWriter = new PrintWriter(fosSend);
                FileOutputStream fosReceive = new FileOutputStream(recipientF, true);
                PrintWriter messageReceiveWriter = new PrintWriter(fosReceive);
                Scanner scan = new Scanner(System.in);
                System.out.print(username + "- ");
                message = scan.nextLine();
                //write it on the end of each person's file
                String timeStamp = new SimpleDateFormat("MM/dd HH:mm:ss").format(new java.util.Date());
                messageSenderWriter.println(username + " " + timeStamp + "- " + message);
                messageReceiveWriter.println(username + " " + timeStamp + "- " + message);
                display.close();
                messageSenderWriter.close();
                messageReceiveWriter.close();
                if (!isSeller) {
                    MetricManager.addDeleteMessageData(username, fileSender, message, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prepares to execute edit by forming file paths
     *
     * @param recipient receiver of message
     *
     * @author John Brooks
     */
    public void editMessage(String recipient) {

        String fileRecipient = "";
        String fileSender = "";

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
        editMessageExecute(recipient, fileSender, fileRecipient);
    }

    /**
     * Prepares to execute edit by forming file paths with store name in case of overload
     *
     * @param recipient receiver of message
     * @param seller name of store
     *
     * @author John Brooks
     */
    public void editMessage(String seller, String recipient) {
        String fileRecipient;
        String fileSender;
        if (!isUserStore) { // this means recipient is the store
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + seller + "/" + recipient + "/";
            editMessageExecute(recipient, fileSender, fileRecipient);
        } else {
            fileSender = "data/sellers/" + seller + "/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
            System.out.println(fileSender + "\n" + fileRecipient);
            editMessageExecute(recipient, fileSender, fileRecipient);
        }
    }


    /**
     * Searches file for index that matches one given by the user and changes that line and
     * writes it back to the file
     *
     * @param recipient receives file
     * @param fileSender sender file path
     * @param fileRecipient recipient file path
     *
     * @author John Brooks
     */
    public void editMessageExecute(String recipient, String fileSender, String fileRecipient) {
        String message;
        String printFile;
        int count = 0;
        int ind = -1;
        int flag;

        File senderF = new File(fileSender + username + recipient + ".txt");
        File recipientF = new File(fileRecipient + recipient + username + ".txt");

        ArrayList<String> readSenderFile = new ArrayList<>();
        ArrayList<String> readReceiverFile = new ArrayList<>();

        if (senderF.exists() && recipientF.exists()) {
            try {
                //initial display
                BufferedReader display = new BufferedReader(new FileReader(senderF));
                BufferedReader buffReceiver = new BufferedReader(new FileReader(recipientF));
                printFile = display.readLine();
                while (printFile != null) {
                    count++;
                    readSenderFile.add(printFile);
                    System.out.println(count + ": " + printFile);
                    printFile = display.readLine();
                }

                String line2 = buffReceiver.readLine();
                while (line2 != null) {
                    readReceiverFile.add(line2);
                    line2 = buffReceiver.readLine();
                }

                if (count > 0) {
                    FileOutputStream fosSend = new FileOutputStream(senderF, false);
                    PrintWriter messageSenderWriter = new PrintWriter(fosSend);
                    FileOutputStream fosReceive = new FileOutputStream(recipientF, false);
                    PrintWriter messageReceiveWriter = new PrintWriter(fosReceive);
                    Scanner scan = new Scanner(System.in);
                    //acquiring index
                    System.out.println("Which index would you like to change?");
                    do {
                        flag = 0;
                        try {
                            ind = Integer.parseInt(scan.nextLine());
                        } catch (NumberFormatException n) {
                            flag++;
                        }
                        if (flag == 1 || (ind < 1 || ind > count))
                            System.out.println("Your index must be a number and must be available. Try again:");
                    } while (flag == 1 || (ind < 1 || ind > count));
                    System.out.println("What would you like the new version to say?");
                    String edit = scan.nextLine();

                    String messageToChange = "";

                    // runs through senders file to find index and change to edit
                    String extractNameAndTime;
                    for (int i = 0; i < readSenderFile.size(); i++) {
                        if (i == ind - 1) {
                            extractNameAndTime = readSenderFile.get(i).substring(0, readSenderFile.get(i).indexOf("-") + 1);
                            // this stores the message prior to editing so it can be found in the receivers file
                            messageToChange = readSenderFile.get(i);
                            edit = extractNameAndTime + " " + edit;
                            readSenderFile.set(i, edit);
                        }
                    }
                    // runs through the receivers file and finds the message to change, and changes it
                    for (int i = 0; i < readReceiverFile.size(); i++) {
                        if (readReceiverFile.get(i).equals(messageToChange)) {
                            readReceiverFile.set(i, edit);
                        }
                    }

                    //write back to files
                    for (int i = 0; i < readSenderFile.size(); i++) {
                        messageSenderWriter.println(readSenderFile.get(i));
                    }
                    for (int i = 0; i < readReceiverFile.size(); i++) {
                        messageReceiveWriter.println(readReceiverFile.get(i));
                    }
                    display.close();
                    buffReceiver.close();
                    messageSenderWriter.close();
                    messageReceiveWriter.close();
                    if (!isSeller) {
                        messageToChange = messageToChange.substring(messageToChange.indexOf("-") + 1);
                        MetricManager.editMessageData(username, fileSender, messageToChange, edit);
                    }
                } else
                    System.out.println("There is nothing in this file to edit.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Prepares to execute delete by forming file paths
     *
     * @param recipient receiver of message
     *
     * @author John Brooks
     */
    public void deleteMessage(String recipient) {

        String fileRecipient = "";
        String fileSender = "";

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
        deleteMessageExecute(recipient, fileSender, fileRecipient);
    }

    /**
     * Prepares to execute delete by forming file paths with store name in case of overload
     *
     * @param recipient receiver of message
     * @param seller name of store
     *
     * @author John Brooks
     */
    public void deleteMessage(String seller, String recipient) {
        String fileRecipient;
        String fileSender;
        if (!isUserStore) { // this means recipient is the storeName
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + seller + "/" + recipient + "/";
            deleteMessageExecute(recipient, fileSender, fileRecipient);
        } else {
            fileSender = "data/sellers/" + seller + "/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
            deleteMessageExecute(recipient, fileSender, fileRecipient);
        }
    }

    /**
     * Searches file for index that matches one given by the user to not add it to an arraylist and
     * therefore not write it to the file
     *
     * @param recipient receiver of message
     *
     * @author John Brooks
     */

    public void deleteMessageExecute(String recipient, String fileSender, String fileRecipient) {
        String printFile;
        int count = 0;
        int flag;
        int indexOfDelete = -1;
        String message = "";

        File senderF = new File(fileSender + username + recipient + ".txt");
        File recipientF = new File(fileRecipient + recipient + username + ".txt");
        ArrayList<String> readSenderFile = new ArrayList<>();

        if (senderF.exists() && recipientF.exists()) {
            try {
                //display
                BufferedReader display = new BufferedReader(new FileReader(senderF));
                printFile = display.readLine();
                while (printFile != null) {
                    count++;
                    readSenderFile.add(printFile);
                    System.out.println(count + ": " + printFile);
                    printFile = display.readLine();
                }
                if (count > 0) {
                    FileOutputStream fosSend = new FileOutputStream(senderF, false);
                    PrintWriter messageSenderWriter = new PrintWriter(fosSend);
                    Scanner scan = new Scanner(System.in);
                    //get index of delete
                    do {
                        flag = 0;
                        try {
                            indexOfDelete = Integer.parseInt(scan.nextLine());
                        } catch (NumberFormatException n) {
                            flag++;
                        }
                        if (flag == 1 || (indexOfDelete < 1 || indexOfDelete > count))
                            System.out.println("Your index must be a number and must be available. Try again:");
                    } while (flag == 1 || (indexOfDelete < 1 || indexOfDelete > count));
                    // loop through, write to file if it is not the delete index
                    for (int i = 0; i < readSenderFile.size(); i++) {
                        if (i + 1 != indexOfDelete) {
                            messageSenderWriter.println(readSenderFile.get(i));
                        } else
                            message = readSenderFile.get(i).substring(readSenderFile.get(i).indexOf("-") + 1);
                    }
                    display.close();
                    messageSenderWriter.close();
                    if (!isSeller) {
                        MetricManager.addDeleteMessageData(username, fileSender, message, true);
                    }
                } else
                    System.out.println("There is nothing in this file to delete.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * imports .txt file if path provided is valid
     * @param path: path of file
     * @param recipient recipient of message
     * @param isRecipientStore whether recipient is store or not
     */

    public void importFile(String path, String recipient, boolean isRecipientStore) {
        // set up paths to correct files
        String fileSender;
        String fileReceiver;
        if (isUserStore) {
            fileSender = FileManager.getStoreDirectory(storeNameMap.get(username),username);
            fileReceiver = "data/buyers/" + recipient + "/";
        } else if (isSeller) {
            fileReceiver = "data/buyers/" + recipient + "/";
            fileSender = "data/sellers/" + username + "/";
        } else if (isRecipientStore) {
            fileSender = "data/buyers/" + username + "/";
            fileReceiver = FileManager.getStoreDirectory(storeNameMap.get(recipient),recipient);
        } else {
            fileReceiver = "data/sellers/" + recipient + "/";
            fileSender = "data/buyers/" + username + "/";
        }

        fileReceiver += recipient + username + ".txt";
        fileSender += username + recipient + ".txt";

        File senderFile = new File(fileSender);
        File receiverFile = new File(fileReceiver);
        File importFile = new File(path);
        try (BufferedReader bfr = new BufferedReader(new FileReader(importFile))) {
            PrintWriter pwReceiver = new PrintWriter(new FileWriter(receiverFile,true));
            PrintWriter pwSender = new PrintWriter(new FileWriter(senderFile,true));

            String timeStamp = new SimpleDateFormat("MM/dd HH:mm:ss").format(new java.util.Date());

            String line = bfr.readLine();
            while (line != null) {
                pwSender.print(username + " " + timeStamp + "- ");
                pwReceiver.print(username + " " + timeStamp + "- ");
                pwReceiver.println(line);
                pwSender.println(line);
                line = bfr.readLine();
            }
            pwReceiver.close();
            pwSender.close();
        } catch (IOException e) {
            System.out.println("Error reading file!");
        }

    }

    /**
     * Become invisible to a user if not already invisible
     * @param username: name of person to become invisible to
     * @return true if already invisible, false otherwise
     * @throws IOException
     */
    public boolean becomeInvisibleToUser(String username) {
        try {
            if(!isSeller) {
                String invisibleFilePath = "data/buyers/" + this.username + "/isInvisible.txt";
                File invisibleFile = new File(invisibleFilePath);
                BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (line.equals(username)) {
                        //Already blocked this user
                        return true;
                    }
                }
                bfr.close();
                //Write the name of the victim to hasBlocked file
                PrintWriter pw = new PrintWriter(new FileWriter(invisibleFile, true));
                pw.write(username);
                pw.println();
                pw.flush();
                pw.close();
                return false;
            } else {
                File seller = new File("data/sellers/" + this.username);
                String[] sellerFile = seller.list();
                for(String fileName: sellerFile) {
                    File file = new File("data/sellers/" + this.username +"/" + fileName);
                    if(file.isDirectory()) {
                        File invisibleFile = new File("data/sellers/" + this.username +"/" + fileName + "/isInvisible.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            if (line.equals(username)) {
                                //Store already blocked this user
                                return true;
                            }
                        }
                        bfr.close();
                        //Write the name of the victim to hasBlocked file
                        PrintWriter pw = new PrintWriter(new FileWriter(invisibleFile, true));
                        pw.write(username);
                        pw.println();
                        pw.flush();
                        pw.close();
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Return a list of blocked users for this user
     * @return array of blocked usernames
     * @throws IOException
     */
    public String[] blockedList() throws IOException{
        ArrayList<String> victims = new ArrayList<>();
        if(!isSeller) {
            String blockedFilePath = "data/buyers/" + this.username + "hasBlocked.txt";
            File blockedFile = new File(blockedFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                victims.add(line);
            }
            String[] blockedList = new String[victims.size()];
            for (int i = 0; i < victims.size(); i++) {
                blockedList[i] = victims.get(i);
            }
            return blockedList;
        } else {
            File seller = new File("data/sellers/" + this.username);
            String[] sellerFile = seller.list();
            File blockedFile = new File("data/sellers/" + this.username + "/" + sellerFile[0] + "/hasBlocked.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                victims.add(line);
            }
            String[] blockedList = new String[victims.size()];
            for (int i = 0; i < victims.size(); i++) {
                blockedList[i] = victims.get(i);
            }
            return blockedList;
        }
    }

    /**
     * Become visible to a user from the isInvisible file
     * return
     * @param username: name of person to unblock
     */
    public void becomeVisibleAgain(String username) {
        try {
            if(!isSeller) {
                ArrayList<String> lines = new ArrayList<>();
                String invisibleFilePath = "data/buyers/" + this.username + "/isInvisible.txt";
                File invisibleFile = new File(invisibleFilePath);
                BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (!line.equals(username)) {
                        lines.add(line);
                    }
                }
                bfr.close();
                PrintWriter pw = new PrintWriter(new FileWriter(invisibleFile, false));
                for (String l : lines) {
                    pw.write(l);
                    pw.println();
                }
                pw.flush();
                pw.close();
            } else {
                File seller = new File("data/sellers/" + this.username);
                String[] sellerFile = seller.list();
                for(String fileName: sellerFile) {
                    File file = new File("data/sellers/" + this.username + "/" + fileName);
                    if (file.isDirectory()) {
                        ArrayList<String> lines = new ArrayList<>();
                        File invisibleFile = new File("data/sellers/" + this.username +"/" + fileName + "/isInvisible.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            if (!line.equals(username)) {
                                lines.add(line);
                            }
                        }
                        bfr.close();
                        PrintWriter pw = new PrintWriter(new FileWriter(invisibleFile, false));
                        for (String l : lines) {
                            pw.write(l);
                            pw.println();
                        }
                        pw.flush();
                        pw.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return list of people that can't see this user (and his stores)
     * @return array of people that can't see this user (and his stores)
     * @throws IOException
     */
    public String[] invisibleList() throws IOException{
        ArrayList<String> victims = new ArrayList<>();
        if(!isSeller) {
            String invisibleFilePath = "data/buyers/" + this.username + "isInvisible.txt";
            File invisibleFile = new File(invisibleFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                victims.add(line);
            }
            String[] invisibleList = new String[victims.size()];
            for (int i = 0; i < victims.size(); i++) {
                invisibleList[i] = victims.get(i);
            }
            return invisibleList;
        } else {
            File seller = new File("data/sellers/" + this.username);
            String[] sellerFile = seller.list();
            File invisibleFile = new File("data/sellers/" + this.username + "/" + sellerFile[0] + "/isInvisible.txt");
            BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                victims.add(line);
            }
            String[] invisibleList = new String[victims.size()];
            for (int i = 0; i < victims.size(); i++) {
                invisibleList[i] = victims.get(i);
            }
            return invisibleList;
        }
    }

    /**
     * Blocked a user if not already blocked
     * @param username
     * @return true if already blocked, false otherwise
     */
    public boolean blockUser(String username) {
        try {
            if(!isSeller) {
                String blockedFilePath = "data/buyers/" + this.username + "/hasBlocked.txt";
                File blockedFile = new File(blockedFilePath);
                BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (line.equals(username)) {
                        //Already blocked this user
                        return true;
                    }
                }
                bfr.close();
                //Write the name of the victim to hasBlocked file
                PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, true));
                pw.write(username);
                pw.println();
                pw.flush();
                pw.close();
                return false;
            } else {
                File seller = new File("data/sellers/" + this.username);
                String[] sellerFile = seller.list();
                for(String fileName: sellerFile) {
                    File file = new File("data/sellers/" + this.username +"/" + fileName);
                    if(file.isDirectory()) {
                        File blockedFile = new File("data/sellers/" + this.username +"/" + fileName + "/hasBlocked.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            if (line.equals(username)) {
                                //Store already blocked this user
                                return true;
                            }
                        }
                        bfr.close();
                        //Write the name of the victim to hasBlocked file
                        PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, true));
                        pw.write(username);
                        pw.println();
                        pw.flush();
                        pw.close();
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * unblocked a user listed in the blockedList
     * @param username
     */
    public void unblockUser(String username) {
        try {
            if(!isSeller) {
                ArrayList<String> lines = new ArrayList<>();
                String blockedFilePath = "data/buyers/" + this.username + "/hasBlocked.txt";
                File blockedFile = new File(blockedFilePath);
                BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (!line.equals(username)) {
                        lines.add(line);
                    }
                }
                bfr.close();
                PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, false));
                for (String l : lines) {
                    pw.write(l);
                    pw.println();
                }
                pw.flush();
                pw.close();
            } else {
                File seller = new File("data/sellers/" + this.username);
                String[] sellerFile = seller.list();
                for(String fileName: sellerFile) {
                    File file = new File("data/sellers/" + this.username + "/" + fileName);
                    if (file.isDirectory()) {
                        ArrayList<String> lines = new ArrayList<>();
                        File blockedFile = new File("data/sellers/" + this.username +"/" + fileName + "/hasBlocked.txt");
                        BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
                        String line;
                        while ((line = bfr.readLine()) != null) {
                            if (!line.equals(username)) {
                                lines.add(line);
                            }
                        }
                        bfr.close();
                        PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, false));
                        for (String l : lines) {
                            pw.write(l);
                            pw.println();
                        }
                        pw.flush();
                        pw.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
