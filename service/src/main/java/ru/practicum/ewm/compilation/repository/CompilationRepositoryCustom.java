package ru.practicum.ewm.compilation.repository;

import ru.practicum.ewm.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepositoryCustom {
    List<Compilation> findAllByPinnedIfNeeded(Boolean pinned, int from, int size);
}
