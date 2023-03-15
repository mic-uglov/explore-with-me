package ru.practicum.ewm.subscription.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.subscription.model.NewSubscriptionDto;
import ru.practicum.ewm.subscription.model.SubscriptionDto;
import ru.practicum.ewm.subscription.model.UpdateSubscriptionDto;
import ru.practicum.ewm.subscription.service.SubscriptionService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("users/{userId}/subscriptions")
public class PrivateSubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionDto> create(
            @PathVariable long userId,
            @RequestBody @Valid NewSubscriptionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.create(userId, dto));
    }

    @PatchMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionDto> update(
            @PathVariable long userId,
            @PathVariable long subscriptionId,
            @RequestBody @Valid UpdateSubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.userUpdate(userId, subscriptionId, dto));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> delete(@PathVariable long userId, @PathVariable long subscriptionId) {
        subscriptionService.userDelete(userId, subscriptionId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getByUser(
            @PathVariable long userId,
            @RequestParam(defaultValue = "true") boolean onlyRelevant) {
        return ResponseEntity.ok(subscriptionService.getByUser(userId, onlyRelevant));
    }

    @GetMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionDto> get(
            @PathVariable long subscriptionId,
            @RequestParam(defaultValue = "true") boolean onlyRelevant) {
        return ResponseEntity.ok(subscriptionService.getOne(subscriptionId, onlyRelevant));
    }
}