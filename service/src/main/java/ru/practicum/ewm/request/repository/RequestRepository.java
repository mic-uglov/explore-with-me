package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequesterId(long requesterId);

    boolean existsByRequesterIdAndEventId(long requesterId, long eventId);

    List<Request> findByEventInitiatorIdAndEventId(long initiatorId, long eventId);

    List<Request> findByEventIdAndStatusIn(long eventId, List<RequestStatus> statuses);

    List<Request> findByEventIdAndIdIn(long eventId, List<Long> requestIds);

    @Modifying
    @Query("update Request r set r.status = ?3 where r.event.id = ?1 and r.status = ?2")
    void updateStatusByEventIdAndStatus(long eventId, RequestStatus oldStatus, RequestStatus newStatus);
}