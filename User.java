public interface User {
    // private String username (handed to class in constructor)
    // private boolean isSeller (passed to constructor)

    void message(); /* username is of person who is logged in
     while true, prompt if user wants to send message, username/store name of recipient
                            ask for message that you want to send*/

    String[] getAvailableUsers(); /*Return an array of username that this user can message. Buyers can only message
    sellers, sellers can only message buyers, and exclude from the list any users that block this user*/

    boolean checkIfRecipientExists(String recipient, String[] availableUsers); // DESTIN: look through directories and check for name

    boolean checkIfMessageExists(String recipient);// check if <username><recipient>.txt exits in directory or not

    void makeMessageFiles(String recipient); // Make both files: <username><recipient>.txt <recipient><username>.txt

    void appendMessage(String recipient); // add to both files

    void editMessage(String recipient); // somehow figure out which one to edit, edit both files

    void deleteMessage(String recipient); // delete from <username><recipient>.txt somehow figure out which one to delete

    void isRecipientSeller(String recipient); // check if recipient is seller

    void blockUser(String username); //


}
