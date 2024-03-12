package ru.practicum.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.events.model.Event;
import ru.practicum.users.model.User;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
@Generated("AutoGeneratedByMapStruct")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "event", target = "event")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "created", target = "created")
    Comment newCommentToComment(NewCommentDto newCommentDto, User author, Event event, LocalDateTime created);

    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "authorId", source = "author.id")
    CommentDto commentToCommentDto(Comment comment);

    List<CommentDto> commentToCommentDto(List<Comment> comments);
}
