package ru.practicum.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ApiError {

    private final List<String> errors;

    private final String message;

    private final String reason;

   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final HttpStatus status;

    private final LocalDateTime timestamp;

}
