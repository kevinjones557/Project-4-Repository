import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.SimpleDateFormat;

public class MarketUser implements User{
    // Output messages
    public final String SELECT_OPTION = "Please select an option below:";
    public final String BLOCK_INVISIBLE_OPTION = "1. Use block/invisible features\n2. Continue to messaging\n3. Exit Messaging System";
    public final String SEARCH_LIST_BUYER = "1. Search for a buyer\n2. View a list of buyers\n3. Cancel";
    public final String SEARCH_LIST_SELLER = "1. Search for a seller\n2. View a list of stores\n" +
            "3. Continue to statistics\n4. Exit Messaging System";
    public final String MESSAGE_OPTIONS = "1. View Message Contents\n2. Send a message\n3. Edit a message\n" +
            "4. Delete a message\n5. Import a .txt file\n6. Export message history as a CSV file\n7.Cancel";
    public final String METRICS_PROMPT = "1. View statistics for stores\n2. Exit Messaging System";

    public final String STORE_OR_SELLER_ACCOUNT = "1. Message with personal account\n2. Message with store account" +
            "\n3. Continue to statistics\n4. Exit Messaging System";

    public final String SWITCH_ACCOUNT = "1. Continue with current account\n2. Switch accounts\n" +
            "3. Continue to statistics\n4. Exit Messaging System";

    private String username;
    private boolean isSeller;

    private boolean isUserStore;

    public LinkedHashMap<String,String> storeNameMap;

    /*
    Buyer:
    Ask if they want to block, unblock, Become invisible to a user, become visible to a user or continue to messaging
    IF YES:
        Select user to block, unblock, Become invisible, become visible
        Search for user or see list of users
        Ask if they would like to continue blocking users or continue to messaging
    Ask if they want to view message, add message, edit message, delete message, import txt file, export csv, or continue to metrics
    IF YES:
        Select user to message
        Search for user or see list
        Ask if they want to message again of if they would like to message a different user or continue to metrics
    Ask if they want to view metrics or log out

    Seller:
    Ask if they would like to continue with their personal profile or log into a store profile
    IF IN PERSONAL:
    Ask if they want to block, unblock, Become invisible to a user, become visible to a user or continue to messaging
    IF IN STORE OR PERSONAL
    Ask if they want to view message, add message, edit message, delete message, import txt file, export csv, or log out of store and continue in personal
    IF IN PERSONAL
    Ask if they want to view metrics for stores or log out
     */

    /** Constructor creates new object with a username and tells object if it is a seller or not.
     * @param username The username associated with the user directory you wish to find
     * @param isSeller Tells class whether this instance is a seller or not
     * @author Kevin Jones
     */
    public MarketUser(String username, boolean isSeller) {
        storeNameMap = FileManager.mapStoresToSellers();
        this.username = username;
        this.isSeller = isSeller;
        isUserStore = false;
    }
    public static void main(String[] args) {
        MarketUser mu = new MarketUser("aden",true);
        mu.mainForSeller();
    }

    /** Function that waits until valid prompt
     * @param lowestValidValue the lowest valid value
     * @param highestValidValue highest valid value
     * @return returns the chosen index
     * @author Kevin Jones
     */

    public int waitForValidInput(int lowestValidValue, int highestValidValue) {
        int selection = Integer.MAX_VALUE;
        Scanner localScan = new Scanner(System.in);
        do {
            try {
                if (selection != Integer.MAX_VALUE) {
                    System.out.println("Please enter a valid number:");
                }
                selection = localScan.nextInt();
                localScan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number:");
                localScan.nextLine();
            }
        } while ((selection < lowestValidValue || selection > highestValidValue));
        return selection;
    }

    public void mainForSeller() {
        String sellerName = this.username;
        Scanner sellerScan = new Scanner(System.in);
        do {
            System.out.println(SELECT_OPTION);
            System.out.println(BLOCK_INVISIBLE_OPTION);
            int selection = waitForValidInput(1, 3);
            if (selection == 3) {
                return;
            }
            if (selection == 2) {
                break; // this will continue to messaging
            }
            // at this point they want to block/unblock/visible/invisible a user so ask for user
            System.out.println(SELECT_OPTION);
            System.out.println("1. Block a user \n2. Unblock a user \n3. Become invisible to a user \n" +
                    "4. Become visible again");
            int option;
            while (true) {
                try {
                    option = sellerScan.nextInt();
                    sellerScan.nextLine();
                    if (option >= 1 && option <= 4) {
                        break;
                    } else {
                        System.out.println("Option number must be between 1 and 4 inclusively!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid option number!");
                }

            }
            try {
                switch (option) {
                    case 1:
                        while (true) {
                            String[] userList = getAvailableUsers();
                            String[] blockedList = blockedList();
                            if(Arrays.equals(userList, blockedList)) {
                                System.out.println("You have blocked all available user!");
                                break;
                            }
                            boolean inBlockedList = false;
                            System.out.println("Please select a user to block");
                            for (int i = 0; i < userList.length; i++) {
                                for(String blockedUser: blockedList) {
                                    if(blockedUser.equals(userList[i])) {
                                        inBlockedList = true;
                                    }
                                }
                                System.out.printf("%d. %s" + ((inBlockedList)?"(blocked)":"") +"\n", i + 1, userList[i]);
                                inBlockedList = false;
                            }
                            int victim;
                            while (true) {
                                try {
                                    victim = sellerScan.nextInt();
                                    sellerScan.nextLine();
                                    if (victim >= 1 && victim <= userList.length) {
                                        break;
                                    } else {
                                        System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a valid option number!");
                                }
                            }
                            victim-=1;
                            boolean blocked = blockUser(userList[victim]);
                            if (blocked) {
                                System.out.printf("%s is already blocked\n", userList[victim]);
                            } else {
                                System.out.printf("Successfully block %s\n", userList[victim]);
                            }
                            System.out.println("Do you want to continue? (Yes/No)");
                            if ((sellerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                break;
                            }
                        }
                        break;
                    case 2:
                        if (blockedList().length == 0) {
                            System.out.println("You haven't blocked any user");
                        } else {
                            while (blockedList().length != 0) {
                                String[] userList = blockedList();
                                System.out.println("Please select a user to unblock");
                                for (int i = 0; i < userList.length; i++) {
                                    System.out.printf("%d. %s\n", i + 1, userList[i]);
                                }
                                int victim;
                                while (true) {
                                    try {
                                        victim = sellerScan.nextInt();
                                        sellerScan.nextLine();
                                        if (victim >= 1 && victim <= userList.length) {
                                            break;
                                        } else {
                                            System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid option number!");
                                    }
                                }
                                victim -=1;
                                unblockUser(userList[victim]);
                                System.out.printf("Successfully unblock %s\n", userList[victim]);
                                if (blockedList().length == 0) {
                                    System.out.println("Your blocked list is now empty");
                                    break;
                                } else {
                                    System.out.println("Do you want to continue? (Yes/No)");
                                    if ((sellerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                        break;
                                    }
                                }
                            }

                        }
                        break;

                    case 3:
                        while (true) {
                            String[] userList = getAvailableUsers();
                            String[] invisibleList = invisibleList();
                            if(Arrays.equals(userList, invisibleList)) {
                                System.out.println("You have become invisible to all available user");
                                break;
                            }
                            boolean inInvisibleList = false;
                            for (int i = 0; i < userList.length; i++) {
                                for(String blockedUser: invisibleList) {
                                    if(blockedUser.equals(userList[i])) {
                                        inInvisibleList = true;
                                    }
                                }
                                System.out.printf("%d. %s" + ((inInvisibleList)?"(can't see you)":"") +"\n", i + 1, userList[i]);
                                inInvisibleList = false;
                            }
                            int victim;
                            while (true) {
                                try {
                                    victim = sellerScan.nextInt();
                                    sellerScan.nextLine();
                                    if (victim >= 1 && victim <= userList.length) {
                                        break;
                                    } else {
                                        System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a valid option number!");
                                }
                            }
                            victim-=1;
                            boolean invisible = becomeInvisibleToUser(userList[victim]);
                            if (invisible) {
                                System.out.printf("You are already invisible to %s\n", userList[victim]);
                            } else {
                                System.out.printf("Successfully become invisible to %s\n", userList[victim]);
                            }
                            System.out.println("Do you want to continue? (Yes/No)");
                            if ((sellerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                break;
                            }
                        }
                        break;

                    case 4:
                        if (invisibleList().length == 0) {
                            System.out.println("You haven't made yourself invisible to any user");
                        } else {
                            while (invisibleList().length != 0) {
                                String[] userList = invisibleList();
                                System.out.println("Please select a user to become visible to");
                                for (int i = 0; i < userList.length; i++) {
                                    System.out.printf("%d. %s\n", i + 1, userList[i]);
                                }
                                int victim;
                                while (true) {
                                    try {
                                        victim = sellerScan.nextInt();
                                        sellerScan.nextLine();
                                        if (victim >= 1 && victim <= userList.length) {
                                            break;
                                        } else {
                                            System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid option number!");
                                    }
                                }
                                victim -= 1;
                                unblockUser(userList[victim]);
                                System.out.printf("Successfully become visible again to %s\n", userList[victim]);
                                if (blockedList().length == 0) {
                                    System.out.println("Your invisible list is now empty");
                                    break;
                                } else {
                                    System.out.println("Do you want to continue? (Yes/No)");
                                    if ((sellerScan.nextLine().equalsIgnoreCase("yes"))) {
                                        break;
                                    }
                                }
                            }

                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An unknown error occurred");
            }
            System.out.println("Do you want to keep using block/invisible features?(Yes/No)");
            if (!sellerScan.nextLine().equalsIgnoreCase("yes")) {
                break;
            }

        } while (true);
        // at this point past blocking/visible stage, onto messaging, and we need to prompt if they want to use store
        messaging: do {
            isUserStore = false;
            this.username = sellerName;
            System.out.println(SELECT_OPTION);
            System.out.println(STORE_OR_SELLER_ACCOUNT);
            int storeOrUser = waitForValidInput(1, 4);
            if (storeOrUser == 4) {
                return;
            } else if (storeOrUser == 3) {
                break;
            } else if (storeOrUser == 2) {
                ArrayList<String> sellerStores = FileManager.getStoresFromSeller(this.username);
                if (sellerStores.size() == 0) {
                    System.out.println("You have not added any stores!");
                } else {
                    for (int i = 0; i < sellerStores.size(); i++) {
                        System.out.println((i + 1) + ". " + sellerStores.get(i));
                    }
                    System.out.println(SELECT_OPTION);
                    int storeOption = waitForValidInput(1, sellerStores.size());
                    this.username = sellerStores.get(storeOption - 1);
                    isUserStore = true;
                }
            }
            // at this point username is either personal username or store name now we get recipient
            do {
                System.out.println(SELECT_OPTION);
                System.out.println(SEARCH_LIST_BUYER);
                int searchOrCancel = waitForValidInput(1, 3);
                if (searchOrCancel == 3) {
                    break; // this will go back to store/user account
                }
                String recipient = null;
                String buyerName;
                if (searchOrCancel == 1) {
                    System.out.println("Please enter the name of a buyer:");
                    buyerName = sellerScan.nextLine().trim();
                    if (!FileManager.checkBuyerExists(buyerName)) {
                        System.out.println("Sorry, this buyer does not exist!");
                    } /*TODO else if (sellerHasMadeThemselvesInvisible)
                    TODO Vinh, check if the buyer who was searched for is invisible to this seller or not
                    TODO if so, enter this if statement and print:
                    System.out.println("This user has made themselves invisible to you!)
                    */
                    else { // at this point we know the seller searched for exists and wants to be reached
                        recipient = buyerName;
                    }
                } else if (searchOrCancel == 2) {
                    try {
                        String[] allAvailableBuyers = getAvailableUsers();
                        for (int i = 0; i < allAvailableBuyers.length; i++) {
                            System.out.println((i + 1) + ". " + allAvailableBuyers[i]);
                        }
                        System.out.println(allAvailableBuyers.length + 1 + ": Cancel");
                        System.out.println(SELECT_OPTION);
                        int storeOption = waitForValidInput(1, allAvailableBuyers.length + 1);
                        if (storeOption != allAvailableBuyers.length + 1) {
                            recipient = allAvailableBuyers[storeOption - 1];
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Sorry, there was an error reading the users, please try again!");
                    }
                }
                // at this point in program if recipient is != null, the recipient is valid
                if (recipient != null) {
                    checkIfMessageExists(recipient, false); // this will check if message has already been created and create if not
                    System.out.printf("Connected with %s!\n", recipient);
                    do {
                        System.out.println(SELECT_OPTION);
                        System.out.println(MESSAGE_OPTIONS);
                        int selection = waitForValidInput(1, 7);
                        if (selection == 7) {
                            break;
                        }
                        switch (selection) {
                            case 1:
                                displayMessage(recipient);
                                break;
                            case 2:
                                if (!isUserStore) {
                                    appendMessage(recipient);
                                } else {
                                    appendMessage(storeNameMap.get(this.username), recipient);
                                }
                                break;
                            case 3:
                                if (!isUserStore) {
                                    editMessage(recipient);
                                } else {
                                    editMessage(storeNameMap.get(username), recipient);
                                }
                                break;
                            case 4:
                                if (!isUserStore) {
                                    deleteMessage(recipient);
                                } else {
                                    deleteMessage(storeNameMap.get(username), recipient);
                                }
                                break;
                            case 5:
                                String path;
                                System.out.println("Please enter the path to the text file you would like to import or " +
                                        "type 'cancel' to Cancel.");
                                do {
                                    path = sellerScan.nextLine();
                                    if (path.equalsIgnoreCase("cancel")) {
                                        break;
                                    }
                                    if (path.endsWith(".txt")) {
                                        break;
                                    }
                                    System.out.println("Please enter a valid path");
                                } while (!path.endsWith(".txt"));
                                if (!path.equalsIgnoreCase("cancel")) {
                                    importFile(path, recipient, false);
                                }
                            case 8:
                                System.out.println("Enter the path where you would like the csv file to be stored:");
                                String csvPath = sellerScan.nextLine();
                                writeCSV(recipient, csvPath);
                        }
                        System.out.println("Would you like to continue interacting with this user?\n1. Yes\n2. No");
                        int stayWithUser = waitForValidInput(1, 2);
                        if (stayWithUser == 2) {
                            break;
                        }
                    } while (true);
                    System.out.println(SELECT_OPTION);
                    System.out.println(SWITCH_ACCOUNT);
                    int switchUser = waitForValidInput(1, 4);
                    if (switchUser == 4) {
                        return;
                    } else if (switchUser == 2) {
                        break;
                    } else if (switchUser == 3) {
                        break messaging;
                    }
                }
            } while (true);
        } while (true);
        this.username = sellerName;
        System.out.println(SELECT_OPTION);
        System.out.println(METRICS_PROMPT);
        int goToMetrics = waitForValidInput(1, 2);
        if (goToMetrics == 2) {
            return;
        } else {
            MetricManager.sellerMetricsUI(this.username, sellerScan, storeNameMap);
        }
    }

    /** A function to handle user input for buyers
     * @author Kevin Jones
     */
    public void mainForBuyer() {
        Scanner buyerScan = new Scanner(System.in);
        boolean isRecipientStore = false;
        do {
            System.out.println(SELECT_OPTION);
            System.out.println(BLOCK_INVISIBLE_OPTION);
            int selection = waitForValidInput(1, 6);
            if (selection == 3) {
                return;
            }
            if (selection == 2) {
                break; // this will continue to messaging
            }
            //start of blocking 
            System.out.println(SELECT_OPTION);
            System.out.println("1. Block a user \n2. Unblock a user \n3. Become invisible to a user \n" +
                    "4. Become visible again");
            int option;
            while (true) {
                try {
                    option = buyerScan.nextInt();
                    buyerScan.nextLine();
                    if (option >= 1 && option <= 4) {
                        break;
                    } else {
                        System.out.println("Option number must be between 1 and 4 inclusively!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid option number!");
                }

            }
            try {
                switch (option) {
                    case 1:
                        while (true) {
                            String[] userList = getAvailableUsers();
                            String[] blockedList = blockedList();
                            if(Arrays.equals(userList, blockedList)) {
                                System.out.println("You have blocked all available user!");
                                break;
                            }
                            boolean inBlockedList = false;
                            System.out.println("Please select a user to block");
                            for (int i = 0; i < userList.length; i++) {
                                for(String blockedUser: blockedList) {
                                    if(blockedUser.equals(userList[i])) {
                                        inBlockedList = true;
                                    }
                                }
                                System.out.printf("%d. %s" + ((inBlockedList)?"(blocked)":"") +"\n", i + 1, userList[i]);
                                inBlockedList = false;
                            }
                            int victim;
                            while (true) {
                                try {
                                    victim = buyerScan.nextInt();
                                    buyerScan.nextLine();
                                    if (victim >= 1 && victim <= userList.length) {
                                        break;
                                    } else {
                                        System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a valid option number!");
                                }
                            }
                            victim-=1;
                            boolean blocked = blockUser(userList[victim]);
                            if (blocked) {
                                System.out.printf("%s is already blocked\n", userList[victim]);
                            } else {
                                System.out.printf("Successfully block %s\n", userList[victim]);
                            }
                            System.out.println("Do you want to continue? (Yes/No)");
                            if ((buyerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                break;
                            }
                        }
                        break;

                    case 2:
                        if (blockedList().length == 0) {
                            System.out.println("You haven't blocked any user");
                        } else {
                            while (blockedList().length != 0) {
                                String[] userList = blockedList();
                                System.out.println("Please select a user to unblock");
                                for (int i = 0; i < userList.length; i++) {
                                    System.out.printf("%d. %s\n", i + 1, userList[i]);
                                }
                                int victim;
                                while (true) {
                                    try {
                                        victim = buyerScan.nextInt();
                                        buyerScan.nextLine();
                                        if (victim >= 1 && victim <= userList.length) {
                                            break;
                                        } else {
                                            System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid option number!");
                                    }
                                }
                                victim -=1;
                                unblockUser(userList[victim]);
                                System.out.printf("Successfully unblock %s\n", userList[victim]);
                                if (blockedList().length == 0) {
                                    System.out.println("Your blocked list is now empty");
                                    break;
                                } else {
                                    System.out.println("Do you want to continue? (Yes/No)");
                                    if ((buyerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                        break;
                                    }
                                }
                            }

                        }
                        break;

                    case 3:
                        while (true) {
                            String[] userList = getAvailableUsers();
                            String[] invisibleList = invisibleList();
                            if(Arrays.equals(userList, invisibleList)) {
                                System.out.println("You have become invisible to all available user");
                                break;
                            }
                            boolean inInvisibleList = false;
                            for (int i = 0; i < userList.length; i++) {
                                for(String blockedUser: invisibleList) {
                                    if(blockedUser.equals(userList[i])) {
                                        inInvisibleList = true;
                                    }
                                }
                                System.out.printf("%d. %s" + ((inInvisibleList)?"(can't see you)":"") +"\n", i + 1, userList[i]);
                                inInvisibleList = false;
                            }
                            int victim;
                            while (true) {
                                try {
                                    victim = buyerScan.nextInt();
                                    buyerScan.nextLine();
                                    if (victim >= 1 && victim <= userList.length) {
                                        break;
                                    } else {
                                        System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a valid option number!");
                                }
                            }
                            victim-=1;
                            boolean invisible = becomeInvisibleToUser(userList[victim]);
                            if (invisible) {
                                System.out.printf("You are already invisible to %s\n", userList[victim]);
                            } else {
                                System.out.printf("Successfully become invisible to %s\n", userList[victim]);
                            }
                            System.out.println("Do you want to continue? (Yes/No)");
                            if ((buyerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                break;
                            }
                        }
                        break;

                    case 4:
                        if (invisibleList().length == 0) {
                            System.out.println("You haven't become invisible to any user");
                        } else {
                            while (invisibleList().length != 0) {
                                String[] userList = invisibleList();
                                System.out.println("Please select a user to become visible to");
                                for (int i = 0; i < userList.length; i++) {
                                    System.out.printf("%d. %s\n", i + 1, userList[i]);
                                }
                                int victim;
                                while (true) {
                                    try {
                                        victim = buyerScan.nextInt();
                                        buyerScan.nextLine();
                                        if (victim >= 1 && victim <= userList.length) {
                                            break;
                                        } else {
                                            System.out.printf("Option number must be between 1 and %d inclusively!\n", userList.length);
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter a valid option number!");
                                    }
                                }
                                victim -=1;
                                unblockUser(userList[victim]);
                                System.out.printf("Successfully become visible again to %s\n", userList[victim]);
                                if (blockedList().length == 0) {
                                    System.out.println("Your invisible list is now empty");
                                    break;
                                } else {
                                    System.out.println("Do you want to continue? (Yes/No)");
                                    if ((buyerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                                        break;
                                    }
                                }
                            }

                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An unknown error occurred");
            }
            System.out.println("Do you want to keep using block/invisible features?(Yes/No)");
            if ((buyerScan.nextLine().equalsIgnoreCase("yes")) ? false : true) {
                break;
            }
        } while (true);
        // at this point past blocking/visible stage, onto messaging
        do {
            isRecipientStore = false;
            System.out.println(SELECT_OPTION);
            System.out.println(SEARCH_LIST_SELLER);
            int searchOrCancel = waitForValidInput(1, 4);
            if (searchOrCancel == 4) {
                return;
            }
            if (searchOrCancel == 3) {
                break; // this will continue to metrics
            }
            String recipient = null;
            if (searchOrCancel == 1) {
                System.out.println("Please enter the name of a seller:");
                String sellerName = buyerScan.nextLine().trim();
                if (!FileManager.checkSellerExists(sellerName)) {
                    System.out.println("Sorry, this seller does not exist!");
                } /*TODO else if (sellerHasMadeThemselvesInvisible)
                TODO Vinh, check if the seller who was searched for is invisible to this buyer or not
                TODO if so, enter this if statement and print:
                System.out.println("This user has made themselves invisible to you!)
            */ else { // at this point we know the seller searched for exists and wants to be reached
                    recipient = sellerName;
                }
            } else if (searchOrCancel == 2) {
                try {
                    String[] allAvailableStores = getAvailableStores();
                    for (int i = 0; i < allAvailableStores.length; i++) {
                        System.out.println((i + 1) + ". " + allAvailableStores[i]);
                    }
                    System.out.println(allAvailableStores.length + 1 + ": Cancel");
                    System.out.println(SELECT_OPTION);
                    int storeOption = waitForValidInput(1, allAvailableStores.length + 1);
                    if (storeOption != allAvailableStores.length + 1) {
                        recipient = allAvailableStores[storeOption - 1];
                        isRecipientStore = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Sorry, there was an error reading the users, please try again!");
                }
            }
            // at this point they want to interact with a user so ask for user
            // at this point if recipient != null it contains either a valid store or seller name
            if (recipient != null) {
                checkIfMessageExists(recipient, isRecipientStore); // this will check if message has already been created and create if not
                System.out.printf("Connected with %s!\n", recipient);
                do {
                    System.out.println(SELECT_OPTION);
                    System.out.println(MESSAGE_OPTIONS);
                    int selection = waitForValidInput(1, 7);
                    if (selection == 7) {
                        break;
                    }
                    switch (selection) {
                        case 1:
                            displayMessage(recipient);
                            break;
                        case 2:
                            if (!isRecipientStore) {
                                appendMessage(recipient);
                            } else {
                                appendMessage(storeNameMap.get(recipient), recipient);
                            }
                            break;
                        case 3:
                            if (!isRecipientStore) {
                                editMessage(recipient);
                            } else {
                                editMessage(storeNameMap.get(recipient), recipient);
                            }
                            break;
                        case 4:
                            if (!isRecipientStore) {
                                deleteMessage(recipient);
                            } else {
                                deleteMessage(storeNameMap.get(recipient), recipient);
                            }
                            break;
                        case 5:
                            String path;
                            System.out.println("Please enter the path to the text file you would like to import or " +
                                    "type 'cancel' to Cancel.");
                            do {
                                path = buyerScan.nextLine();
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
                        case 6:
                            System.out.println("Enter the path where you would like the csv file to be stored:");
                            String csvPath = buyerScan.nextLine();
                            writeCSV(recipient, csvPath);
                    }
                    System.out.println("Would you like to continue interacting with this user?\n1. Yes\n2. No");
                    int stayWithUser = waitForValidInput(1, 2);
                    if (stayWithUser == 2) {
                        break;
                    }
                } while (true);
            }
        } while (true);
        System.out.println(SELECT_OPTION);
        System.out.println(METRICS_PROMPT);
        int goToMetrics = waitForValidInput(1, 2);
        if (goToMetrics == 2) {
            return;
        } else {
            MetricManager.sellerMetricsUI(this.username, buyerScan, storeNameMap);
        }

    }

    /** A static method that will change the names of files and directories to match username
     * @param username the username that is currently stored everywhere
     * @author Kevin Jones
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
     * @author Kevin Jones
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
     * @author Kevin Jones
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
        if (isSeller) {
            this.mainForSeller();
        }
        else {
            this.mainForBuyer();
        }
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
     * Get a list of users that this user can see
     * @return an array of available people
     * @throws IOException if error
     */
    public String[] getAvailableUsers() throws IOException {
        ArrayList<String> available = new ArrayList<>();
        if(!isSeller){
            File sellersDir = new File("data/sellers");
            String[] sellers = sellersDir.list();
            for(String seller: sellers) {
                File sellerFolder = new File("data/sellers/" + seller);
                File invisibleFilePath = new File("data/sellers/" + seller + "/isInvisible.txt");
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
                File invisibleFilePath = new File("data/buyers/" + buyer + "/isInvisible.txt");
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
     * @throws IOException if error
     */
    public String[] getMessage_ableUser() throws IOException {
        ArrayList<String> available = new ArrayList<>();
        if(!isSeller){
            File sellersDir = new File("data/sellers");
            String[] sellers = sellersDir.list();
            for(String seller: sellers) {
                File sellerFolder = new File("data/sellers/" + seller);
                File invisibleFilePath = new File("data/sellers/" + seller + "/hasBlocked.txt");
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
                File invisibleFilePath = new File("data/buyers/" + buyer + "/hasBlocked.txt");
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
        String[] message_able = new String[available.size()];
        for(int i = 0; i < message_able.length; i++) {
            message_able[i] = available.get(i);
        }
        return message_able;
    }


    /**
     * Get a list of stores this user can see
     * @return an array of available stores
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

    /**
     * Get a list of stores this user can see
     * @return an array of available stores
     * @throws IOException in case of error
     */
    public String[] getMessage_ableStores() throws IOException {
        boolean invisible = true;
        String[] possibleSellers = getMessage_ableUser();
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
        String[] message_ableStores = new String[possibleStores.size()];
        message_ableStores = possibleStores.toArray(message_ableStores);
        return message_ableStores;
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
                path = FileManager.getDirectoryFromUsername(username);
            } catch (UserNotFoundException u) {
                System.out.println("Unable to load message");
            }
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader(path + username + recipient + ".txt"))) {
            String line = bfr.readLine();
            if (line == null) {
                System.out.println("No message content to display!");
            }
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
                if (isSeller) {
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
                    if (isSeller) {
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
                    if (isSeller) {
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
            String invisibleFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/isInvisible.txt";
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
        String blockedFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/hasBlocked.txt";
        File blockedFile = new File(blockedFilePath);
        BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
        String line;
        while ((line = bfr.readLine()) != null) {
            if (!line.isEmpty()) {
                victims.add(line);
            }
        }
        String[] blockedList = new String[victims.size()];
        for (int i = 0; i < victims.size(); i++) {
            blockedList[i] = victims.get(i);
        }
        return blockedList;
    }

    /**
     * Become visible to a user from the isInvisible file
     * return
     * @param username: name of person to unblock
     */
    public void becomeVisibleAgain(String username) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            String invisibleFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/isInvisible.txt";
            File invisibleFile = new File(invisibleFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (!line.equals(username) && !line.isEmpty()) {
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
        String invisibleFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/isInvisible.txt";
        File invisibleFile = new File(invisibleFilePath);
        BufferedReader bfr = new BufferedReader(new FileReader(invisibleFile));
        String line;
        while ((line = bfr.readLine()) != null) {
            if (!line.isEmpty()) {
                victims.add(line);
            }
        }
        String[] invisibleList = new String[victims.size()];
        for (int i = 0; i < victims.size(); i++) {
            invisibleList[i] = victims.get(i);
        }
        return invisibleList;
    }

    /**
     * Blocked a user if not already blocked
     * @param username
     * @return true if already blocked, false otherwise
     */
    public boolean blockUser(String username) {
        try {
            String blockedFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/hasBlocked.txt";
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

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * unblocked a user listed in the blockedList
     * @param username
     */
    public void unblockUser(String username) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            String blockedFilePath = "data/" + ((isSeller) ? "sellers/" : "buyers/") + this.username + "/hasBlocked.txt";
            File blockedFile = new File(blockedFilePath);
            BufferedReader bfr = new BufferedReader(new FileReader(blockedFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (!line.equals(username) && !line.isEmpty()) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//TODO allow sellers to access specific stores
//TODO fix display message
