package ru.practicum.ewm.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class NewCategoryDto {
    @NotBlank
    String name;
}