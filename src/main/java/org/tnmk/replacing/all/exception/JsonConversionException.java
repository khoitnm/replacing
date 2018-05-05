package org.tnmk.replacing.all.exception;

/**
 * Use for wrong fileSize.
 */
public class JsonConversionException extends BaseException {
    public static final String ERROR_CODE = ExceptionConstants.General.JsonConversionError;
    /**
     *
     */
    private static final long serialVersionUID = 2429969213914233228L;

    public JsonConversionException() {
        //This is used by Feign client
        this(null);
    }

    public JsonConversionException(final String message) {
        super(ERROR_CODE, message);
    }

    public JsonConversionException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }

    public JsonConversionException(final String message, final Object detailObject, final Throwable throwable) {
        super(ERROR_CODE, message, detailObject, throwable);
    }

}
