package ru.practicum.ewm.stats.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StatsRepository extends
        JpaRepository<EndpointHit, Long>, QuerydslPredicateExecutor<EndpointHit>, StatsRepositoryCustom {
}