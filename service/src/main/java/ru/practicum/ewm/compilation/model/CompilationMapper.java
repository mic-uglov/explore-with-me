package ru.practicum.ewm.compilation.model;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public static Compilation toCompilation(NewCompilationDto dto, List<Event> events) {
        Compilation compilation = new Compilation();

        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.getPinned() != null && dto.getPinned());
        compilation.setEvents(events.stream().collect(Collectors.toUnmodifiableSet()));

        return compilation;
    }

    public static CompilationDto toDto(Compilation compilation) {
        CompilationDto dto = new CompilationDto();

        dto.setId(compilation.getId());
        dto.setPinned(compilation.getPinned());
        dto.setTitle(compilation.getTitle());
        if (compilation.getEvents() != null) {
            dto.setEvents(compilation.getEvents().stream()
                    .map(EventMapper::toShortDto).collect(Collectors.toUnmodifiableList()));
        }

        return dto;
    }
}