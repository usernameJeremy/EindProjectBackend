package nl.example.boodschappenbezorgapp.Exceptions;

import java.io.Serial;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public BadRequestException() {
        super();
    }
    public BadRequestException(String message) {
        super(message);
    }
}