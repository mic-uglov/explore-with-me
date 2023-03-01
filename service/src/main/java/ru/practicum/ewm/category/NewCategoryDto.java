package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class NewCategoryDto {
    @NotBlank
    private String name;
}