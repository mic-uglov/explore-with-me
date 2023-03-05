package ru.practicum.ewm.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@NoArgsConstructor
@Getter
@Setter
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime created;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User requester;

    private RequestStatus status;

    public Request(User requester, Event event) {
        this.created = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
        this.requester = requester;
        this.event = event;
    }
}