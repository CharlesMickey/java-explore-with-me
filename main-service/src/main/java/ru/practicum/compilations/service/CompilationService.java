package ru.practicum.compilations.service;

import java.util.List;

import ru.practicum.compilations.dto.CompilationDto;

public interface CompilationService {
    CompilationDto getCompilationById(Long id);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);
}
