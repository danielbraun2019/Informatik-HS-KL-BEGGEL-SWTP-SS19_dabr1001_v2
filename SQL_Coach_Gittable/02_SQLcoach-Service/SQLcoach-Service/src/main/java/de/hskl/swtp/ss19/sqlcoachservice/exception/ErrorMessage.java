package de.hskl.swtp.ss19.sqlcoachservice.exception;


import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * This class contains the error message that is created by all rest-services in case
 * of an error.
 * <p>
 * errorMessage: message for the user
 * appErrorCode: specific application error codes to handle by the frontend
 * errorCode: http error code
 * stacktract: for debugging
 * documentation: additional information (for the user)
 *
 * @author Bastian Beggel
 */

@XmlRootElement
public class ErrorMessage {


    private String errorMessage;
    private int errorCode;
    private String cause;
    private String stacktrace;


    public ErrorMessage() {
    }

    public ErrorMessage(String errorMessage, int errorCode, String cause, String stacktrace) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.cause = cause;
        this.stacktrace = stacktrace;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    static public ErrorMessage create(Throwable throwable) {
        //convert stacktrace to string
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();


        String cause = throwable.getCause() != null ? throwable.getCause().getMessage() : null;
        return new ErrorMessage(throwable.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), cause, stackTrace);

    }

}
