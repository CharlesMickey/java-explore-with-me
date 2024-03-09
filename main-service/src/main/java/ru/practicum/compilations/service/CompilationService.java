package ru.practicum.compilations.service;

import java.util.List;

import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;

public interface CompilationService {
    CompilationDto getCompilationById(Long id);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto createdCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Long compilationId);

    CompilationDto updateCompilation(long compilationId, UpdateCompilationRequest updateCompilationRequest);
}
