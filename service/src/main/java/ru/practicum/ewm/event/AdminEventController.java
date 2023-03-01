package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;
import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {
    @GetMapping
    public ResponseEntity<List<EventFullDto>> search(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE) int size) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> update(
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        return ResponseEntity.ok(null);
    }
}