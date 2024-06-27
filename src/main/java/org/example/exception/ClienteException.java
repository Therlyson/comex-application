package org.example.exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
