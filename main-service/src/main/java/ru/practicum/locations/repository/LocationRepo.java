package ru.practicum.locations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.locations.model.Location;

public interface LocationRepo extends JpaRepository<Location, Long> {
}
