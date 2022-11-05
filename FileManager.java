import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Handles various file management methods for the program
 * @author Destin Groves
 */
public class FileManager {
    /** Used to find a file directory for a given User. Useful when writing or reading files.
     * @param username The username associated with the user directory you wish to find
     * @return the file directory found associated with the given username.
     * Throws a UserNotFoundException when it cannot find the user.
     * (Yes, this should be an @exception. IntelliJ doesn't understand that now, for some reason)
     */
    public static String getDirectoryFromUsername(String username) throws UserNotFoundException {
        if (Files.exists(Paths.get("data/buyers/"+username))) {
            return "data/buyers/"+username;
        } else if (Files.exists(Paths.get("data/sellers/"+username))) {
            return "data/sellers/"+username;
        } else {
            throw new UserNotFoundException("The requested User does not exist!");
        }
    }

    /**
     * Checks if a User with given username has an associated directory, and therefore exists
     *
     * @param username The username associated with the user directory you wish to find
     * @return if the directory exists
     */
    public static boolean checkUserExists(String username) {
        if (Files.exists(Paths.get("data/buyers/" + username))) return true;
        if (Files.exists(Paths.get("data/sellers/" + username))) return true;
        return false;
    }
    /**
     * Checks if a User with given username has an associated directory, is a buyer or seller, and therefore exists
     *
     * @param username The username associated with the user directory you wish to find
     * @param isSeller The directory to search for the user within
     * @return if the directory exists
     */
    public static boolean checkUserExists(String username, boolean isSeller) {
        if (Files.exists(Paths.get("data/buyers/" + username)) & !isSeller) return true;
        if (Files.exists(Paths.get("data/sellers/" + username)) & isSeller) return true;
        return false;
    }

    /** Used to generate a directory for new Users.
     * @param username The username of the new User you wish to create a directory for.
     * @param isSeller Determines whether the User is a Seller or a Customer.
     * @return true if the directory was created. Returns false if the directory exists, or some other error occurs.
     */
    public static boolean generateDirectoryFromUsername(String username, boolean isSeller) {
        try {
            Path filePath;
            if (isSeller) {
                filePath = Files.createDirectory(Paths.get("data/sellers/" + username));
            } else {
                filePath = Files.createDirectory(Paths.get("data/buyers/" + username));
            }
            Files.createFile(Paths.get(filePath+"/metrics.txt"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
