package ru.practicum.compilations.service;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.mapper.CompilationMapper;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.compilations.repository.CompilationRepo;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.NotFoundException;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepo compilationRepo;
    private final CompilationMapper compilationMapper;
    private final EventRepo eventRepo;

    @Override
    public CompilationDto getCompilationById(Long id) {
        Compilation compilation = compilationRepo
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Compilation", id)));

        return compilationMapper.compilatonsToCompilationsDto(compilation);
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = CustomPageRequest.customOf(from, size);

        if (pinned == null)
            return compilationMapper.compilatonsToCompilationsDto(compilationRepo.findAll(pageable).getContent());

        return compilationMapper
                .compilatonsToCompilationsDto(compilationRepo.findAllByPinned(pinned, pageable).getContent());
    }

    @Override
    @Transactional
    public CompilationDto createdCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events =
                newCompilationDto.getEvents() != null
                        ? eventRepo.findAllByIdIn(newCompilationDto.getEvents())
                        : Collections.emptyList();

        Compilation compilation = compilationRepo
                .save(compilationMapper.compilationToNewCompilationDto(newCompilationDto, events));
        return compilationMapper.compilatonsToCompilationsDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compilationId) {
        getCompilationById(compilationId);

        compilationRepo.deleteById(compilationId);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(long compilationId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationMapper.compilationDtoToCompilation(getCompilationById(compilationId));

        if (updateCompilationRequest.getEvents() != null && !updateCompilationRequest.getEvents().isEmpty()) {
            final List<Event> events = eventRepo.findAllByIdIn(updateCompilationRequest.getEvents());
            compilation.setEvents(events);
        }

        if (updateCompilationRequest.getTitle() != null && !updateCompilationRequest.getTitle().isBlank()) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }

        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }

        Compilation updCompilation = compilationRepo.save(compilation);


        return compilationMapper.compilatonsToCompilationsDto(updCompilation);
    }
}
