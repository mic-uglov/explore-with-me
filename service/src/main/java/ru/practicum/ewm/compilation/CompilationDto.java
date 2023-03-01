package ru.practicum.ewm.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.EventShortDto;

import java.util.List;

@Getter
@Setter
public class CompilationDto {
    List<EventShortDto> events;
    Long id;
    Boolean pinned;
    String title;
}
