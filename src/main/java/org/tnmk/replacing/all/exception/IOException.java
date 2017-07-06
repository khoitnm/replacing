package org.tnmk.replacing.all.exception;

/**
 * This is a very general exception.
 * We only throw this exception only if there's something wrong and the root cause is totally out of our awareness.<br/>
 * <p>
 * <pre>
 * For example:
 * With the logic of your code, you will have a sorted list. Then you call binarySearch(sortedList).
 * But inside your binarySearch(), you find out that input list was not sorted, throw UnexpectedException.
 * </pre>
 */
public class IOException extends BaseException {

    private static final long serialVersionUID = -2947099715615663831L;
    private static final String ERROR_CODE = ExceptionConstants.General.UnexpectedError;

    public IOException() {
        //This is used by Feign client
        this(null);
    }

    public IOException(final String message) {
        super(ERROR_CODE, message);
    }

    public IOException(final String message, final Throwable throwable) {
        super(ERROR_CODE, message, throwable);
    }
}
