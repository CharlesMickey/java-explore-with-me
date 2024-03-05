package ru.practicum.categories.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.mapper.CategoriesMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoriesRepo;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepo categoriesRepo;
    private final EventRepo eventRepo;
    private final CategoriesMapper categoriesMapper;

    @Override
    public CategoryDto getCategoryDtoById(Long id) {
        Category category = categoriesRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Category", id)));

        return categoriesMapper.categoriesToCategoriesDto(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoriesRepo.findById(id).orElseThrow(() -> new NotFoundException(
                        String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Category", id)));
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = CustomPageRequest.customOf(from, size);

        return categoriesMapper.categoriesToCategoriesDto(categoriesRepo.findAll(pageable).getContent());
    }

    @Override
    @Transactional
    public CategoryDto createdCategory(NewCategoryDto newCategoryDto) {
        try {
            final Category category = categoriesRepo.save(categoriesMapper
                    .categoryNewToCategory(newCategoryDto));

            return categoriesMapper.categoriesToCategoriesDto(category);

            // Обработка ошибки нарушения уникальности DataIntegrityViolationException
        } catch (DataIntegrityViolationException e) {

            throw new ConflictException("Category name must be unique");
        }
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoriesRepo.existsById(id)) {
            throw new NotFoundException(
                    String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Category", id));
        }

        if (eventRepo.existsByCategoryId(id)) {
            throw new ConflictException("The category is not empty",
                    "For the requested operation the conditions are not met.");
        }

        categoriesRepo.deleteById(id);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = getCategoryDtoById(categoryId);

        if (categoriesRepo.existsByIdNotAndName(categoryId, newCategoryDto.getName())) {
            throw new ConflictException("The category name already exist.");
        }

        categoryDto.setName(newCategoryDto.getName());
        Category updCategory = categoriesRepo.save(categoriesMapper.categoriesDtoToCategories(categoryDto));

        return categoriesMapper.categoriesToCategoriesDto(updCategory);
    }
}
