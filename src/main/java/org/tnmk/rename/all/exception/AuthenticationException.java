package org.tnmk.rename.all.exception;

/**
 * @author khoi.tran
 */
public class AuthenticationException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.Security.AuthenticationError;
    private static final long serialVersionUID = -2743935142391308743L;

    public AuthenticationException() {
        //This is used by Feign client
        this(null);
    }

    /**
     * @param message general error message.
     */
    public AuthenticationException(final String message) {
        super(ERROR_CODE, message);
    }

}
