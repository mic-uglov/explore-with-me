package ru.practicum.ewm.stats.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.ewm.stats.common.EndpointHitDto;
import ru.practicum.ewm.stats.common.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.practicum.ewm.stats.common.Settings.DATE_TIME_PATTERN;

public class StatsClient {
    private final RestTemplate rest;
    private final String app;

    public static UriBuilder getUriBuilder(LocalDateTime start, LocalDateTime end) {
        return new UriBuilder(start, end);
    }

    StatsClient(String app, String url, RestTemplateBuilder builder) {
        this.app = app;
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void saveHit(String uri, String ip) {
        EndpointHitDto dto = new EndpointHitDto();

        dto.setApp(app);
        dto.setUri(uri);
        dto.setIp(ip);

        rest.postForEntity("/hit", dto, Object.class);
    }

    public <T> Map<T, Long> getStats(String statsUriString, Function<String, T> transform) {
        ResponseEntity<ViewStatsDto[]> response = rest.getForEntity(statsUriString, ViewStatsDto[].class);

        if (response.getBody() != null) {
            return Arrays.stream(response.getBody())
                    .collect(Collectors.toMap(dto -> transform.apply(dto.getUri()), ViewStatsDto::getHits));
        }

        return Collections.emptyMap();
    }

    public static class UriBuilder {
        private final UriComponentsBuilder ucb;

        private UriBuilder(LocalDateTime start, LocalDateTime end) {
            ucb = UriComponentsBuilder.fromUriString("/stats");
            ucb.queryParam("start", start.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
            ucb.queryParam("end", end.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        }

        public UriBuilder uris(List<String> uris) {
            ucb.queryParam("uris", uris);
            return this;
        }

        @SuppressWarnings("unused")
        public UriBuilder unique(boolean unique) {
            ucb.queryParam("unique", unique);
            return this;
        }

        public String build() {
            return ucb.build().toString();
        }
    }
}