package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.validation.Enumeration;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;
import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    @GetMapping
    public ResponseEntity<List<EventShortDto>> search(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") boolean onlyAvailable,
            @RequestParam(required = false)
            @Enumeration(value = EventOrder.class, message = "Неизвестный порядок сортировки: %s")
            EventOrder sort,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE) int size) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventFullDto> get(@PathVariable long id) {
        return ResponseEntity.ok(null);
    }
}
