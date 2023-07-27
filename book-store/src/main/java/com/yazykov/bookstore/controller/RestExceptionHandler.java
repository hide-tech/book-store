package com.yazykov.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationExceptions(SQLException ex) {
        return ex.getMessage();
    }
}
