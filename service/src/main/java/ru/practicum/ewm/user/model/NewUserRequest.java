package ru.practicum.ewm.user.model;

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
    private String email;

    @NotBlank
    private String name;
}