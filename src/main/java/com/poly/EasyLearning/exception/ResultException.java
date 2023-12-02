package com.poly.EasyLearning.exception;

public class ResultException extends RuntimeException {

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }
}