package ru.practicum.ewm.compilation.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.NotBlankOrNull;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class UpdateCompilationRequest {
    private List<Long> events;

    private Boolean pinned;

    @NotBlankOrNull
    @Size(max = 256)
    private String title;
}