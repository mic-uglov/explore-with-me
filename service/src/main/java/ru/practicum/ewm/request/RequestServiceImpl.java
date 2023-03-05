package ru.practicum.ewm.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.event.EventState;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

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
        if (!EventState.PUBLISHED.equals(event.getState())) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Достугнут лимит запросов на участие");
        }

        User requester = userService.getUser(userId);
        Request request = new Request(requester, event);

        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
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

        request.setStatus(RequestStatus.CANCELED);

        return RequestMapper.toDto(request);
    }

    private Request getRequest(long requestId) {
        return requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос id=" + requestId + " не найден"));
    }
}