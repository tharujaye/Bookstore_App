package com.bookstore.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException ex) {
        if (ex instanceof BookNotFoundException || ex instanceof CustomerNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND) // 404 Not Found
                    .entity(new ErrorMessage("Not Found", ex.getMessage()))
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST) // 400 Bad Request
                    .entity(new ErrorMessage("Bad Request", ex.getMessage()))
                    .build();
        }
    }


    public static class ErrorMessage {
        private String error;
        private String message;

        public ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
