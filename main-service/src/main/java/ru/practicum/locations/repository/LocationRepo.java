package ru.practicum.locations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.locations.model.Location;

import java.util.Optional;

public interface LocationRepo extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(Float lat, Float lon);
}
