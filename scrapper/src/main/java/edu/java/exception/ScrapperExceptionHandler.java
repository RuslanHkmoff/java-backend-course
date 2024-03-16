package edu.java.exception;

import edu.java.models.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> invalidArgumentHandle(ConstraintViolationException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Invalid arguments",
                    badRequest.toString(),
                    ex
                )
            );
    }

    @ExceptionHandler(ChatAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> chatAlreadyExists(ChatAlreadyExistsException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Re-registration",
                    badRequest.toString(),
                    ex
                )
            );
    }

    @ExceptionHandler(LinkAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> linkAlreadyExists(LinkAlreadyExistsException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Re-add link",
                    badRequest.toString(),
                    ex
                )
            );
    }

    @ExceptionHandler(LinkDoesntExistsException.class)
    public ResponseEntity<ApiErrorResponse> linkDoesntExists(LinkDoesntExistsException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Link not exists",
                    badRequest.toString(),
                    ex
                )
            );
    }

    @ExceptionHandler(ChatDoesntExistsException.class)
    public ResponseEntity<ApiErrorResponse> chatDoesntExists(ChatDoesntExistsException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Chat not exists",
                    badRequest.toString(),
                    ex
                )
            );
    }
}
