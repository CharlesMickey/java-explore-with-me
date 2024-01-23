package ru.practicum.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {
    @Query("SELECT new ru.practicum.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM Hit hit " +
            "WHERE hit.timestamp BETWEEN :start AND :end AND hit.uri IN :uris " +
            "GROUP BY hit.app, hit.uri")
    List<ViewStats> getViewStatsUniq(@Param("start") LocalDateTime start,
                                     @Param("end") LocalDateTime end,
                                     @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.model.ViewStats(hit.app, hit.uri, COUNT(hit.ip)) " +
            "FROM Hit hit " +
            "WHERE hit.timestamp BETWEEN :start AND :end AND hit.uri IN :uris " +
            "GROUP BY hit.app, hit.uri")
    List<ViewStats> getViewStatsNotUniq(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end,
                                        @Param("uris") List<String> uris);


    @Query("SELECT new ru.practicum.model.ViewStats(hit.app, hit.uri, COUNT(DISTINCT hit.ip)) " +
            "FROM Hit hit " +
            "WHERE hit.timestamp BETWEEN :start AND :end " +
            "GROUP BY hit.app, hit.uri")
    List<ViewStats> getViewStatsUniq(@Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("SELECT new ru.practicum.model.ViewStats(hit.app, hit.uri, COUNT(hit.ip)) " +
            "FROM Hit hit " +
            "WHERE hit.timestamp BETWEEN :start AND :end " +
            "GROUP BY hit.app, hit.uri")
    List<ViewStats> getViewStatsNotUniq(@Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
}
