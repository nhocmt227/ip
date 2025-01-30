package exception;

/**
 * Custom exception class for handling errors specific to the Jessica application.
 * This class extends {@code Exception} and provides constructors to create exception instances
 * with various levels of detail (message and/or cause).
 */
public class JessicaException extends Exception {
    /**
     * Constructs a new {@code JessicaException} with no detail message or cause.
     */
    public JessicaException() {
        super();
    }

    /**
     * Constructs a new {@code JessicaException} with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public JessicaException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code JessicaException} with the specified detail message and cause.
     *
     * @param message The detail message explaining the reason for the exception.
     * @param cause   The cause of the exception, which can be retrieved later with {@code getCause()}.
     */
    public JessicaException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code JessicaException} with the specified cause.
     *
     * @param cause The cause of the exception, which can be retrieved later with {@code getCause()}.
     */
    public JessicaException(Throwable cause) {
        super(cause);
    }
}
