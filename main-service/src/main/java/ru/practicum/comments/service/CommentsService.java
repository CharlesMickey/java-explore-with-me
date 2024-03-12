package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;


import java.util.List;

public interface CommentsService {
    CommentDto getCommentById(Long id);

    List<CommentDto> getCommentsByEvent(Long eventId, Integer from, Integer size);

    CommentDto createComment(Long authorId, Long eventId, NewCommentDto newCommentDto);

    void deleteComment(Long commentId, Long authorId);

    void deleteCommentAdmin(Long commentId);

    CommentDto updateComment(Long commentId, Long authorId, NewCommentDto newCommentDto);
}
