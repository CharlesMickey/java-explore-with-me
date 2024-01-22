package ru.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.Hit;
import ru.practicum.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatsHitMapper {

    StatsHitMapper INSTANCE = Mappers.getMapper(StatsHitMapper.class);

    EndpointHitDto toEndpointHitDto(Hit hit);

    @Mapping(target = "id", ignore = true)
    Hit toHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> toListViewStatsDto(List<ViewStats> list);

    ViewStatsDto toViewStatsDto(ViewStats viewStats);


}
