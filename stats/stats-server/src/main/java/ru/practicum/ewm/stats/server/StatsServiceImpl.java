package ru.practicum.ewm.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.common.EndpointHitDto;
import ru.practicum.ewm.stats.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    @Transactional
    public void saveHit(EndpointHitDto dto) {
        dto.setTimestamp(LocalDateTime.now());
        statsRepository.save(StatsMapper.toEndpointHit(dto));
    }

    @Override
    public List<ViewStatsDto> getStats(
            LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        return statsRepository.getStats(start, end, uris, unique).stream()
                .map(StatsMapper::toViewStatsDto).collect(Collectors.toUnmodifiableList());
    }
}