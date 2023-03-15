package ru.practicum.ewm.subscription.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.subscription.model.SubscriptionDto;
import ru.practicum.ewm.subscription.model.UpdateSubscriptionDto;
import ru.practicum.ewm.subscription.service.SubscriptionService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/subscriptions")
public class AdminSubscriptionController {
    private final SubscriptionService subscriptionService;

    @PatchMapping("/{subscriptionId}")
    public ResponseEntity<SubscriptionDto> update(
            @PathVariable long subscriptionId,
            @RequestBody @Valid UpdateSubscriptionDto dto) {
        return ResponseEntity.ok(subscriptionService.update(subscriptionId, dto));
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> delete(@PathVariable long subscriptionId) {
        subscriptionService.delete(subscriptionId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteInitiator(@RequestParam long initiatorId) {
        subscriptionService.deleteInitiator(initiatorId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> search(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> subscriberIds,
            @RequestParam(required = false) List<Long> initiatorIds,
            @RequestParam(defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE) @Positive int size) {
        return ResponseEntity.ok(subscriptionService.adminSearch(text, subscriberIds, initiatorIds, from, size));
    }
}