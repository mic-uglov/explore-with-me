package ru.practicum.ewm.stats.server;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.stats.common.EndpointHitDto;
import ru.practicum.ewm.stats.common.ViewStatsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.stats.common.Settings.DATE_TIME_PATTERN;

@Controller
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<Void> saveHit(@RequestBody @Valid EndpointHitDto dto) {
        statsService.saveHit(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStatsDto>> getStats(
            @RequestParam
            @DateTimeFormat(pattern = DATE_TIME_PATTERN)
            LocalDateTime start,
            @RequestParam
            @DateTimeFormat(pattern = DATE_TIME_PATTERN)
            LocalDateTime end,
            @RequestParam(required = false)
            List<String> uris,
            @RequestParam(defaultValue = "false")
            boolean unique) {
        return ResponseEntity.ok(statsService.getStats(start, end, uris, unique));
    }
}