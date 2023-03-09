package ru.practicum.ewm.category.repository;

import ru.practicum.ewm.category.model.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> getAll(int from, int size);
}