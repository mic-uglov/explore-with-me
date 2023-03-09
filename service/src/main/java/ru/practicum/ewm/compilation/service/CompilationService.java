package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.model.CompilationDto;

import java.util.List;

public interface CompilationService {
    CompilationDto create(NewCompilationDto dto);

    void delete(long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest request);

    List<CompilationDto> search(Boolean pinned, int from, int size);

    CompilationDto get(long compId);
}