package com.example.demo.controllers;

import com.example.demo.exceptions.UserBySourceValidationException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.util.ErrorResponse;
import com.example.demo.util.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(MethodArgumentNotValidException ex) {
        final List<ErrorResponse> violations = ex.getAllErrors().stream()
                .map(
                        status -> new ErrorResponse(
                                status.getDefaultMessage()
                        )
                )
                .collect(Collectors.toList());

        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler({UserBySourceValidationException.class,
            UserNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserBySourceValidationException(Exception ex) {
        return handleException(ex);
    }

    private ErrorResponse handleException(Exception ex) {
        return new ErrorResponse(ex.getMessage());
    }

}
