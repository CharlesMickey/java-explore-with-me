package ru.practicum.api_controllers.pub;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
@Slf4j
public class PubCompilationsController {

    private final CompilationService compilationService;

    @GetMapping()
    public List<CompilationDto> getCompilations(
            @RequestParam(required = false) Boolean pinned,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
            @Positive @RequestParam(value = "size", defaultValue = "10") int size) {

        log.info("Get request /compilations with params state, pinned: {}, from: {}, size: {}", pinned, from, size);

        return compilationService.getCompilations(pinned, from, size);

    }


    @GetMapping("/{id}")
    public CompilationDto getCompilationById(@PathVariable Long id) {

        log.info("Get request /compilations/id: {} ", id);

        return compilationService.getCompilationById(id);

    }

}
