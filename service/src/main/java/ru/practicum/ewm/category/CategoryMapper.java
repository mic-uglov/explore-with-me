package ru.practicum.ewm.category;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static Category toCategory(NewCategoryDto dto) {
        return new Category(dto.getName());
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}