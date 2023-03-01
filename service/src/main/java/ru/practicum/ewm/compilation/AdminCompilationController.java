package ru.practicum.ewm.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    @PostMapping
    public ResponseEntity<CompilationDto> create(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> delete(@PathVariable long compId) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> update(
            @PathVariable long compId,
            @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        return ResponseEntity.ok(null);
    }
}