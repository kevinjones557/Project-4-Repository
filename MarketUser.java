import java.io.File;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

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
    public String[] getAvailableUser() throws IOException {
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

    public void appendMessage(String recipient) { // add to both files
    }

    public void editMessage(String recipient) { // somehow figure out which one to edit, edit both files
    }

    public void deleteMessage(String recipient) { // delete from <username><recipient>.txt somehow figure out which one to delete
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
         PrintWriter pw = new PrintWriter(new FileWriter(blockedFile));
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
    public void unblockUser(String username) {
        //TODO: implement unblockUser method
    }
}
