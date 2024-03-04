package ru.practicum.api_controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
@Slf4j
public class AdminCompilationsController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createdCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Post request /admin/compilations, newCompilationDto: {}", newCompilationDto);
        return compilationService.createdCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compilationId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compilationId) {
        log.info("Delete request /admin/compilations, id: {}", compilationId);
        compilationService.deleteCompilation(compilationId);
    }

    @PatchMapping("/{compilationId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable long compilationId,
                                      @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {

        log.debug("Patch /admin/compilations, id: {}, compilationId: {}",compilationId, updateCompilationRequest);
        return compilationService.updateCompilation(compilationId, updateCompilationRequest);
    }
}
