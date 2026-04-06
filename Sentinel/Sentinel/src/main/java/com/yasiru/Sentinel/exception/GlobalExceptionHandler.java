package com.yasiru.Sentinel.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().body(
                ApiError.of(400, "Validation Failed", "Invalid request fields",
                        request.getRequestURI(), errors
                )
        );

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request
    ){

        return ResponseEntity.badRequest().body(
                ApiError.of(400,"Bad Request",ex.getMessage(),request.getRequestURI())
        );

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiError.of(401,"Unauthorized","Invalid email or password",request.getRequestURI())
        );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> HandleDisabled(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
          ApiError.of(403,"Forbidden","Accpunt is not active",request.getRequestURI())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(
            Exception ex,HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiError.of(500,"Internal Server Error","Something went wrong",request.getRequestURI())
        );
    }

}
