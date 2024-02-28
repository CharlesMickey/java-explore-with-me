package ru.practicum.events.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.practicum.requests.dto.ParticipationRequestDto;

@Data
@Builder
public class EventRequestStatusUpdateResult {

    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
