package ru.practicum.ewm.stats.server;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class StatsRepositoryImpl implements StatsRepositoryCustom {
    private final EntityManager entityManager;

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        QEndpointHit hit = QEndpointHit.endpointHit;
        BooleanExpression whereExpr = hit.timestamp.between(start, end);

        if (uris != null && !uris.isEmpty()) {
            whereExpr = whereExpr.and(hit.uri.in(uris));
        }

        return new JPAQuery<Tuple>(entityManager)
                .select(hit.app, hit.uri, unique ? hit.ip.countDistinct() : Expressions.ONE.count())
                .from(hit)
                .where(whereExpr)
                .groupBy(hit.app, hit.uri)
                .orderBy(Expressions.THREE.desc())
                .stream()
                .map(StatsMapper::toViewStats)
                .collect(Collectors.toUnmodifiableList());
    }
}