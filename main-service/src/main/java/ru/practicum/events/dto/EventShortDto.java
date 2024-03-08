package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.utils.Constants;

@Data
@Builder
public class EventShortDto {

    private Long id;

    private String annotation;

    private String title;

    private Boolean paid;

    private CategoryDto category;

    private UserShortDto initiator;

    private int confirmedRequests;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime eventDate;

    private int views;
}
