package ru.practicum.categories.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {

    @NotBlank(message = "Field: name. Error: must not be blank. Value: null")
    @Size(min = 1, max = 50, message = "размер названия категории от 1 до 50")
    private String name;
}
