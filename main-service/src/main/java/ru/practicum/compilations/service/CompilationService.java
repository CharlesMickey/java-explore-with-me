package ru.practicum.compilations.service;

import ru.practicum.compilations.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto getCompilationById(Long id);

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

}
