package ru.practicum.ewm.appevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.stats.client.StatsClient;
import ru.practicum.ewm.stats.client.StatsClientFactory;

import static ru.practicum.ewm.config.Settings.APP;

@Service
public class StatsService {
    private final StatsClient client;

    @Autowired
    public StatsService(StatsClientFactory clientFactory) {
        client = clientFactory.makeClient(APP);
    }

    @Async
    @EventListener
    public void onStatsEvent(StatsEvent event) {
        client.saveHit(event.getUri(), event.getIp());
    }
}