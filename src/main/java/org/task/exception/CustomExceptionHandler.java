package org.task.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException e) { //Переопределенный метод для возврата сообщения исключения и соответствующего статуса на клиент

        return Response.status(Response.Status.BAD_REQUEST).
                entity(new ErrorMessage(e.getMessage(), false))
                .build();
    }
}

