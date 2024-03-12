package ru.practicum.api_controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.service.CommentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
@Slf4j
public class AdminCommentsController {

    private final CommentsService commentsService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCommentAdmin(@PathVariable long commentId) {
        log.info("Delete request /admin/comments, id: {}", commentId);
        commentsService.deleteCommentAdmin(commentId);
    }
}
