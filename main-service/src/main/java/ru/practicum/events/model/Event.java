package ru.practicum.events.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.Category;
import ru.practicum.locations.model.Location;
import ru.practicum.statuses.EventState;
import ru.practicum.users.model.User;

@Entity
@Table(name = "events", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Annotation не может быть пустым")
    @Size(min = 20, max = 2000, message = "size must be between 20 and 2000")
    private String annotation;

    @Column
    @NotBlank(message = "Description не может быть пустым")
    @Size(min = 20, max = 7000, message = "size must be between 20 and 7000")
    private String description;

    @Column
    @NotBlank(message = "Title не может быть пустым")
    @Size(min = 3, max = 120, message = "Title не менее 3 и не более 120 символов")
    private String title;

    @Column
    private Boolean paid;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category не может быть пустым")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @NotNull(message = "Location не может быть пустым")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Column(name = "confirmed_requests")
    private int confirmedRequests;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "event_date")
    @NotNull(message = "EventDate не может быть пустым")
    private LocalDateTime eventDate;

    @Column(name = "participant_limit")
    @PositiveOrZero(message = "Participant limit не может быть отрицательным")
    private int participantLimit;

    @Column
    @Enumerated(EnumType.STRING)
    private EventState state;
}
