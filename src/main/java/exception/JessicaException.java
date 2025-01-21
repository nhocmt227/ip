package exception;

public class JessicaException extends Exception {
    public JessicaException() {
        super();
    }

    public JessicaException(String message) {
        super(message);
    }

    public JessicaException(String message, Throwable cause) {
        super(message, cause);
    }

    public JessicaException(Throwable cause) {
        super(cause);
    }
}
