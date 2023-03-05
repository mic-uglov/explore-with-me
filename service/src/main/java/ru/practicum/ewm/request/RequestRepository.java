package ru.practicum.ewm.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequesterId(long requesterId);

    boolean existsByRequesterIdAndEventId(long requesterId, long eventId);
}