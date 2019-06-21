package de.hskl.swtp.ss19.sqlcoachservice.exception;



import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {

        ErrorMessage errorMessage = ErrorMessage.create(exception);
       
        return Response.status(errorMessage.getErrorCode()).entity(errorMessage)
                .type(MediaType.APPLICATION_JSON).build();

    }
}
