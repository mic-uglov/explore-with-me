package ru.practicum.ewm.subscription.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class NewSubscriptionDto {
    @NotBlank
    @Size(max = 64)
    private String name;
    private List<Long> initiators;
}