package ru.practicum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final String reason;

    public ConflictException(String message) {
        super(message);
        this.reason = "";

    }

    public ConflictException(String message, String reason) {
        super(message);
        this.reason = reason;
    }


}
