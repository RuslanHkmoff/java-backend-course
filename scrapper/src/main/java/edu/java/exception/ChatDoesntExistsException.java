package edu.java.exception;

public class ChatDoesntExistsException extends RuntimeException {
    public ChatDoesntExistsException() {
    }

    public ChatDoesntExistsException(String message) {
        super(message);
    }
}
