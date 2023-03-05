package ru.practicum.ewm.compilation;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto dto);

    void delete(long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest request);

    List<CompilationDto> search(Boolean pinned, int from, int size);

    CompilationDto get(long compId);
}