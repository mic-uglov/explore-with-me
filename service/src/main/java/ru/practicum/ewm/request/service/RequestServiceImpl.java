package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.*;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.ewm.request.model.RequestStatus.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getByUser(long userId) {
        return requestRepository.findByRequesterId(userId).stream()
                .map(RequestMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto request(long userId, long eventId) {
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException("Нельзя добавить повторный запрос");
        }

        Event event = eventService.getEvent(eventId);

        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("Инициатор события не может добавить запрос " +
                    "на участие в своем событии");
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getParticipantLimit() > 0 &&
                event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Достугнут лимит запросов на участие");
        }

        User requester = userService.getUser(userId);
        Request request = new Request(requester, event);

        if (!event.getRequestModeration()) {
            request.setStatus(CONFIRMED);
        }

        return RequestMapper.toDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancel(long userId, long requestId) {
        Request request = getRequest(requestId);

        if (request.getRequester().getId() != userId) {
            throw new ConflictException("Нельзя отменить чужой запрос");
        }

        request.setStatus(CANCELED);

        return RequestMapper.toDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getRequests(long userId, long eventId) {
        return requestRepository.findByEventInitiatorIdAndEventId(userId, eventId).stream()
                .map(RequestMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateStatus(
            long userId, long eventId, EventRequestStatusUpdateRequest request) {
        Event event = eventService.getEvent(eventId);

        if (event.getInitiator().getId() != userId) {
            throw new ConflictException("Пользователь id=" + userId +
                    " не является инициатором события id=" + eventId);
        }

        final int limit = event.getParticipantLimit();
        long confirmed = event.getConfirmedRequests();
        final RequestStatus status = request.getStatus();

        if (status == CONFIRMED && limit != 0 && confirmed >= limit) {
            throw new ConflictException("Достигнут лимит по заявкам для события id=" + eventId);
        }

        if (event.getRequestModeration() || status == REJECTED) {
            List<Request> requests = groupByStatus(
                            requestRepository.findByEventIdAndIdIn(eventId, request.getRequestIds()))
                    .get(PENDING);

            if (requests == null || requests.size() != request.getRequestIds().size()) {
                throw new ConflictException("Статус можно изменить только у заявок в состоянии ожидания");
            }

            if (status == REJECTED) {
                requests.forEach(r -> r.setStatus(REJECTED));
            }

            if (status == CONFIRMED) {
                for (Request r : requests) {
                    r.setStatus(CONFIRMED);
                    confirmed++;
                    if (confirmed >= limit) {
                        requestRepository.updateStatusByEventIdAndStatus(eventId, PENDING, REJECTED);
                        break;
                    }
                }
            }
        }

        return RequestMapper.toResult(
                groupByStatus(requestRepository.findByEventIdAndStatusIn(
                        eventId, List.of(CONFIRMED, REJECTED))));
    }

    private Map<RequestStatus, List<Request>> groupByStatus(List<Request> requests) {
        return requests.stream()
                .collect(Collectors.groupingBy(Request::getStatus, Collectors.toUnmodifiableList()));

    }

    /*
    + если для события лимит заявок равен 0 или отключена пре-модерация заявок,
      то подтверждение заявок не требуется
    + нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
    + статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
    - если при подтверждении данной заявки, лимит заявок для события исчерпан,
      то все неподтверждённые заявки необходимо отклонить
     */

    private Request getRequest(long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос id=" + requestId + " не найден"));
    }
}