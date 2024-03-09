package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.locations.dto.LocationDto;
import ru.practicum.statuses.EventState;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.utils.Constants;

@Data
@Builder
public class EventFullDto {

    private Long id;

    private String annotation;

    private String description;

    private String title;

    private Boolean paid;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime createdOn;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime publishedOn;

    private CategoryDto category;

    private LocationDto location;

    private UserShortDto initiator;

    private int confirmedRequests;

    private Boolean requestModeration;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime eventDate;

    private int participantLimit;

    private EventState state;

    private int views;
}
