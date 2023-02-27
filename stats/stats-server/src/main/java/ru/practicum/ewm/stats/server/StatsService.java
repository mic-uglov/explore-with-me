package ru.practicum.ewm.stats.server;

import ru.practicum.ewm.stats.common.EndpointHitDto;
import ru.practicum.ewm.stats.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void saveHit(EndpointHitDto dto);

    List<ViewStatsDto> getStats(
            LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}