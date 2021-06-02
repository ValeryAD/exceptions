package com.github.valeryad.exceptions;

public class IncorrectGradeException extends Exception {
    public IncorrectGradeException() {
    }

    public IncorrectGradeException(String message) {
        super(message);
    }

    public IncorrectGradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectGradeException(Throwable cause) {
        super(cause);
    }
}
