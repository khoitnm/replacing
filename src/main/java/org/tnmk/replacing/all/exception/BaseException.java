package org.tnmk.replacing.all.exception;

/**
 * This is the base exception of this project.
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = -8279292782665815881L;
    /**
     * Don't use number for errorCode. It should be String in order to provide the flexibility in usage.
     */
    private final String errorCode;

    private final String errorMessage;

    private final transient Object details;

    public BaseException() {
        this.errorMessage = null;
        this.errorCode = null;
        this.details = null;
    }

    public BaseException(final String errorCode, final String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = null;
    }

    /**
     * @param errorCode view in {@link ExceptionConstants}#ERROR_CODE_XXX.
     * @param message
     * @param details
     */
    public BaseException(final String errorCode, final String message, final Object details) {
        super(message);
        this.errorMessage = message;
        this.errorCode = errorCode;
        this.details = details;
    }

    public BaseException(final String errorCode, final Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.errorMessage = throwable.getMessage();
        this.details = null;
    }

    public BaseException(final String errorCode, final String message, final Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = null;
    }

    public BaseException(final String errorCode, final String message, final Object details, final Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.details = details;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Object getDetails() {
        return this.details;
    }

}
