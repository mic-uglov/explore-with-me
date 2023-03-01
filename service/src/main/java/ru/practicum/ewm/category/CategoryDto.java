package ru.practicum.ewm.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class CategoryDto {
    private Long id;

    @NotBlank
    private String name;
}