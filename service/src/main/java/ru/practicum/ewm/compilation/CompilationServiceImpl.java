package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventService eventService;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto dto) {
        List<Long> eventIds = dto.getEvents();
        List<Event> events = null;

        if (eventIds != null && !eventIds.isEmpty()) {
            events = eventService.getEvents(eventIds);
        }

        return CompilationMapper.toDto(compilationRepository.save(CompilationMapper.toCompilation(dto, events)));
    }
}