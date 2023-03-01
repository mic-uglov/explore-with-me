package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Location {
    @NotNull
    Double lat;

    @NotNull
    Double lon;
}