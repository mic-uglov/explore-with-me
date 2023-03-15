package ru.practicum.ewm.subscription.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.EventOrder;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.service.EventQueryParams;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.subscription.model.*;
import ru.practicum.ewm.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional
    public SubscriptionDto create(long userId, NewSubscriptionDto dto) {
        User subscriber = userService.getUser(userId);
        List<Long> initiatorIds = dto.getInitiators();
        List<User> initiators;

        if (initiatorIds == null || initiatorIds.isEmpty()) {
            initiators = Collections.emptyList();
        } else {
            initiators = userService.getUsers(initiatorIds);
        }

        return SubscriptionMapper.toDto(
                subscriptionRepository.save(SubscriptionMapper.toSubscription(
                        subscriber, dto.getName(), initiators)));
    }

    @Override
    @Transactional
    public SubscriptionDto update(long subscriptionId, UpdateSubscriptionDto dto) {
        return update(getSubscription(subscriptionId), dto);
    }

    @Override
    @Transactional
    public SubscriptionDto userUpdate(long userId, long subscriptionId, UpdateSubscriptionDto dto) {
        return update(getSubscription(subscriptionId, userId), dto);
    }

    @Override
    @Transactional
    public void delete(long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Override
    @Transactional
    public void userDelete(long userId, long subscriptionId) {
        subscriptionRepository.deleteByIdAndSubscriberId(subscriptionId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDto> getByUser(long userId, boolean onlyRelevant) {
        return subscriptionRepository.findAllBySubscriberIdAndRelevance(userId, onlyRelevant).stream()
                .map(SubscriptionMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionDto getOne(long subscriptionId, boolean onlyRelevant) {
        Subscription subscription = getSubscription(subscriptionId);
        EventQueryParams params = EventQueryParams.getBuilder()
                .states(List.of(EventState.PUBLISHED))
                .rangeStart(LocalDateTime.now())
                .users(subscription.getInitiators().stream()
                        .map(User::getId).collect(Collectors.toUnmodifiableList()))
                .sort(EventOrder.EVENT_DATE)
                .build();

        return SubscriptionMapper.toDto(subscription, eventService.search(params));
    }

    @Override
    @Transactional
    public void deleteInitiator(long initiatorId) {
        User initiator = userService.getUser(initiatorId);

        subscriptionRepository.findAllContainingInitiator(initiator).forEach(
                s -> s.getInitiators().remove(initiator));
    }

    @Override
    public List<SubscriptionDto> adminSearch(
            String text, List<Long> subscriberIds, List<Long> initiatorIds, int from, int size) {
        return subscriptionRepository.findAllByParams(text, subscriberIds, initiatorIds, from, size).stream()
                .map(SubscriptionMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    private Subscription getSubscription(long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Подписка id=" + subscriptionId + " не найдена"));
    }

    @Transactional(readOnly = true)
    private Subscription getSubscription(long subscriptionId, long userId) {
        return subscriptionRepository.findByIdAndSubscriberId(subscriptionId, userId)
                .orElseThrow(() -> new NotFoundException(
                        "Подписка id=" + subscriptionId + " пользователя id=" + userId + " не найдена"));
    }

    @Transactional
    private SubscriptionDto update(Subscription subscription, UpdateSubscriptionDto dto) {
        if (dto.getName() != null) {
            subscription.setName(dto.getName());
        }

        List<Long> initiatorIds = dto.getInitiatorIds();
        if (initiatorIds != null && !initiatorIds.isEmpty()) {
            subscription.setInitiators(
                    userService.getUsers(initiatorIds).stream()
                            .collect(Collectors.toUnmodifiableSet()));
        }

        return SubscriptionMapper.toDto(subscription);
    }
}