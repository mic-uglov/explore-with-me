package ru.practicum.ewm.subscription.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class NewSubscriptionDto {
    @NotBlank
    private String name;
    private List<Long> initiators;
}