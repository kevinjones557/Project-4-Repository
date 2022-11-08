import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;

public class MarketUser implements User{
    private String username;
    private boolean isSeller;

    public static void main(String[] args) {
        MarketUser mu = new MarketUser("john",false);
        mu.message();
        MarketUser.changeUsername("destin","pringles");
    }

    /** Constructor creates new object with a username and tells object if it is a seller or not.
     * @param username The username associated with the user directory you wish to find
     * @param isSeller Tells class whether this instance is a seller or not
     */
    public MarketUser(String username, boolean isSeller) {
        this.username = username;
        this.isSeller = isSeller;
    }

    /** A static method that will change the names of files and directories to match username
     * @param username the username that is currently stored everywhere
     */
    public static void deleteUsername(String username) throws UserNotFoundException{
        FileUtils.d
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
                int indexOldUsername = filename.indexOf(oldUsername);
                if (indexOldUsername >= 0) {
                    changeNameInFile(oldUsername, newUsername, "data/sellers/" + seller + "/" + filename);
                    String newFilename;
                    if (indexOldUsername == 0) {
                        newFilename = newUsername + filename.substring(oldUsername.length());
                    } else {
                        newFilename = filename.substring(0,indexOldUsername) + newUsername + ".txt";
                    }
                    try {
                        Files.move(Paths.get("data/sellers/" + seller + "/" + filename), Paths.get("data/sellers/"
                                + seller + "/" + newFilename));
                    } catch (IOException e) {
                        System.out.println("Sorry, failed to rename user!");
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
    /** Method called when user is logged in, using System.out.println() will ask user if they want to message someone,
     * who they want to message, and what they want to message.
     */
    public void message() {
        Integer selection;
        String recipient = "";
        String proceed;
        boolean keepGoing = true;
        Scanner scan = new Scanner(System.in);
        String buyOrSell = (isSeller)? "potential buyer" : "store";
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
                            if (checkIfRecipientExists(recipient)) {
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
                        System.out.println("Enter '1' to search for a store, enter '2' to see a list of stores, " +
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
                            System.out.println("Enter the name of a store:");
                            recipient = scan.nextLine();
                            if (checkIfRecipientExists(recipient)) {
                                break; // at this point we know that the variable 'recipient' contains a valid username
                            } else {
                                System.out.println("Sorry! This store does not exist!");
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

                }
                // after this statement we know that String recipient contains a valid value
                checkIfMessageExists(recipient); // this will check if message has already been created and create if not
                System.out.printf("Connected with %s!\nPlease select an option:\n", recipient);
                boolean stayConnected;
                do {
                    System.out.println("1. Send a message\n" +
                            "2. Edit a message\n" +
                            "3. Delete a message\n" +
                            "4. Block this " + ((this.isSeller) ? "buyer\n" : "store\n") +
                            "5. Unblock this " + ((this.isSeller) ? "buyer\n" : "store\n") +
                            "6. Import a .txt file\n" +
                            "7. Export message as a .csv file\n" +
                            "8. Cancel");
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
                    } while ((selection < 1 || selection > 8));
                    switch (selection) {
                        case 1 -> appendMessage(recipient);
                        case 2 -> editMessage(recipient);
                        case 3 -> deleteMessage(recipient);
                        case 4 -> blockUser(recipient);
                        case 5 -> unblockUser(recipient);
                        // TODO case 6 -> implement;
                        // TODO case 7 -> implement;
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
     * Get a list of users that this user can message
     * @return an array of available people for messaging
     * @throws IOException
     */
    public String[] getAvailableUsers() throws IOException {
        ArrayList<String> available = new ArrayList<>();
        String buyerOrSeller = "data/" + ((this.isSeller)? "buyers/": "sellers/");
        //Goes in the right directory
        File recipientType = new File("data/" + ((this.isSeller)? "buyers/": "sellers/"));
        String[] usernames = recipientType.list();
        //Loop through user directories
        for(String userDir : usernames) {
            File thatUserBlockedFile = new File(buyerOrSeller+"/" + userDir + "/" + "hasBlocked");
            BufferedReader bfr = new BufferedReader(new FileReader(thatUserBlockedFile));
            String line;
            boolean blocked = false;
            //Check the hasBlocked file, if this.username isn't there add the user to available
            while((line = bfr.readLine())!= null) {
                if(line.equals(this.username)) {
                    blocked = true;
                    break;
                }
            }
            bfr.close();
            if(!blocked) {
                available.add(userDir);
            }
        }
        //Just turn ArrayList into array classic 180 stuff
        String[] availables = new String[available.size()];
        for(int i = 0; i < availables.length; i++) {
            availables[i] = available.get(i);
        }
        return availables;
    }

    /** Method to see if given recipient actually exists by checking if directory with recipient name exists
     * @param recipient The username associated with the user directory you wish to find
     */
    public boolean checkIfRecipientExists(String recipient) { // DESTIN: look through directories and check for name
        String path = "";
        if (isSeller) {
            path = "data/buyers/";
        } else {
            path = "data/sellers/";
        }
        try {
            File f = new File(path + recipient);
            return f.exists();
        } catch (Exception e) {
            System.out.println("Error loading file, please try again");
            return false;
        }
    }

    /** Method to see if conversation has started
     * Check if <username><recipient>.txt exists because if it exists then <recipient><username>.txt also exists
     * if nto create both files
     * @param recipient The username associated with the person the user wishes to measure
     */
    public void checkIfMessageExists(String recipient) {// check if <username><recipient>.txt exits in directory or not
        String path1 = "";
        String path2 = "";
        if (isSeller) {
            path1 = "data/sellers/" + username + "/";
            path2 = "data/buyers/" + recipient + "/";
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
    }

    /**
     * Adds message to both sender and receiver file
     *
     * @param recipient
     *
     * @Author John Brooks
     */

    public void appendMessage(String recipient) {

        String fileRecipient = "";
        String fileSender = "";
        String message;
        String printFile;

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // add to both files

    /**
     * Searches file for index that matches one given by the user and changes that line and
     * writes it back to the file
     *
     * @param recipient
     *
     * @Author John Brooks
     */
    public void editMessage(String recipient) {

        String fileRecipient = "";
        String fileSender = "";
        String message;
        String printFile;
        int count = 0;
        int ind = -1;
        int flag;

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
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
                } else
                    System.out.println("There is nothing in this file to edit.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Searches file for index that matches one given by the user to not add it to an arraylist and
     * therefore not write it to the file
     *
     * @param recipient
     *
     * @Author John Brooks
     */

    public void deleteMessage(String recipient) {
        String fileRecipient = "";
        String fileSender = "";
        String printFile;
        int count = 0;
        int flag;
        int indexOfDelete = -1;

        if (isSeller) {
            fileSender = "data/sellers/" + username + "/";
            fileRecipient = "data/buyers/" + recipient + "/";
        } else {
            fileSender = "data/buyers/" + username + "/";
            fileRecipient = "data/sellers/" + recipient + "/";
        }
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
                        }
                    }
                    display.close();
                    messageSenderWriter.close();
                } else
                    System.out.println("There is nothing in this file to delete.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Block a user if not already blocked
     * @param username: name of person to block
     * @return true if already blocked, false if notBlocked
     * @throws IOException
     */
    public boolean blockUser(String username) {
        try {
            String blockedFilePath = "data/" + ((this.isSeller) ? "sellers/" : "buyers/") + this.username + "/hasBlocked";
            File blockedFile = new File(blockedFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (line.equals(username)) {
                    //The user is already blocked
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Return a list of blocked users for this user
     * @return array of blocked usernames
     * @throws IOException
     */
    public String[] blockedList() throws IOException{
        ArrayList<String> victims = new ArrayList<>();
        String blockedFilePath = "data/" + ((this.isSeller)? "sellers/": "buyers/") + this.username + "/hasBlocked";
        File blockedFile = new File(blockedFilePath);
        BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
        String line;
        while((line = bfr.readLine())!= null) {
            victims.add(line);
        }
        String[] blockedList = new String[victims.size()];
        for(int i = 0; i < victims.size();i++) {
            blockedList[i] = victims.get(i);
        }
        return blockedList;
    }

    /**
     * unblocked a user from the blockedList() return array
     * @param username: name of person to unblock
     */
    public void unblockUser(String username) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            String blockedFilePath = "data/" + ((this.isSeller) ? "sellers/" : "buyers/") + this.username + "/hasBlocked";
            File blockedFile = new File(blockedFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (!line.equals(username)) {
                    lines.add(line);
                }
            }
            bfr.close();
            PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, true));
            for (String l : lines) {
                pw.write(l);
                pw.println();
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
Ted owns walmart and target
john messages walmart 'hi' and target 'hello

john(walmart)
 */