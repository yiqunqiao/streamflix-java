/**
Student Name - Yiqun Qiao
*/
package exceptions;

/** Reports missing or invalid row values. */
public class InvalidLineException extends Exception {
  
    /**
     * Creates an exception with the given error message.
     * @param message the reason for the error
     */
    public InvalidLineException(String message) {
        super(message);
    }
}

