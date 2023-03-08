package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.appevent.StatsService;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.event.model.*;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.service.UserService;
import ru.practicum.ewm.validation.NotBeforeTwoHoursFromNowValidator;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final StatsService statsService;

    @Override
    @Transactional
    public EventFullDto create(long userId, NewEventDto dto) {
        if (!(NotBeforeTwoHoursFromNowValidator.isValid(dto.getEventDate()))) {
            throw new ConflictException(
                    "Дата должна быть не раньше чем через два часа от текущего момента");
        }

        Event event = EventMapper.toEvent(
                dto, categoryService.getCategory(dto.getCategory()), userService.getUser(userId));

        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> search(EventQueryParams params) {
        return getByParams(params).stream()
                .map(EventMapper::toShortDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> adminSearch(EventQueryParams params) {
        return getByParams(params).stream()
                .map(EventMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public EventFullDto adminUpdate(long eventId, UpdateEventAdminRequest request) {
        Event event = getEvent(eventId);
        EventAdminStateAction action = request.getStateAction();

        if (action != null) {
            if (!EventState.PENDING.equals(event.getState())) {
                throw new ConflictException(
                        "Возможна публикация или отклонение только события в состоянии ожидания");
            }
            if (EventAdminStateAction.PUBLISH_EVENT.equals(action)) {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(EventState.PUBLISHED);
            } else {
                event.setState(EventState.CANCELED);
            }
        }

        if (request.getEventDate() != null) {
            if (!(NotBeforeTwoHoursFromNowValidator.isValid(request.getEventDate()))) {
                throw new ConflictException(
                        "Дата должна быть не раньше чем через два часа от текущего момента");
            }
            if (event.getPublishedOn() != null &&
                    request.getEventDate().isBefore(event.getPublishedOn().minusHours(1))) {
                throw new ConflictException(
                        "Дата начала события должна быть не ранее чем за час от даты публикации");
            }
            event.setEventDate(request.getEventDate());
        }

        commonUpdate(request, event);

        return EventMapper.toDto(event);
    }

    @Override
    public EventFullDto publicGet(long id) {
        return EventMapper.toDto(getEvent(id, true, null));
    }

    @Override
    public EventFullDto getEventOfUser(long userId, long eventId) {
        userService.checkExistence(userId);
        return EventMapper.toDto(getEvent(eventId, false, userId));
    }

    @Override
    @Transactional
    public EventFullDto userUpdate(long userId, long eventId, UpdateEventUserRequest request) {
        userService.checkExistence(userId);
        Event event = getEvent(eventId, false, userId);

        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException("Изменять можно только отмененные или ожидающие подтверждения события");
        }

        if (request.getEventDate() != null) {
            if (!(NotBeforeTwoHoursFromNowValidator.isValid(request.getEventDate()))) {
                throw new ConflictException(
                        "Дата должна быть не раньше чем через два часа от текущего момента");
            }
            event.setEventDate(request.getEventDate());
        }

        if (request.getStateAction() == EventUserStateAction.CANCEL_REVIEW) {
            event.setState(EventState.CANCELED);
        } else if (request.getStateAction() == EventUserStateAction.SEND_TO_REVIEW) {
            event.setState(EventState.PENDING);
        }

        commonUpdate(request, event);

        return EventMapper.toDto(event);
    }

    public Event getEvent(long id) {
        return getEvent(id, false, null);
    }

    @Override
    public List<Event> getEvents(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds);
    }

    @Transactional(readOnly = true)
    private Event getEvent(long id, boolean onlyPublished, Long userId) {
        EventQueryParams.EventQueryParamsBuilder builder = EventQueryParams.getBuilder();
        builder.id(id);
        if (onlyPublished) {
            builder.states(List.of(EventState.PUBLISHED));
        }
        if (userId != null) {
            builder.users(List.of(userId));
        }

        return getByParams((builder.build())).stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Событие id=" + id + " не найдено"));
    }

    private List<Event> getByParams(EventQueryParams params) {
        List<Event> events = eventRepository.getByParams(params);

        if (events.isEmpty()) {
            return events;
        }

        LocalDateTime minCreatedOn;
        if (params.getSort() == EventOrder.VIEWS) {
            minCreatedOn = events.get(0).getCreatedOn();
        } else {
            minCreatedOn = events.stream()
                    .min(Comparator.comparing(Event::getCreatedOn))
                    .orElseThrow()
                    .getCreatedOn();
        }

        Map<Long, Long> hits = statsService.getHits(events.stream()
                .map(Event::getId).collect(Collectors.toUnmodifiableList()), minCreatedOn);

        events.forEach(e -> e.setViews(hits.get(e.getId())));

        if (params.getSort() == EventOrder.VIEWS) {
            return events.stream()
                    .sorted(Comparator.comparing(Event::getViews).reversed())
                    .skip(params.getFrom())
                    .limit(params.getSize())
                    .collect(Collectors.toUnmodifiableList());
        } else {
            return events;
        }
    }

    private void commonUpdate(UpdateEventRequest request, Event event) {
        if (request.getAnnotation() != null) {
            event.setAnnotation(request.getAnnotation());
        }
        if (request.getCategory() != null) {
            event.setCategory(categoryService.getCategory(request.getCategory()));
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        // in an ideal world, we will check lat and lon
        if (request.getLocation() != null) {
            event.setLocation(request.getLocation());
        }
        if (request.getPaid() != null) {
            event.setPaid(request.getPaid());
        }
        // true developers would check current participants number
        if (request.getParticipantLimit() != null) {
            event.setParticipantLimit(request.getParticipantLimit());
        }
        // shouldn't we check existing requests?
        if (request.getRequestModeration() != null) {
            event.setRequestModeration(request.getRequestModeration());
        }
        if (request.getTitle() != null) {
            event.setTitle(request.getTitle());
        }
    }
}