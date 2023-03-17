package ru.practicum.ewm.subscription.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.subscription.model.QSubscription;
import ru.practicum.ewm.subscription.model.Subscription;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<Subscription> findAllBySubscriberIdAndRelevance(long subscriberId, boolean onlyRelevant) {
        JPAQuery<Subscription> query = new JPAQuery<Subscription>(entityManager)
                .from(QSubscription.subscription);
        BooleanExpression whereExpr = QSubscription.subscription.subscriber.id.eq(subscriberId);

        if (onlyRelevant) {
            BooleanExpression subquery = JPAExpressions
                    .selectFrom(QEvent.event)
                    .where(QEvent.event.state.eq(EventState.PUBLISHED)
                            .and(QEvent.event.eventDate.goe(LocalDateTime.now()))
                            .and(QEvent.event.initiator.in(QSubscription.subscription.initiators)))
                    .exists();
            whereExpr = whereExpr.and(subquery);
        }

        return query.where(whereExpr).fetch();
    }

    @Override
    public List<Subscription> findAllByParams(
            String text, List<Long> subscriberIds, List<Long> initiatorIds, int from, int size) {
        QSubscription subscription = QSubscription.subscription;
        JPAQuery<Subscription> query = new JPAQuery<Subscription>(entityManager).from(subscription);
        BooleanExpression whereExpr = Expressions.ONE.eq(Expressions.ONE);

        if (text != null && !text.isBlank()) {
            whereExpr = whereExpr.and(subscription.name.containsIgnoreCase(text));
        }
        if (subscriberIds != null && !subscriberIds.isEmpty()) {
            whereExpr = whereExpr.and(subscription.subscriber.id.in(subscriberIds));
        }
        if (initiatorIds != null && !initiatorIds.isEmpty()) {
            whereExpr = whereExpr.and(subscription.initiators.any().id.in(initiatorIds));
        }

        return query.where(whereExpr).offset(from).limit(size).fetch();
    }
}