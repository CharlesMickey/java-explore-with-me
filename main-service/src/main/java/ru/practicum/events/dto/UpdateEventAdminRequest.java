package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;
import ru.practicum.locations.dto.LocationDto;
import ru.practicum.statuses.StateAction;

@Data
@Builder
public class UpdateEventAdminRequest {

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

    private Boolean paid;

    private Long category;

    private LocationDto location;

    private Boolean requestModeration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Integer participantLimit;

    private StateAction stateAction;
}
