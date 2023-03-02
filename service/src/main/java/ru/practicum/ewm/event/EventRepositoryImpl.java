package ru.practicum.ewm.event;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Event> getByParams(EventQueryParams params) {
        JPAQuery<Tuple> query = new JPAQuery<>(entityManager)
                .select(QEvent.event, Expressions.ZERO /* TODO */)
                .from(QEvent.event)
                .where(params.getExpression());

        params.setSortAndPage(query);
        return query.stream()
                .map(t -> {
                    Event event = t.get(QEvent.event);
                    if (event == null) {
                        event = new Event();
                    }
                    event.setConfirmedRequests(0);
                    return event;
                })
                .collect(Collectors.toUnmodifiableList());
    }
}