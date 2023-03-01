package ru.practicum.ewm.compilation;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class NewCompilationDto {
    List<Long> events;

    Boolean pinned;

    @NotBlank
    String title;
}