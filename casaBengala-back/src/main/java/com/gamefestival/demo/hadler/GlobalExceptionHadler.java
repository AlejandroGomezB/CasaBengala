package com.gamefestival.demo.hadler;

import com.gamefestival.demo.dto.ErrorMessage;
import com.gamefestival.demo.exception.RegistroException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHadler {
    @ExceptionHandler(RegistroException.class)
    public ResponseEntity<ErrorMessage> handleAccountNotFound(RegistroException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                List.of()
        );
        return ResponseEntity.status(status).body(errorMessage);
    }
    //MAnejar errores de valdiacion de entrada
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Validation failed for request";
        List<String> details = ex.getBindingResult().getFieldErrors().stream().map(this::formatFieldError).toList();

        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                details
        );
        return ResponseEntity.status(status).body(errorMessage);
    }

    private String formatFieldError(FieldError fieldError) {
        return "%s: %s".formatted(fieldError.getField(), fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value");
    }

    //errores Http500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleErrorGenerico(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                status.getReasonPhrase(),
                "Unexpected Error",
                request.getRequestURI(),
                Instant.now(),
                List.of(ex.getClass().getSimpleName())
        );
        return ResponseEntity.status(status).body(errorMessage);
    }
}
