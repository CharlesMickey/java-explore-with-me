package ru.practicum.events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.events.model.Event;
import ru.practicum.statuses.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {

    Optional<Event> findByIdAndState(long id, EventState eventState);

    @Query("SELECT e FROM Event e " +
            "LEFT JOIN e.category c " +
            "WHERE " +
            "(:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) AND " +
            "((:categories) IS NULL OR c.id IN (:categories)) AND " +
            "(:paid IS NULL OR e.paid = :paid) AND " +
            "(COALESCE(:rangeStart, CURRENT_TIMESTAMP) <= e.eventDate) AND " +
            "(cast(:rangeEnd as timestamp)IS NULL OR e.eventDate <= :rangeEnd) AND " +
            "(:onlyAvailable = false OR (:onlyAvailable = true AND (e.participantLimit = 0 OR e.confirmedRequests < e.participantLimit)))")
    Page<Event> findByParameters(@Param("text") String text,
                                 @Param("categories") List<Long> categories,
                                 @Param("paid") Boolean paid,
                                 @Param("rangeStart") LocalDateTime rangeStart,
                                 @Param("rangeEnd") LocalDateTime rangeEnd,
                                 @Param("onlyAvailable") Boolean onlyAvailable,
                                 Pageable pageable);

    boolean existsByCategoryId(long catId);

    List<Event> findAllByIdIn(List<Long> idList);

    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long id, Long userId);


    @Query("SELECT e FROM Event e " +
            "LEFT JOIN e.category c " +
            "LEFT JOIN e.initiator u " +
            "WHERE " +
            "((:users) IS NULL OR u.id IN (:users)) AND " +
            "((:states) IS NULL OR e.state IN (:states)) AND " +
            "((:categories) IS NULL OR c.id IN (:categories)) AND " +
            "(COALESCE(:rangeStart, CURRENT_TIMESTAMP) <= e.eventDate) AND " +
            "(cast(:rangeEnd as timestamp) IS NULL OR e.eventDate <= :rangeEnd) ")
    Page<Event> findByAdminParameters(@Param("users") List<Long> users,
                                      @Param("states") List<EventState> states,
                                      @Param("categories") List<Long> categories,
                                      @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd,
                                      Pageable pageable);


}



