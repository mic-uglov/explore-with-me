package ru.practicum.ewm.appevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.client.StatsClientFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.config.Settings.APP;

@Service
public class StatsService {
    private static final String URI_PREFIX = "/events/";
    private final StatsClient client;

    private static String toUri(Long id) {
        return URI_PREFIX + id;
    }

    private static long toEventId(String uri) {
        return Long.parseLong(uri.substring(URI_PREFIX.length()));
    }

    @Autowired
    public StatsService(StatsClientFactory clientFactory) {
        client = clientFactory.makeClient(APP);
    }

    @Async
    @EventListener
    public void onStatsEvent(StatsEvent event) {
        client.saveHit(event.getUri(), event.getIp());
    }

    public Map<Long, Long> getHits(List<Long> eventIds, LocalDateTime start) {
        String uriString = StatsClient.getUriBuilder(start, LocalDateTime.now())
                .uris(eventIds.stream()
                        .map(StatsService::toUri).collect(Collectors.toUnmodifiableList()))
                .build();

        return client.getStats(uriString, StatsService::toEventId);
    }
}