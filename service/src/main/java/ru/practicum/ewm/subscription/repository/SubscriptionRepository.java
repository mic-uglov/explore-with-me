package ru.practicum.ewm.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.subscription.model.Subscription;
import ru.practicum.ewm.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionRepositoryCustom {
    Optional<Subscription> findByIdAndSubscriberId(long id, long subscriberId);

    void deleteByIdAndSubscriberId(long id, long subscriberId);

    @Query("select s from Subscription s where exists (select 1 from s.initiators i where i = ?1)")
    List<Subscription> findAllContainingInitiator(User initiator);
}