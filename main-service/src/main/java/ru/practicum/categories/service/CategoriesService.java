package ru.practicum.categories.service;

import java.util.List;

import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.model.Category;

public interface CategoriesService {
    CategoryDto getCategoryDtoById(Long id);

    Category getCategoryById(Long id);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Long id);

    CategoryDto updateCategory(Long id, NewCategoryDto newCategoryDto);

}
