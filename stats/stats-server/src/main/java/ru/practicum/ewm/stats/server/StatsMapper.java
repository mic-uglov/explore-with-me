package ru.practicum.ewm.stats.server;

import com.querydsl.core.Tuple;
import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.common.EndpointHitDto;
import ru.practicum.ewm.stats.common.ViewStatsDto;

@UtilityClass
public class StatsMapper {
    public static EndpointHit toEndpointHit(EndpointHitDto dto) {
        EndpointHit hit = new EndpointHit();

        hit.setId(dto.getId());
        hit.setApp(dto.getApp());
        hit.setUri(dto.getUri());
        hit.setIp(dto.getIp());
        hit.setTimestamp(dto.getTimestamp());

        return hit;
    }

    public static ViewStats toViewStats(Tuple tuple) {
        ViewStats viewStats = new ViewStats();

        viewStats.setApp(tuple.get(0, String.class));
        viewStats.setUri(tuple.get(1, String.class));
        viewStats.setHits(tuple.get(2, Long.class));

        return viewStats;
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        ViewStatsDto dto = new ViewStatsDto();

        dto.setApp(viewStats.getApp());
        dto.setUri(viewStats.getUri());
        dto.setHits(viewStats.getHits());

        return dto;
    }
}