package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero
            int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE)
            @Positive
            int size) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> get(@PathVariable long catId) {
        return ResponseEntity.ok(null);
    }
}