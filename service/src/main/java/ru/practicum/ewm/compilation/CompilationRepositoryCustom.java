package ru.practicum.ewm.compilation;

import java.util.List;

public interface CompilationRepositoryCustom {
    List<Compilation> findAllByPinnedIfNeeded(Boolean pinned, int from, int size);
}
