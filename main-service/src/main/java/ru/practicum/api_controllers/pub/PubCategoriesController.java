package ru.practicum.api_controllers.pub;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.dto.CategoryDto;
import ru.practicum.categories.service.CategoriesService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Slf4j
public class PubCategoriesController {

    private final CategoriesService categoriesService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
            @Positive @RequestParam(value = "size", defaultValue = "10") int size) {

        log.info("Get request /categories with params from: {}, size: {}", from, size);
        return categoriesService.getCategories(from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(@PathVariable Long id) {
        log.info("Get request /categories/id: {} ", id);
        return categoriesService.getCategoryDtoById(id);
    }
}
