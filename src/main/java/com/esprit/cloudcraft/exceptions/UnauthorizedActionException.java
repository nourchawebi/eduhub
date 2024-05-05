package com.esprit.cloudcraft.exceptions;

public class UnauthorizedActionException extends RuntimeException{

    public UnauthorizedActionException(String message){
        super(message);
    }
}
