package ru.practicum.ewm.subscription.service;

import ru.practicum.ewm.subscription.model.NewSubscriptionDto;
import ru.practicum.ewm.subscription.model.SubscriptionDto;
import ru.practicum.ewm.subscription.model.UpdateSubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    SubscriptionDto create(long userId, NewSubscriptionDto dto);

    SubscriptionDto update(long subscriptionId, UpdateSubscriptionDto dto);

    SubscriptionDto userUpdate(long userId, long subscriptionId, UpdateSubscriptionDto dto);

    void delete(long subscriptionId);

    void userDelete(long userId, long subscriptionId);

    List<SubscriptionDto> getByUser(long userId, boolean onlyRelevant);

    SubscriptionDto getOne(long subscriptionId, boolean onlyRelevant);

    void deleteInitiator(long initiatorId);

    List<SubscriptionDto> adminSearch(
            String text, List<Long> subscriberIds, List<Long> initiatorIds, int from, int size);
}