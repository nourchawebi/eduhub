package com.esprit.cloudcraft.exception;

public class DuplicateValueException extends RuntimeException{
    public DuplicateValueException(String resource,String field,String duplicateValue){
        super(resource+" with "+field+"= '"+duplicateValue+"' existe in the database");
    }
}
