import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

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
            return "data/buyers/"+username+"/";
        } else if (Files.exists(Paths.get("data/sellers/"+username))) {
            return "data/sellers/"+username+"/";
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

    public static boolean checkSellerExists(String username) {
        return (Files.exists(Paths.get("data/sellers/" + username)));
    }

    public static boolean checkBuyerExists(String username) {
        return (Files.exists(Paths.get("data/buyers/" + username)));
    }

    public static String getStoreDirectory(String username, String storeName) {
        return String.format("data/sellers/%s/%s/", username, storeName);
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
            Files.createFile(Paths.get(filePath+"/hasBlocked.txt"));
            Files.createFile(Paths.get(filePath+"/isInvisible.txt"));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /** Generates a store subdirectory for a Seller user.
     *
     * @param username the Username of the Seller
     * @param storeName the name of the Store the Seller wishes to make
     * @return true if the directory was created, false if the directory already exists or an exception occurs.
     */
    public static boolean generateStoreForSeller(String username, String storeName) {
        try {
            Path filePath = Files.createDirectory(Paths.get(getDirectoryFromUsername(username) + storeName));
            Files.createFile(Paths.get(filePath+"/metrics.txt"));
            return true;
        } catch (UserNotFoundException e) {
            System.out.println("Seller not found.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static LinkedHashMap<String, String> mapStoresToSellers() {
        LinkedHashMap<String, String> storesMapped = new LinkedHashMap<>();
        String[] sellerList = new File("data/sellers/").list();
        for (String seller : sellerList) {
            String[] storesOwned = new File("data/sellers/"+seller).list();
            for (String store : storesOwned) {
                storesMapped.put(store, seller);
            }
        }
        return storesMapped;
    }
}
