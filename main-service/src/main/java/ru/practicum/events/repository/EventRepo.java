package ru.practicum.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.Event;
import ru.practicum.statuses.EventState;

import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndState(long id, EventState eventState);
}
