package com.esprit.cloudcraft.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class userAlreadyExistException extends RuntimeException{
    @ExceptionHandler(Exception.class)
   public ProblemDetail userAlreadyExistException(Exception ex)

   {   ProblemDetail errorDetail=null;

       errorDetail= ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),ex.getMessage());
      errorDetail.setProperty("user not created reason","user exists");
       return errorDetail;





   }
}
