package com.elija.controller.config;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jooq.exception.*;
import org.postgresql.util.PSQLException;

@Provider
class DataAccessExceptionMapper implements ExceptionMapper<DataAccessException> {
    @Override
    public Response toResponse(DataAccessException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("DataAccess failed")
                .build();
    }
}

@Provider
class IntegrityConstraintViolationExceptionMapper implements ExceptionMapper<IntegrityConstraintViolationException> {
    @Override
    public Response toResponse(IntegrityConstraintViolationException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Database constraint violated")
                .build();
    }
}

@Provider
class TooManyRowsExceptionMapper implements ExceptionMapper<TooManyRowsException> {
    @Override
    public Response toResponse(TooManyRowsException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Expected at most one element - but found more")
                .build();
    }
}

@Provider
class DataChangedExceptionMapper implements ExceptionMapper<DataChangedException> {
    @Override
    public Response toResponse(DataChangedException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Optimistic lock violated")
                .build();
    }
}

@Provider
class DetachedExceptionMapper implements ExceptionMapper<DetachedException> {
    @Override
    public Response toResponse(DetachedException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Persistence entity is detached")
                .build();
    }
}

@Provider
class PSQLExceptionMapper implements ExceptionMapper<PSQLException> {
    @Override
    public Response toResponse(PSQLException exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("A PSQL persistence error occurred")
                .build();
    }
}

/**
 * Generic Mapper that catches every exception - concretes are matched first
 */
@Provider
class GenericExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Exception (%s) occurred".formatted(exception.getClass().getSimpleName()))
                .build();
    }
}

/**
 * Generic Mapper that catches every throwable
 */
@Provider
class GenericThrowableMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable throwable) {
        return Response
                .status(RestResponse.Status.INTERNAL_SERVER_ERROR)
                .entity("Throwable (%s) occurred".formatted(throwable.getClass().getSimpleName()))
                .build();
    }
}
