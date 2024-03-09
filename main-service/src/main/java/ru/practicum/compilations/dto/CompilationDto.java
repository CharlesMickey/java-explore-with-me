package ru.practicum.compilations.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.practicum.events.dto.EventShortDto;

@Data
@Builder
public class CompilationDto {

    private Long id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
