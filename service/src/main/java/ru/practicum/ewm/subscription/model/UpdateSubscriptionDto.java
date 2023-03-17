package ru.practicum.ewm.subscription.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.NotBlankOrNull;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UpdateSubscriptionDto {
    @NotBlankOrNull
    @Size(max = 64)
    private String name;
    private List<Long> initiators;
}
