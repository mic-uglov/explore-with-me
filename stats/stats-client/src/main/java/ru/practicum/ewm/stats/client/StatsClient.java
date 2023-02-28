package ru.practicum.ewm.stats.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
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
import java.util.stream.Collectors;

import static ru.practicum.ewm.stats.common.Settings.DATE_TIME_PATTERN;

public class StatsClient {
    private final RestTemplate rest;
    private final String app;

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

    public Map<String, Long> getStats(LocalDateTime start, LocalDateTime end,
                                      @Nullable List<String> uris, @Nullable Boolean unique) {
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString("/stats");

        ucb.queryParam("start", start.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        ucb.queryParam("end", end.format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        if (uris != null) {
            ucb.queryParam("uris", uris);
        }
        if (unique != null) {
            ucb.queryParam("unique", unique);
        }

        ResponseEntity<ViewStatsDto[]> response = rest.getForEntity(ucb.build().toUriString(), ViewStatsDto[].class);

        if (response.getBody() != null) {
            return Arrays.stream(response.getBody())
                    .collect(Collectors.toMap(ViewStatsDto::getUri, ViewStatsDto::getHits));
        }

        return Collections.emptyMap();
    }
}