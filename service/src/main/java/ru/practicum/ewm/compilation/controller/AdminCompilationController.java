package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;
import ru.practicum.ewm.compilation.model.NewCompilationDto;
import ru.practicum.ewm.compilation.model.UpdateCompilationRequest;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> create(@RequestBody @Valid NewCompilationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.create(dto));
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> delete(@PathVariable long compId) {
        compilationService.delete(compId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> update(
            @PathVariable long compId,
            @RequestBody @Valid UpdateCompilationRequest request) {
        return ResponseEntity.ok(compilationService.update(compId, request));
    }
}