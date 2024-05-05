package com.esprit.cloudcraft.controller;


import com.esprit.cloudcraft.exceptions.DuplicateValueException;
import com.esprit.cloudcraft.exceptions.ResourceNotFoundException;
import com.esprit.cloudcraft.exceptions.UnauthorizedActionException;
import jakarta.validation.ConstraintViolationException;
import com.esprit.cloudcraft.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ConstraintViolationException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> {

            errorMessage
                    .append("field (")
                    .append(violation.getPropertyPath())
                    .append(")")
                    .append(": ")
                    .append(violation.getMessage()).append("|");
        });
        ErrorResponse errorResponse=ErrorResponse.builder().statusCode(400).message(errorMessage.toString()).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse=ErrorResponse.builder().statusCode(404).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }
    @ExceptionHandler(DuplicateValueException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> duplicateValueException(DuplicateValueException ex, WebRequest request) {
        ErrorResponse errorResponse=ErrorResponse.builder().statusCode(400).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> unauthorizedAction(DuplicateValueException ex, WebRequest request) {
        ErrorResponse errorResponse=ErrorResponse.builder().statusCode(403).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }


}
