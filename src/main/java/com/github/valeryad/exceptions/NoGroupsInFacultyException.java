package com.github.valeryad.exceptions;

public class NoGroupsInFacultyException extends Exception {
    public NoGroupsInFacultyException() {
    }

    public NoGroupsInFacultyException(String message) {
        super(message);
    }

    public NoGroupsInFacultyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoGroupsInFacultyException(Throwable cause) {
        super(cause);
    }
}
