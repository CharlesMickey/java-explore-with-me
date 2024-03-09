package ru.practicum.events.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.requests.dto.ParticipationRequestDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {

    public List<ParticipationRequestDto> confirmedRequests;
    public List<ParticipationRequestDto> rejectedRequests;
}
