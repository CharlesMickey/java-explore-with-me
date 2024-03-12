package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentsRepo;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.NotFoundException;
import ru.practicum.statuses.EventState;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepo commentsRepo;
    private final UserService userService;
    private final EventRepo eventRepo;
    private final CommentMapper commentMapper;


    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentsRepo.findById(id).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Comment", id)));
        return commentMapper.сommentToCommentDto(comment);
    }

    @Override
    public List<CommentDto> getCommentsByEvent(Long eventId, Integer from, Integer size) {
        eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", eventId)));

        Pageable pageable = CustomPageRequest.customOf(from, size);

        return commentMapper.сommentToCommentDto(commentsRepo.findAllByEventId(eventId, pageable).getContent());
    }

    @Override
    @Transactional
    public CommentDto createComment(Long authorId, Long eventId, NewCommentDto newCommentDto) {
        Event event = eventRepo.findByIdAndState(eventId, EventState.PUBLISHED).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", eventId)));

        User author = userService.findUserById(authorId);
        Comment comment = commentsRepo
                .save(commentMapper.newCommentToComment(newCommentDto, author, event, LocalDateTime.now()));

        return commentMapper.сommentToCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteCommentAdmin(Long commentId) {
        getCommentById(commentId);

        commentsRepo.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long authorId) {
        commentsRepo.findByIdAndAuthorId(commentId, authorId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.AUTHOR_NOT_FOUND)));

        commentsRepo.deleteById(commentId);
    }

    @Override
    @Transactional
    public CommentDto updateComment(Long commentId, Long authorId, NewCommentDto newCommentDto) {
        Comment comment = commentsRepo.findByIdAndAuthorId(commentId, authorId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.AUTHOR_NOT_FOUND)));

        if (newCommentDto.getText() != null && !newCommentDto.getText().isBlank()) {
            comment.setText(newCommentDto.getText());
        }

        return commentMapper.сommentToCommentDto(comment);
    }
}
