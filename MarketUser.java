import java.io.*;
import java.util.*;

public class MarketUser implements User{
    private String username;
    private boolean isSeller;

    public static void main(String[] args) {
        MarketUser mu = new MarketUser("testuser",true);
        mu.message();
    }

    /** Constructor creates new object with a username and tells object if it is a seller or not.
     * @param username The username associated with the user directory you wish to find
     * @param isSeller Tells class whether this instance is a seller or not
     */
    public MarketUser(String username, boolean isSeller) {
        this.username = username;
        this.isSeller = isSeller;
    }

    /** Method called when user is logged in, using System.out.println() will ask user if they want to message someone,
     * who they want to message, and what they want to message.
     */
    public void message() {
        Integer selection = null;
        String recipient = "";
        String proceed;
        Scanner scan = new Scanner(System.in);
        if (isSeller) { // if it is a seller, enter this statement
            System.out.println("Do you wish to contact a potential buyer? (Yes/No)");
            proceed = scan.nextLine();
            if (proceed.equalsIgnoreCase("yes")) { // if the user has selected to message someone...
                do { // keep prompting for a recipient until they either select a valid recipient, or cancel
                    System.out.println("Enter '1' to search for a buyer, enter '2' to see a list of buyers," +
                            " or enter any number to cancel:");
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
                            System.out.println("Sorry! This user does not exist!");
                        }
                    } else if (selection == 2) { // if the user wants to see a list of people to contact
                        String[] allAvailableUsers = getAvailableUsers();
                        for (int i = 0; i < allAvailableUsers.length; i++) {
                            System.out.println((i + 1) + ". " + allAvailableUsers[i]);
                        }
                        System.out.println("Select the buyer you wish to contact by entering the number by their name");
                        recipient = allAvailableUsers[scan.nextInt() - 1];
                        scan.nextLine();
                        break; // at this point we again know that the variable 'recipient' contains a valid username
                    } else {
                        proceed = ""; // set proceed to not 'yes' so that we return out of function
                        break;
                    }
                } while(true);
            }
        } else { // lots of repeated code here, but I think needed because of slightly different print statements
            System.out.println("Do you wish to contact a store? (Yes/No)");
            proceed = scan.nextLine();
            if (proceed.equalsIgnoreCase("yes")) { // if the user has selected to message someone...
                do { // keep prompting for a recipient until they either select a valid recipient, or cancel
                    System.out.println("Enter '1' to search for a store, enter '2' to see a list of stores, " +
                            " or enter any number to cancel:");
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
                    } else if (selection ==2 ) { // if the user wants to see a list of people to contact
                        String[] allAvailableUsers = getAvailableUsers();
                        for (int i = 0; i < allAvailableUsers.length; i++) {
                            System.out.println((i + 1) + ". " + allAvailableUsers[i]);
                        }
                        System.out.println("Select the store you wish to contact by entering the number by their name");
                        recipient = allAvailableUsers[scan.nextInt() - 1];
                        scan.nextLine();
                        break; // at this point we again know that the variable 'recipient' contains a valid username
                    } else {
                        proceed = ""; // set proceed to not 'yes' so that we return out of function
                        break;
                    }
                } while(true);
            }
        }
        /* at this point in program one of two things has happened: Either the String recipient contains a valid
        username, or the user said "No" to wanting to send message */
        if (!proceed.equalsIgnoreCase("yes")) {
            return;
        } // after this statement we know that String recipient contains a valid value
        checkIfMessageExists(recipient); // this will check if message has already been created and create if not
        System.out.printf("Connected with %s!\n", recipient);
        selection = null;
        System.out.println("Enter '1' to send a message, '2' edit a message, '3' to delete a message" +
                " or enter any number to cancel:");
        do {
            try {
                selection = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number:");
                scan.nextLine();
            }
        } while (selection == null);
        if (selection == 1) {
            appendMessage(recipient);
        } else if (selection == 2) {
            editMessage(recipient);
        } else if (selection == 3) {
            deleteMessage(recipient);
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
        String buyerOrSeller = (this.isSeller)? "Buyers": "Sellers";
        //Goes in the right directory
        File recipientType = new File((this.isSeller)? "Buyers": "Sellers");
        String[] usernames = recipientType.list();
        //Loop through user directories
        for(String userDir : usernames) {
            File thatUserBlockedFile = new File(buyerOrSeller+"\\" + userDir + "\\" + "hasBlocked");
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

        //retrieval for exact file path from Destin
        File senderF = new File(fileSender);
        File recipientF = new File(fileRecipient);
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
                FileOutputStream fosReceive = new FileOutputStream(senderF, true);
                PrintWriter messageReceiveWriter = new PrintWriter(fosReceive);
                Scanner scan = new Scanner(System.in);
                System.out.println(username + ":");
                message = scan.nextLine();
                //write it on the end of each persons file
                messageSenderWriter.println(username + ": " + message);
                messageReceiveWriter.println(recipient + ": " + message);
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
        int count = 1;
        int ind = -1;
        int flag;

        //retrieval for exact file path from Destin
        File senderF = new File(fileSender);
        File recipientF = new File(fileRecipient);
        if (senderF.exists() && recipientF.exists()) {
            try {
                //initial display
                BufferedReader display = new BufferedReader(new FileReader(senderF));
                printFile = display.readLine();
                while (printFile != null) {
                    System.out.println(count + ": " + printFile);
                    count++;
                    printFile = display.readLine();
                }
                BufferedReader buffSender = new BufferedReader(new FileReader(senderF));
                FileOutputStream fosSend = new FileOutputStream(senderF, false);
                PrintWriter messageSenderWriter = new PrintWriter(fosSend);
                FileOutputStream fosReceive = new FileOutputStream(senderF, false);
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
                    if (flag == 1 || ind < 1 && ind > count)
                        System.out.println("Your index must be a number and must be available. Try again:");
                } while (flag == 1 || ind < 1 && ind > count);
                System.out.println("What would you like the new version to say?");
                String edit = scan.nextLine();
                ArrayList<String> readSenderFile = new ArrayList<>();
                String line = buffSender.readLine();
                //run through array, add lines, and check throughout if line matches criteria and then change it
                int count2 = 0;
                while (line != null) {
                    if (ind == count2) {
                        line = edit;
                    }
                    readSenderFile.add(username + ": " + line);
                    line = buffSender.readLine();
                    count2++;
                }
                //write back to files
                for (int i = 0; i < readSenderFile.size(); i++) {
                    messageSenderWriter.write(readSenderFile.get(i));
                    messageReceiveWriter.write(readSenderFile.get(i));
                }
                display.close();
                buffSender.close();
                messageSenderWriter.close();
                messageReceiveWriter.close();
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
        String message;
        String printFile;
        int count = 1;
        int flag;
        int indexOfDelete = -1;

        //retrieval for exact file path from Destin
        File senderF = new File(fileSender);
        File recipientF = new File(fileRecipient);
        if (senderF.exists() && recipientF.exists()) {
            try {
                //display
                BufferedReader display = new BufferedReader(new FileReader(senderF));
                printFile = display.readLine();
                while (printFile != null) {
                    System.out.println(count + ": " + printFile);
                    count++;
                    printFile = display.readLine();
                }
                BufferedReader buffSender = new BufferedReader(new FileReader(senderF));
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
                    if (flag == 1 || indexOfDelete < 1 && indexOfDelete > count)
                        System.out.println("Your index must be a number and must be available. Try again:");
                } while (flag == 1 || indexOfDelete < 1 && indexOfDelete > count);

                ArrayList<String> readSenderFile = new ArrayList<>();
                String line = buffSender.readLine();
                //run through array, add lines, and check throughout if line matches criteria then dont add it
                int count2 = 1;
                while (line != null) {
                    if (!(count2 == indexOfDelete)) {
                        readSenderFile.add(line);
                    }
                    line = buffSender.readLine();
                    count2++;
                }
                for (int i = 0; i < readSenderFile.size(); i++) {
                    messageSenderWriter.write(readSenderFile.get(i));
                }
                display.close();
                buffSender.close();
                messageSenderWriter.close();
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
    public boolean blockUser(String username) throws IOException {
         String blockedFilePath = (this.isSeller)? "Sellers": "Buyers" + this.username + "hasBlocked";
         File blockedFile = new File(blockedFilePath);
         BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
         String line;
         while((line = bfr.readLine())!= null) {
             if(line.equals(username)) {
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
    }
    
    /**
     * Return a list of blocked users for this user
     * @return array of blocked usernames
     * @throws IOException
     */
    public String[] blockedList() throws IOException{
        ArrayList<String> victims = new ArrayList<>();
        String blockedFilePath = (this.isSeller)? "Sellers": "Buyers" + this.username + "hasBlocked";
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
    public void unblockUser(String username) throws IOException{
        ArrayList<String> lines = new ArrayList<>();
        String blockedFilePath = (this.isSeller)? "Sellers": "Buyers" + this.username + "hasBlocked";
        File blockedFile = new File(blockedFilePath);
        BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
        String line;
        while((line = bfr.readLine())!= null) {
            if(!line.equals(username)) {
                lines.add(line);
            }
        }
        bfr.close();
        PrintWriter pw = new PrintWriter(new FileWriter(blockedFile, true));
        for(String l : lines) {
            pw.write(l);
            pw.println();
        }
        pw.flush();
        pw.close();
    }
}
