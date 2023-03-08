package ru.practicum.ewm.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class CategoryDto {
    private Long id;

    @NotBlank
    @Size(max = 64)
    private String name;
}