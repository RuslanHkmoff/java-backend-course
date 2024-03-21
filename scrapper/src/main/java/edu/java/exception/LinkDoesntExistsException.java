package edu.java.exception;

public class LinkDoesntExistsException extends RuntimeException {
    public LinkDoesntExistsException(String message) {
        super(message);
    }

    public LinkDoesntExistsException() {
    }
}
