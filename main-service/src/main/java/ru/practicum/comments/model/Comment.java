package ru.practicum.comments.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field: text. Error: must not be blank. Value: null")
    @Size(min = 50, max = 1000, message = "Размер комментария от 50 до 1000")
    private String text;

    @ManyToOne()
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;

    private LocalDateTime created;
}
