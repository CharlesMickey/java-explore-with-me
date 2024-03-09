package ru.practicum.compilations.model;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.events.model.Event;

@Entity
@Table(name = "compilations", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Title не может быть пустым")
    @Size(min = 1, max = 50, message = "Длина title от 1 до 50 символов")
    private String title;

    @Column
    private Boolean pinned;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private List<Event> events;
}
