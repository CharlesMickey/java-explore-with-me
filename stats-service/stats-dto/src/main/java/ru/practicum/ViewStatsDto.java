package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewStatsDto {

    @NotBlank(message = "App не может быть пустым")
    private String app;

    @NotBlank(message = "Uri не может быть пустым")
    private String uri;

    @NotNull(message = "Hits не может быть пустым")
    private  Integer hits;
}
