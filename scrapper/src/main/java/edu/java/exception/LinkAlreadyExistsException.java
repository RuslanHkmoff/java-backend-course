package edu.java.exception;

public class LinkAlreadyExistsException extends RuntimeException {
    public LinkAlreadyExistsException(String message) {
        super(message);
    }

    public LinkAlreadyExistsException() {
    }
}
