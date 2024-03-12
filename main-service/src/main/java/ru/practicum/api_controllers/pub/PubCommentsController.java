package ru.practicum.api_controllers.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.service.CommentsService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
@Slf4j
public class PubCommentsController {
    private final CommentsService commentsService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByEvent(@RequestParam Long eventId,
                                            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                            @Positive @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("Get request /comments with params state, eventId: {}, from: {}, size: {}", eventId, from, size);

        return commentsService.getCommentsByEvent(eventId, from, size);
    }
}
