package ru.practicum.ewm.subscription.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.model.EventShortDto;
import ru.practicum.ewm.user.model.UserShortDto;

import java.util.List;

@Getter
@Setter
public class SubscriptionDto {
    private Long id;
    private UserShortDto subscriber;
    private String name;
    private List<UserShortDto> initiators;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventShortDto> events;
}