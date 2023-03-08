package ru.practicum.ewm.event;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;

import java.time.LocalDateTime;
import java.util.List;

public class EventQueryParams {
    private Long id;
    private List<Long> users;
    private List<EventState> states;
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private boolean onlyAvailable;
    private EventOrder sort;
    private Integer from;
    private Integer size;

    public static EventQueryParamsBuilder getBuilder() {
        return new EventQueryParamsBuilder();
    }

    private EventQueryParams() {
    }

    public boolean isOnlyAvailable() {
        return onlyAvailable;
    }

    public EventOrder getSort() {
        return sort;
    }

    public Integer getFrom() {
        return from;
    }

    public Integer getSize() {
        return size;
    }

    public BooleanExpression getExpression() {
        QEvent event = QEvent.event;
        BooleanExpression expr = Expressions.ONE.eq(1);

        if (id != null) {
            expr = event.id.eq(id);
        }

        if (users != null && !users.isEmpty()) {
            expr = expr.and(event.initiator.id.in(users));
        }

        if (states != null && !states.isEmpty()) {
            expr = expr.and(event.state.in(states));
        }

        if (text != null && !text.isBlank()) {
            expr = expr.and(
                    event.annotation.containsIgnoreCase(text).or(event.description.containsIgnoreCase(text)));
        }

        if (categories != null && !categories.isEmpty()) {
            expr = expr.and(event.category.id.in(categories));
        }

        if (paid != null) {
            expr = expr.and(event.paid.eq(paid));
        }

        if (rangeStart != null) {
            expr = expr.and(event.eventDate.goe(rangeStart));
        }

        if (rangeEnd != null) {
            expr = expr.and(event.eventDate.loe(rangeEnd));
        }

        return expr;
    }

    public void setSortAndPage(JPAQuery<?> query) {
        if (sort == EventOrder.VIEWS) {
            query.orderBy(QEvent.event.createdOn.asc());
        } else {
            if (sort == EventOrder.EVENT_DATE) {
                query.orderBy(QEvent.event.eventDate.asc());
            }
            if (size != null) {
                query.limit(size);
            }
            if (from != null && from != 0) {
                query.offset(from);
            }
        }
    }

    public static class EventQueryParamsBuilder {
        private final EventQueryParams params;

        private EventQueryParamsBuilder() {
            this.params = new EventQueryParams();
        }

        public EventQueryParamsBuilder id(Long id) {
            params.id = id;
            return this;
        }

        public EventQueryParamsBuilder users(List<Long> users) {
            params.users = users;
            return this;
        }

        public EventQueryParamsBuilder states(List<EventState> states) {
            params.states = states;
            return this;
        }

        public EventQueryParamsBuilder text(String text) {
            params.text = text;
            return this;
        }

        public EventQueryParamsBuilder categories(List<Long> categories) {
            params.categories = categories;
            return this;
        }

        public EventQueryParamsBuilder paid(Boolean paid) {
            params.paid = paid;
            return this;
        }

        public EventQueryParamsBuilder rangeStart(LocalDateTime rangeStart) {
            params.rangeStart = rangeStart;
            return this;
        }

        public EventQueryParamsBuilder rangeEnd(LocalDateTime rangeEnd) {
            params.rangeEnd = rangeEnd;
            return this;
        }

        public EventQueryParamsBuilder onlyAvailable(Boolean onlyAvailable) {
            params.onlyAvailable = onlyAvailable;
            return this;
        }

        public EventQueryParamsBuilder from(int from) {
            params.from = from;
            return this;
        }

        public EventQueryParamsBuilder size(int size) {
            params.size = size;
            return this;
        }

        public EventQueryParamsBuilder sort(EventOrder sort) {
            params.sort = sort;
            return this;
        }

        public EventQueryParams build() {
            return params;
        }
    }
}