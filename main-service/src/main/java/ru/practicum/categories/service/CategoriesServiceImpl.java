package ru.practicum.categories.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.mapper.CategoriesMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.repository.CategoriesRepo;
import ru.practicum.exception.NotFoundException;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepo categoriesRepo;
    private final CategoriesMapper categoriesMapper;

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoriesRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Category", id)));

        return categoriesMapper.categoriesToCategoriesDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = CustomPageRequest.customOf(from, size);

        return categoriesMapper.categoriesToCategoriesDto(categoriesRepo.findAll(pageable).getContent());
    }
}
