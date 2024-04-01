package com.laba.OrderService.exception;

public class GeneralException extends RuntimeException{


    public GeneralException() {

    }
    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

}
