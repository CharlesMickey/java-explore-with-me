package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hit", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hit {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String app;

    @Column()
    private String uri;

    @Column()
    private String ip;

    @Column(name = "created")
    private LocalDateTime timestamp;
}
