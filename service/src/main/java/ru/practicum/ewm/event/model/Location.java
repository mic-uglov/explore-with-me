package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Embeddable
public class Location {
    @NotNull
    @Column(name = "location_lat")
    private Double lat;

    @NotNull
    @Column(name = "location_lon")
    private Double lon;
}