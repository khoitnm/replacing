package org.tnmk.rename.all.exception;

/**
 * Created by khoi.tran on 4/15/16.
 * The error code should never use 0 as prefix to avoid problem when convert to number (if any). <br/>
 * So it should always begin by 1XXX, 2XXX...<br/>
 */
@SuppressWarnings("PMD")
public interface ExceptionConstants {

    /**
     * General: 1XXXXX
     */
    public interface General {
        //Very generic errors
        String UnexpectedError = "100000";
        String BadRequest = "100001";

        //Related to bean, schema, and conversion
        String InvalidSchema = "100101";
        String JsonConversionError = "100201";
    }

    /**
     * <ul>
     * <li>Security: 20XXXX -> 22XXXX </li>
     * <li>+ General security: 20XXXX </li>
     * <li>+ Authentication(Wrong credentials, AccessToken invalid...): 21XXXX </li>
     * <li>- Credentials: 2101XX; </li>
     * <li>- AccessToken: 2102XX; </li>
     * <li>+ Authorization: 22XXXX</li>
     * </ul>
     */
    public interface Security {
        //Authentication
        String AuthenticationError = "210000";
        String InvalidCredentials = "210100";
        String InvalidAccessToken = "210200";
        String ExpiredAccessToken = "210201";

        //Authorization
        String AuthorizationError = "220000";
    }

    /**
     * 30XXXX
     */
    public interface Config {
        String GeneralError = "300100";
    }

    /**
     * 31XXXX - 39XXXX
     */
    public interface Resource {
        String NotExist = "310001";
    }
}
