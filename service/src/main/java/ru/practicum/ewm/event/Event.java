package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String annotation;

    @ManyToOne
    private Category category;

    private String description;

    private LocalDateTime eventDate;

    @Embedded
    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private String title;

    private LocalDateTime createdOn;

    @ManyToOne
    private User initiator;

    private LocalDateTime publishedOn;

    private EventState state;

    @Transient
    private Long confirmedRequests;

    @Transient
    private Long views;
}