package ru.practicum.ewm.subscription.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;
import ru.practicum.ewm.event.model.EventShortDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class SubscriptionMapper {
    public static Subscription toSubscription(User subscriber, String name, List<User> initiators) {
        Subscription subscription = new Subscription();

        subscription.setSubscriber(subscriber);
        subscription.setName(name);
        subscription.setInitiators(initiators.stream().collect(Collectors.toUnmodifiableSet()));

        return subscription;
    }

    public static SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto dto = new SubscriptionDto();

        dto.setId(subscription.getId());
        dto.setSubscriber(UserMapper.toShortDto(subscription.getSubscriber()));
        dto.setName(subscription.getName());
        dto.setInitiators(subscription.getInitiators().stream()
                .map(UserMapper::toShortDto).collect(Collectors.toUnmodifiableList()));

        return dto;
    }

    public static SubscriptionDto toDto(Subscription subscription, List<EventShortDto> events) {
        SubscriptionDto dto = toDto(subscription);

        dto.setEvents(events);

        return dto;
    }
}