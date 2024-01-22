package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class EndpointHitDto {

    @NotBlank(message = "App не может быть пустым")
    private String app;

    @NotBlank(message = "Uri не может быть пустым")
    private String uri;

    @NotBlank(message = "Ip не может быть пустым")
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Timestamp не может быть пустым")
    private LocalDateTime timestamp;
}
