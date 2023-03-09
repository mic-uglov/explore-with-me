package ru.practicum.ewm.compilation.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class NewCompilationDto {
    private List<Long> events;

    private boolean pinned;

    @NotBlank
    @Size(max = 256)
    private String title;
}