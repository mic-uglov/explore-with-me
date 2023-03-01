package ru.practicum.ewm.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.NotBlankOrNull;

import java.util.List;

@Getter
@Setter
public class UpdateCompilationRequest {
    List<Long> events;

    Boolean pinned;

    @NotBlankOrNull
    String title;
}