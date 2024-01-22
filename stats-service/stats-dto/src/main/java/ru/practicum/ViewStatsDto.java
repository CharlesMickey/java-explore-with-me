package ru.practicum;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ViewStatsDto {

    @NotBlank(message = "App не может быть пустым")
    private String app;

    @NotBlank(message = "Uri не может быть пустым")
    private String uri;

    @NotNull(message = "Hits не может быть пустым")
    private  Integer hits;
}
