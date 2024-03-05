package ru.practicum.locations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.locations.dto.LocationDto;
import ru.practicum.locations.mapper.LocationMapper;
import ru.practicum.locations.model.Location;
import ru.practicum.locations.repository.LocationRepo;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepo locationRepo;
    private final LocationMapper locationMapper;

    @Override
    public Location getLocation(LocationDto locationDto) {
        return locationRepo
                .findByLatAndLon(locationDto.getLat(), locationDto.getLon())
                .orElseGet(() -> locationRepo.save(
                        locationMapper.locationDtoToLocation(locationDto)));
    }
}
