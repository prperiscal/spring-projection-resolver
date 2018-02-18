package com.prperiscal.resolver.projection.exception;

/**
 * <p> Signals that a projection is invalid or duplicated
 *
 * @author <a href="mailto:prperiscal@gmail.com">Pablo Rey Periscal</a>
 * @since 1.0.0
 */
public class IllegalProjectionDefinitionException extends RuntimeException{

    /**
     * Constructs an IllegalProjectionDefinitionException with no detail message.
     * A detail message is a String that describes this particular exception.
     */
    public IllegalProjectionDefinitionException() {
        super();
    }

    /**
     * Constructs an IllegalProjectionDefinitionException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * @param s the String that contains a detailed message
     */
    public IllegalProjectionDefinitionException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A <tt>null</tt> value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.5
     */
    public IllegalProjectionDefinitionException(String message, Throwable cause) {
        super(message, cause);
    }
}
