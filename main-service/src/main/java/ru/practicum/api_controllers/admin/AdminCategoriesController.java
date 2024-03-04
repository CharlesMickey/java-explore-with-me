package ru.practicum.api_controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.dto.NewCategoryDto;
import ru.practicum.categories.service.CategoriesService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@Slf4j
public class AdminCategoriesController {
    private final CategoriesService categoriesService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Post request /admin/categories, newCategoryDto: {}", newCategoryDto);

        return categoriesService.createdCategory(newCategoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long categoryId) {
        log.info("Delete request /admin/categories, id: {}", categoryId);
        categoriesService.deleteCategory(categoryId);
    }

    @PatchMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable long categoryId, @Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.debug("Patch request /admin/categories, id: {}, newCategoryDto: {}",categoryId, newCategoryDto);
        return categoriesService.updateCategory(categoryId, newCategoryDto);
    }


}
