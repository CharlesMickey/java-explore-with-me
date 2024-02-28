package ru.practicum.categories.service;

import java.util.List;

import ru.practicum.categories.dto.CategoryDto;

public interface CategoriesService {
    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getCategories(Integer from, Integer size);
}
