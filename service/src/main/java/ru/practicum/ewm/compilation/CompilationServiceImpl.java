package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.Event;
import ru.practicum.ewm.event.EventService;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public void delete(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationRequest request) {
        Compilation compilation = getCompilation(compId);

        if (request.getTitle() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }

        List<Long> eventIds = request.getEvents();
        if (eventIds != null && !eventIds.isEmpty()) {
            compilation.setEvents(eventService.getEvents(eventIds));
        }

        return CompilationMapper.toDto(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> search(Boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinnedIfNeeded(pinned, from, size).stream()
                .map(CompilationMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto get(long compId) {
        return CompilationMapper.toDto(getCompilation(compId));
    }

    private Compilation getCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Подборка id=" + " не найдена"));
    }
}