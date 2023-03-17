package ru.practicum.ewm.subscription.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User subscriber;

    private String name;

    @ManyToMany
    @JoinTable(name = "subscription_initiator", inverseJoinColumns = @JoinColumn(name = "initiator_id"))
    @OrderBy("id")
    private List<User> initiators;
}