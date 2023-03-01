package ru.practicum.ewm.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewUserRequest {
    @NotNull
    @Email
    String email;

    @NotBlank
    String name;
}