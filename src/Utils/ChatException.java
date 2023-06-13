package Utils;

/**
 * This class represents a chat exception.
 * @see Exception
 */
public class ChatException extends Exception {
    public ChatException(String message) {
        /*
        This constructor calls the constructor of the super class
         */
        super(message);
    }

    public ChatException(String message, Throwable cause) {
        /*
        This constructor calls the constructor of the super class
         */
        super(message, cause);
    }
}
