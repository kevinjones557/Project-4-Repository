/** An exception thrown when a User's Directory cannot be accessed.
 * @author Destin Groves
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}
