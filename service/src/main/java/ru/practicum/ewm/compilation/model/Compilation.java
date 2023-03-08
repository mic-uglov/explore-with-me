package ru.practicum.ewm.compilation.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean pinned;

    private String title;

    @ManyToMany
    @JoinTable(name = "compilation_event", inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
}