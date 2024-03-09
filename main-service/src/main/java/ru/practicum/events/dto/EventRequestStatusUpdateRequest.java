package ru.practicum.events.dto;

import java.util.List;
import javax.validation.constraints.*;

import lombok.Builder;
import lombok.Data;
import ru.practicum.statuses.RequestStatus;

@Data
@Builder
public class EventRequestStatusUpdateRequest {

    @NotNull(message = "RequestId не может быть null.")
    @NotEmpty(message = "RequestId не может быть пустым.")
    private List<Long> requestIds;

    @NotNull(message = "Request status не может быть пустым.")
    private RequestStatus status;
}
