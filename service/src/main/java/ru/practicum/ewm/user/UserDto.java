package ru.practicum.ewm.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    String email;
    Long id;
    String name;
}