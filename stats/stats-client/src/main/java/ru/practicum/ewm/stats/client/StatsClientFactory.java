package ru.practicum.ewm.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class StatsClientFactory {
    private final String url;
    private final RestTemplateBuilder builder;

    @Autowired
    public StatsClientFactory(@Value("${server.url}") String url, RestTemplateBuilder builder) {
        this.url = url;
        this.builder = builder;
    }

    public StatsClient makeClient(String app) {
        return new StatsClient(app, url, builder);
    }
}