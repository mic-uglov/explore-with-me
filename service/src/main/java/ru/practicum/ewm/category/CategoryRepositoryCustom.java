package ru.practicum.ewm.category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> getAll(int from, int size);
}