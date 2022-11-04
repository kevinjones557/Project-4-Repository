import java.util.InputMismatchException;
import java.util.Scanner;

public class MarketUser implements User{
    private String username;
    private boolean isSeller;

    public static void main(String[] args) {
        MarketUser mu = new MarketUser("hello",true);
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
                            "or enter any number to cancel");
                    do {
                        try {
                            selection = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid number:");
                        }
                    } while (selection == null);
                    scan.nextLine();
                    if (selection == 1) { // if the user wants to search for a buyer, enter this statement
                        System.out.println("Enter the username of a buyer:");
                        recipient = scan.nextLine();
                        if (checkIfRecipientExists(recipient)) {
                            break; // at this point we know that the variable 'recipient' contains a valid username
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
                    System.out.println("Enter '1' to search for a store, enter '2' to see a list of stores," +
                            "or enter '3' to cancel");
                    selection = scan.nextInt();
                    scan.nextLine();
                    if (selection == 1) { // if the user wants to search for a store, enter this statement
                        System.out.println("Enter the name of a store:");
                        recipient = scan.nextLine();
                        if (checkIfRecipientExists(recipient)) {
                            break; // at this point we know that the variable 'recipient' contains a valid username
                        } else {
                            System.out.println("Sorry! This user does not exist!");
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
        boolean messageAlreadyExists = checkIfMessageExists(recipient);

    }
    public String[] getAvailableUsers() {
        return null;
    }/*Return an array of username that this user can message. Buyers can only message
    sellers, sellers can only message buyers, and exclude from the list any users that block this user*/


    public boolean checkIfRecipientExists(String recipient) { // DESTIN: look through directories and check for name
        return true;
    }

    public boolean checkIfMessageExists(String recipient) {// check if <username><recipient>.txt exits in directory or not
        return true;
    }

    public void makeMessageFiles(String recipient) { // Make both files: <username><recipient>.txt <recipient><username>.txt
    }

    public void appendMessage(String recipient) { // add to both files
    }

    public void editMessage(String recipient) { // somehow figure out which one to edit, edit both files
    }

    public void deleteMessage(String recipient) { // delete from <username><recipient>.txt somehow figure out which one to delete
    }

    public boolean blockUser(String recipient) { // check if recipient is seller
        return true;
    }
}
