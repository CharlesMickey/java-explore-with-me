package ru.practicum.comments.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Long id;

    private String text;

    private Long eventId;

    private Long authorId;

    private LocalDateTime created;

}
