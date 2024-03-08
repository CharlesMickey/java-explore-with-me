package ru.practicum.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.RequestMapper;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.repository.RequestRepo;
import ru.practicum.statuses.EventState;
import ru.practicum.statuses.RequestStatus;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;
import ru.practicum.utils.Constants;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;
    private final RequestMapper requestMapper;
    private final UserService userService;
    private final EventRepo eventRepo;

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        userService.checkExistUserById(userId);
        return requestMapper.requestToRequestDto(requestRepo.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createUserRequest(Long userId, Long eventId) {
        User user = userService.findUserById(userId);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", eventId)));

        if (requestRepo.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException(Constants.EXISTS_REQUEST);
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException(Constants.OWN_EVENT_REQUEST);
        }

        if (event.getPublishedOn() == null || !event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(Constants.UNPUBLISHED_EVENT_REQUEST);
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ConflictException(Constants.LIMIT_EVENT_REQUEST);
        }

        Request request = Request.builder()
                .status(((event.getParticipantLimit() == 0) || (!event.getRequestModeration()))
                        ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .build();


        Request savedRequest = requestRepo.save(request);

        if (savedRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepo.save(event);
        }

        return requestMapper.requestToRequestDto(savedRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) {
        userService.checkExistUserById(userId);
        Request request = requestRepo.findByIdAndRequesterId(requestId, userId).orElseThrow(() ->
                new NotFoundException(String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Request", requestId)));

        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.requestToRequestDto(request);
    }
}


