package ru.practicum.ewm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DEF_PAGE_SIZE;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {
    @GetMapping
    public ResponseEntity<List<UserDto>> get(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = DEF_PAGE_SIZE) int size) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(
            @RequestBody @Valid NewUserRequest newUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable long userId) {
        return ResponseEntity.noContent().build();
    }
}