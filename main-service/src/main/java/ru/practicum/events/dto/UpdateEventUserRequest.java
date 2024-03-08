package ru.practicum.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.locations.dto.LocationDto;
import ru.practicum.statuses.StateAction;
import ru.practicum.utils.Constants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000, message = "size must be between 20 and 2000")
    private String annotation;

    @Size(min = 20, max = 7000, message = "size must be between 20 and 7000")
    private String description;

    @Size(min = 3, max = 120, message = "Title не менее 3 и не более 120 символов")
    private String title;

    private Boolean paid;

    private Long category;

    private LocationDto location;

    private Boolean requestModeration;

    @JsonFormat(pattern = Constants.DATE_FORMAT)
    private LocalDateTime eventDate;

    @PositiveOrZero(message = "От 0 и старше")
    private Integer participantLimit;

    private StateAction stateAction;
}
