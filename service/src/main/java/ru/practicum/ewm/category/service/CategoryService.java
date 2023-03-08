package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.model.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto dto);

    void delete(long catId);

    CategoryDto update(long catId, CategoryDto categoryDto);

    List<CategoryDto> getAll(int from, int size);

    CategoryDto get(long catId);

    Category getCategory(long catId);
}