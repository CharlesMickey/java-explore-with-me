package ru.practicum.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.Category;

public interface CategoriesRepo extends JpaRepository<Category, Long> {
    boolean existsByIdNotAndName(long catId, String name);
}
