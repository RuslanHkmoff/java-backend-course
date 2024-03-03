package edu.java.exception;

public class ChatAlreadyExistsException extends RuntimeException {
    public ChatAlreadyExistsException() {
    }

    public ChatAlreadyExistsException(String message) {
        super(message);
    }
}
