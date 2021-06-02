package com.github.valeryad.exceptions;

public class NoSubjectsExcetption extends Exception{
    public NoSubjectsExcetption() {
    }

    public NoSubjectsExcetption(String message) {
        super(message);
    }

    public NoSubjectsExcetption(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSubjectsExcetption(Throwable cause) {
        super(cause);
    }
}
