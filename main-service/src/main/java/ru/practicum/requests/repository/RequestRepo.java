package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepo extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    boolean existsByRequesterIdAndEventId(Long requesterId, Long eventId);

    Optional<Request> findByIdAndRequesterId(Long id, Long requesterId);

    List<Request> findAllByIdIn(List<Long> requestIds);
}
