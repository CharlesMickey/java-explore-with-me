package ru.practicum.categories.service;

import java.util.List;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;

public interface CategoriesService {
    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto createdCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto);

}
