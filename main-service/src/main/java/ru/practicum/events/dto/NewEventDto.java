package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;
import ru.practicum.locations.dto.LocationDto;

@Data
@Builder
public class NewEventDto {

    @NotBlank(message = "Annotation не может быть пустым")
    @Size(min = 20, max = 2000, message = "size must be between 20 and 2000")
    private String annotation;

    @NotBlank(message = "Description не может быть пустым")
    @Size(min = 20, max = 7000, message = "size must be between 20 and 7000")
    private String description;

    @NotBlank(message = "Title не может быть пустым")
    @Size(
            min = 3,
            max = 120,
            message = "Title не менее 3 и не более 120 символов"
    )
    private String title;

    @NotNull(message = "Paid не может быть пустым")
    private Boolean paid;

    @NotNull(message = "Category не может быть пустым")
    @Positive(message = "CategoryId must be positive")
    private Long category;

    @NotNull(message = "Location не может быть пустым")
    private LocationDto location;

    @NotNull(message = "Request moderation не может быть пустым")
    private Boolean requestModeration;

    @NotNull(message = "EventDate не может быть пустым")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "Participant limit не может быть пустым")
    @PositiveOrZero(message = "Participant limit не может быть отрицательным")
    private Integer participantLimit;
}