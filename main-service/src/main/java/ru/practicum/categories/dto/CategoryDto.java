package ru.practicum.categories.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "У категории должно быть название")
    @Size(min = 1, max = 100, message = "размер названия категории от 1 до 100")
    private String name;
}
