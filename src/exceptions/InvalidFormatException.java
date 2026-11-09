/**
Student Name - Yiqun Qiao
*/
package exceptions;

/** Reports invalid data formats. */
public class InvalidFormatException extends Exception {

    /**
     * Creates an exception with the given error message.
     * @param message the reason for the error
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}

