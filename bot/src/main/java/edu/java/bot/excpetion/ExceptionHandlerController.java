package edu.java.bot.excpetion;

import edu.java.models.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponse> badRequestHandle(BadRequestException ex) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity
            .status(badRequest)
            .body(
                ApiErrorResponse.buildResponse(
                    "Bad request",
                    badRequest.toString(),
                    ex
                )
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> serverExceptionHandle(Exception ex) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity
            .status(internalServerError)
            .body(
                ApiErrorResponse.buildResponse(
                    "Server Error",
                    internalServerError.toString(),
                    ex
                )
            );
    }
}
