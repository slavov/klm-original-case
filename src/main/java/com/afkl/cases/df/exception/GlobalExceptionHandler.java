package com.afkl.cases.df.exception;

import com.afkl.cases.df.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    protected ResponseEntity<ErrorResponse> handle4xx(final ClientException clientException,
                                                      final HttpServletRequest request) {

        final var httpStatus = HttpStatus.NOT_FOUND.value();
        final ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.getReasonPhrase(),
                httpStatus, clientException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerException.class)
    protected ResponseEntity<ErrorResponse> handle5xx(final ServerException serverException,
                                                      final HttpServletRequest request) {

        final var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorResponse errorResponse = new ErrorResponse(httpStatus.getReasonPhrase(),
                httpStatus.value(), serverException.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
