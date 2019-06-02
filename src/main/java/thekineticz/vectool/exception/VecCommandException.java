package thekineticz.vectool.exception;

/**
 * Indicates there was some issue constructing the vec command.
 */
public class VecCommandException extends Exception {

    /**
     * Creates a standard VecCommandException.
     */
    public VecCommandException() {
        super();
    }

    /**
     * Creates a new VecCommandException with a custom error message.
     *
     * @param message The error message.
     */
    public VecCommandException(String message) {
        super(message);
    }

    /**
     * Creates a VecCommandException with an exception that caused it to be thrown.
     *
     * @param cause The original exception.
     */
    public VecCommandException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a VecCommandException with a custom error message and the original exception that led to it being thrown.
     *
     * @param message The error message.
     * @param cause   The original exception.
     */
    public VecCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
