package com.esprit.cloudcraft.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidetokenException extends RuntimeException {
 /*   @ExceptionHandler(Exception.class)
    public ProblemDetail invalidetokenException(Exception ex)

    {
        ProblemDetail errorDetail=null;
    errorDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
       errorDetail.setProperty("can't verify mail reason","token expired");

       return errorDetail;}*/
}
