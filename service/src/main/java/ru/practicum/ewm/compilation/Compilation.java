package ru.practicum.ewm.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.Event;

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

    private Boolean pinned;

    private String title;

    @ManyToMany
    @JoinTable(name = "compilation_event", inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;
}