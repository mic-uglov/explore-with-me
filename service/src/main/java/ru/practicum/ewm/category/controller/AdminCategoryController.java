package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.category.model.NewCategoryDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(newCategoryDto));
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> delete(@PathVariable long catId) {
        categoryService.delete(catId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> update(
            @PathVariable long catId,
            @RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.update(catId, categoryDto));
    }
}