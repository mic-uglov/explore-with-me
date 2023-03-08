package ru.practicum.ewm.event.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.service.EventQueryParams;
import ru.practicum.ewm.request.model.QRequest;
import ru.practicum.ewm.request.model.RequestStatus;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Event> getByParams(EventQueryParams params) {
        final JPAQuery<Long> subquery = new JPAQuery<>()
                .select(Expressions.ONE.count())
                .from(QRequest.request)
                .where(QRequest.request.event.eq(QEvent.event)
                        .and(QRequest.request.status.eq(RequestStatus.CONFIRMED)));
        BooleanExpression whereExpr = params.getExpression();

        if (params.isOnlyAvailable()) {
            whereExpr = whereExpr.and(subquery.lt(QEvent.event.participantLimit.longValue()));
        }

        JPAQuery<Tuple> query = new JPAQuery<>(entityManager)
                .select(QEvent.event, subquery)
                .from(QEvent.event)
                .where(whereExpr);

        params.setSortAndPage(query);

        return query.stream()
                .map(t -> {
                    Event event = t.get(QEvent.event);
                    if (event == null) {
                        event = new Event();
                    }
                    event.setConfirmedRequests(t.get(1, Long.class));
                    return event;
                })
                .collect(Collectors.toUnmodifiableList());
    }
}