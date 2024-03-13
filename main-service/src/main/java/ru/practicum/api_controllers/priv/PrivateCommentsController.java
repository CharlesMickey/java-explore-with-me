package ru.practicum.api_controllers.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.service.CommentsService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
@Slf4j
public class PrivateCommentsController {
    private final CommentsService commentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable long userId,
                                    @RequestParam Long eventId,
                                    @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Post request /users/userId, eventId:{}, userId:{},  newCompilationDto: {}",
                userId, eventId, newCommentDto);
        return commentsService.createComment(eventId, userId, newCommentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long userId,
                              @PathVariable long commentId) {
        log.info("Delete request /users/userId/comments/commentId, userId:{}, commentId: {}", userId, commentId);
        commentsService.deleteComment(commentId, userId);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@PathVariable long userId,
                                    @PathVariable long commentId,
                                    @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Patch /users/userId/comments/commentId," +
                "userId;{}, commentId: {}, compilationId: {}", commentId, userId, newCommentDto);
        return commentsService.updateComment(commentId, userId, newCommentDto);
    }
}
