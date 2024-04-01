package com.laba.OrderService.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<String> businessExceptionHandler(GeneralException generalException){
        return new ResponseEntity<>( generalException.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
