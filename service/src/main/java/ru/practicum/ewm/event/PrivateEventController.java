package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.ParticipationRequestDto;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getByUser(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE) int size) {
        EventQueryParams params = EventQueryParams.getBuilder()
                .users(List.of(userId))
                .from(from)
                .size(size)
                .build();

        return ResponseEntity.ok(eventService.search(params));
    }

    @PostMapping
    public ResponseEntity<EventFullDto> create(
            @PathVariable long userId,
            @RequestBody @Valid NewEventDto newEventDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(userId, newEventDto));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> get(
            @PathVariable long userId,
            @PathVariable long eventId) {
        return ResponseEntity.ok(eventService.getEventOfUser(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> update(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return ResponseEntity.ok(eventService.userUpdate(userId, eventId, updateEventUserRequest));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(
            @PathVariable long userId,
            @PathVariable long eventId) {
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateRequests(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return ResponseEntity.ok(null);
    }
}