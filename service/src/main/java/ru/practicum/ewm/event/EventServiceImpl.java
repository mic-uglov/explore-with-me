package ru.practicum.ewm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryService;
import ru.practicum.ewm.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public EventFullDto create(long userId, NewEventDto newEventDto) {
        Event event = EventMapper.toEvent(
                newEventDto, categoryService.getCategory(newEventDto.getCategory()), userService.getUser(userId));
        return EventMapper.toDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> search(EventQueryParams params) {
        return eventRepository.getByParams(params).stream()
                .map(EventMapper::toShortDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<EventFullDto> adminSearch(EventQueryParams params) {
        return eventRepository.getByParams(params).stream()
                .map(EventMapper::toDto).collect(Collectors.toUnmodifiableList());
    }
}