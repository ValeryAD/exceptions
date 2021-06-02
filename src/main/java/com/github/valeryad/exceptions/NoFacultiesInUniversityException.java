package com.github.valeryad.exceptions;

public class NoFacultiesInUniversityException extends Exception {
    public NoFacultiesInUniversityException() {
    }

    public NoFacultiesInUniversityException(String message) {
        super(message);
    }

    public NoFacultiesInUniversityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFacultiesInUniversityException(Throwable cause) {
        super(cause);
    }
}
