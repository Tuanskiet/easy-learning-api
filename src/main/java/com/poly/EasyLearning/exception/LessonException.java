package com.poly.EasyLearning.exception;

public class LessonException extends RuntimeException {

    public LessonException(String message) {
        super(message);
    }

    public LessonException(String message, Throwable cause) {
        super(message, cause);
    }
}