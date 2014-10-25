package view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
  private static final Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);

  public Response toResponse(Throwable t) {
    log.error("Uncaught exception thrown by REST service", t);
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .build();
  }
}