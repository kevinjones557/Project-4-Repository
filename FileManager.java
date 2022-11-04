import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Handles various file management methods for the program
 * @author Destin Groves
 */
public class FileManager {

    /** Used to find a file directory for a given User. Useful when writing or reading files.
     * @param username The username associated with the user directory you wish to find
     * @return the file directory found associated with the given username.
     * @exception UserNotFoundException when there is no directory that corresponds with the given username.
     */
    public String getDirectoryFromUsername(String username) throws UserNotFoundException {
        if (Files.exists(Paths.get("data/buyers/"+username))) {
            return "data/buyers/"+username;
        } else if (Files.exists(Paths.get("data/sellers/"+username))) {
            return "data/sellers/"+username;
        } else {
            throw new UserNotFoundException("The requested User does not exist!");
        }
    }

    /** Used to generate a directory for new Users.
     * @param username The username of the new User you wish to create a directory for.
     * @param isSeller Determines whether the User is a Seller or a Customer.
     * @return true if the directory was created. Returns false if the directory exists, or some other error occurs.
     */
    public boolean generateDirectoryFromUsername(String username, boolean isSeller) {
        try {
            if (isSeller) {
                Files.createDirectory(Paths.get("data/sellers/"+username));
            } else {
                Files.createDirectory(Paths.get("data/buyers/"+username));
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
