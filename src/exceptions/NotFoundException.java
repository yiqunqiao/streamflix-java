/**
Student Name - Yiqun Qiao
*/
package exceptions;

/** Reports a missing TV series ID. */
public class NotFoundException extends Exception {

    /**
     * Creates an exception with the given error message.
     * @param message the reason for the error
     */
    public NotFoundException(String message) {
        super(message);
    }
}

