package org.example.exception;

public class ComexException extends RuntimeException{
    public ComexException(String message, Throwable cause) {
        super(message, cause);
    }
}
