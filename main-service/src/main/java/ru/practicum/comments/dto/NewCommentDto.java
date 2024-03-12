package ru.practicum.comments.dto;

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
public class NewCommentDto {

    @NotBlank(message = "Field: text. Error: must not be blank. Value: null")
    @Size(min = 50, max = 1000, message = "Размер комментария от 50 до 1000")
    private String text;
}
