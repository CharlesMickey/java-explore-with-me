package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.statuses.EventState;

@Data
@Builder
public class EventFullDto {

  private Long id;

  private String annotation;

  private String description;

  private String title;

  private Boolean paid;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdOn;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime publishedOn;

  private CategoryDto category;

  private LocationDto location;

  private UserShortDto initiator;

  private Integer confirmedRequests;

  private Boolean requestModeration;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime eventDate;

  private Integer participantLimit;

  private EventState state;

  private int views;
}
