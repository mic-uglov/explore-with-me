package ru.practicum.ewm.stats.server;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepositoryCustom {
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}