package ru.practicum.comments.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Long id;

    @Size(min = 50, max = 1000, message = "Размер комментария от 50 до 1000")
    private String text;

    private Long eventId;

    private Long authorId;

    private LocalDateTime created;

}
