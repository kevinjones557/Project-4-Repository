import java.io.IOException;

/**
 * An interface to set up original functionality of MarketUser
 *
 * @author Kevin Jones
 * @version November 2022
 */
public interface User {
    // private String username (handed to class in constructor)
    // private boolean isSeller (passed to constructor)

    void message(); /* username is of person who is logged in
     while true, prompt if user wants to send message, username/store name of recipient
                            ask for message that you want to send*/

    // check if <username><recipient>.txt exits in directory or not
    void checkIfMessageExists(String recipient, boolean isStore);

    /*Return an array of username that this user can message. Buyers can only message
    sellers, sellers can only message buyers, and exclude from the list any users that
     block this user*/
    String[] getAvailableUsers() throws IOException;

    // add to both files
    void appendMessage(String recipient);

    // somehow figure out which one to edit, edit both files
    void editMessage(String recipient);

    // delete from <username><recipient>.txt somehow figure out which one to delete
    void deleteMessage(String recipient);

    // check if recipient is seller
    boolean blockUser(String recipient) throws IOException;


}
