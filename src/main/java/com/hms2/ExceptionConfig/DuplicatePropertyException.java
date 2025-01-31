package com.hms2.ExceptionConfig;

public class DuplicatePropertyException extends RuntimeException{
    public DuplicatePropertyException(String message){
        super(message);
    }
}
