import java.io.IOException;

public interface User {
    // private String username (handed to class in constructor)
    // private boolean isSeller (passed to constructor)

    void message(); /* username is of person who is logged in
     while true, prompt if user wants to send message, username/store name of recipient
                            ask for message that you want to send*/
    boolean checkIfRecipientExists(String recipient); // DESTIN: look through directories and check for name

    void checkIfMessageExists(String recipient);// check if <username><recipient>.txt exits in directory or not

    String[] getAvailableUsers() throws IOException;/*Return an array of username that this user can message. Buyers can only message
    sellers, sellers can only message buyers, and exclude from the list any users that block this user*/
    void appendMessage(String recipient); // add to both files

    void editMessage(String recipient); // somehow figure out which one to edit, edit both files

    void deleteMessage(String recipient); // delete from <username><recipient>.txt somehow figure out which one to delete

    boolean blockUser (String recipient) throws IOException; // check if recipient is seller


}

/*
1. Kevin 05:09:23: Hi
2. John : How ar you
3. John: whats up
Kevin: not much
Kevin: how are you

 */
