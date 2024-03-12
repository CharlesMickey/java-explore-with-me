package ru.practicum.comments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comments.model.Comment;

import java.util.Optional;

public interface CommentsRepo extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByEventId(Long id, Pageable pageable);
    Optional<Comment> findByIdAndAuthorId(Long id, Long authorId);
}
