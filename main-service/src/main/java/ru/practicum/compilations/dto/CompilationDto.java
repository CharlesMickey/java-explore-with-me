package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompilationDto {
    private Long id;
    private Boolean pinned;
    private String title;
    private List<EventShortDto> events;
}
