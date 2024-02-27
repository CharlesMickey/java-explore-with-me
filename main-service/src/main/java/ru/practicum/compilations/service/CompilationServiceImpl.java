package ru.practicum.compilations.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.compilations.repository.CompilationRepo;
import ru.practicum.exception.NotFoundException;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepo compilationRepo;
    private final CompilationMapper compilationMapper;

    public CompilationDto getCompilationById(Long id) {
        Compilation compilation = compilationRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Compilation", id)));

        return compilationMapper.compilatonsToCompilationsDto(compilation);
    }


    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = CustomPageRequest.customOf(from, size);

        if (pinned == null) return compilationMapper
                .compilatonsToCompilationsDto(compilationRepo.findAll(pageable).getContent());

        return compilationMapper
                .compilatonsToCompilationsDto(compilationRepo.findAllByPinned(pinned, pageable).getContent());
    }

}
