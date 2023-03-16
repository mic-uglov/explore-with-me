package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
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

    private boolean paid;

    private int participantLimit;

    private boolean requestModeration;

    private String title;

    private LocalDateTime createdOn;

    @ManyToOne
    private User initiator;

    private LocalDateTime publishedOn;

    private EventState state;

    @Transient
    private long confirmedRequests;

    @Transient
    private long views;

    public Event(Category category, User initiator) {
        this.category = category;
        this.initiator = initiator;
        this.createdOn = LocalDateTime.now();
        this.state = EventState.PENDING;
    }
}