package ru.practicum.locations.service;

import ru.practicum.locations.dto.LocationDto;
import ru.practicum.locations.model.Location;

public interface LocationService {

    Location getLocation(LocationDto locationDto);
}
