package ru.practicum.compilations.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCompilationDto {

    @NotBlank(message = "Title не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина title от 1 до 50 символов")
    private String title;

    private boolean pinned = false;

    private List<Long> events;
}
