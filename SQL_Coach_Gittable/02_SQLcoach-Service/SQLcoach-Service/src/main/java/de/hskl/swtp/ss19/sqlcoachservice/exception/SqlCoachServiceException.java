package de.hskl.swtp.ss19.sqlcoachservice.exception;


/**
 * Application exception class.
 *
 */
public class SqlCoachServiceException extends RuntimeException {


    /**
     * Generate exception.
     *
     * @param errorMessage error message
     * @param cause : cause of the exception
     */
    public SqlCoachServiceException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    /**
     * Konstruktor,
     * @param errorMessage Fehlermeldung
     */
    public SqlCoachServiceException(String errorMessage) {
        super(errorMessage);
    }


}


