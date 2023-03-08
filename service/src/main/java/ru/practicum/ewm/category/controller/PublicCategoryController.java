package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero
            int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE)
            @Positive
            int size) {
        return ResponseEntity.ok(categoryService.getAll(from, size));
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> get(@PathVariable long catId) {
        return ResponseEntity.ok(categoryService.get(catId));
    }
}