package ru.practicum.ewm.subscription.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.NotBlankOrNull;

import java.util.List;

@Getter
@Setter
public class UpdateSubscriptionDto {
    @NotBlankOrNull
    private String name;
    private List<Long> initiatorIds;
}
