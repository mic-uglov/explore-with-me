package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> get(@PathVariable long userId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> request(
            @PathVariable long userId,
            @RequestParam long eventId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancel(
            @PathVariable long userId,
            @PathVariable long requestId) {
        return ResponseEntity.ok(null);
    }
}