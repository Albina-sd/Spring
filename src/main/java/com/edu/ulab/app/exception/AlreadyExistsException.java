package com.edu.ulab.app.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message){
        super(message);
    }
}
