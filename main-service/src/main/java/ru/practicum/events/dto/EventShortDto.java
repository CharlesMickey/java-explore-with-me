package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.statuses.EventState;
import ru.practicum.users.dto.UserShortDto;

@Data
@Builder
public class EventShortDto {

    private Long id;

    private String annotation;

    private String title;

    private Boolean paid;

    private CategoryDto category;

    private UserShortDto initiator;

    private Integer confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private int views;
}
