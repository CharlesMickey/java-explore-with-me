package ru.practicum.compilations.dto;

import java.util.List;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCompilationRequest {

    @Size(min = 1, max = 100, message = "Длина title от 1 до 100 символов")
    private String title;

    private Boolean pinned;

    private List<Long> events;
}
