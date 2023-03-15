package ru.practicum.ewm.subscription.repository;

import ru.practicum.ewm.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionRepositoryCustom {
    List<Subscription> findAllBySubscriberIdAndRelevance(long subscriberId, boolean onlyRelevant);

    List<Subscription> findAllByParams(
            String text, List<Long> subscriberIds, List<Long> initiatorIds, int from, int size);
}