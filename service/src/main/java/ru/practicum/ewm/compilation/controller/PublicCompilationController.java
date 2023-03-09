package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> search(
            @RequestParam(required = false)
            Boolean pinned,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero
            int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE)
            @Positive
            int size) {
        return ResponseEntity.ok(compilationService.search(pinned, from, size));
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> get(@PathVariable long compId) {
        return ResponseEntity.ok(compilationService.get(compId));
    }
}