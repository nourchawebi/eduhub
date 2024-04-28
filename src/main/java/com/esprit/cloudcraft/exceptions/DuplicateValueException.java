package com.esprit.cloudcraft.exceptions;

public class DuplicateValueException extends RuntimeException{
    public DuplicateValueException(String resource,String field,String duplicateValue){
        super(resource+" with "+field+"= '"+duplicateValue+"' existe in the database");
    }
}
