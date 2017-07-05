package org.tnmk.replacing.all.exception;

/**
 * This class is used for the business resource, not the infrastructure resource.
 *
 * @author khoi.tran on 6/5/17.
 */
public class ResourceNotFoundException extends BaseException {

    private static final long serialVersionUID = 770635918234007510L;

    public ResourceNotFoundException() {
        this(ExceptionConstants.Resource.NotExist, null);
    }

    public ResourceNotFoundException(final String message) {
        this(ExceptionConstants.Resource.NotExist, message);
    }

    public ResourceNotFoundException(final String errorCode, final String message) {
        super(errorCode, message);
    }
}
