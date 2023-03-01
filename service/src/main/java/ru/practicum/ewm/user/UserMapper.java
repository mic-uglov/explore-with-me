package ru.practicum.ewm.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public static User toUser(NewUserRequest newUserRequest) {
        return new User(newUserRequest.getName(), newUserRequest.getEmail());
    }
}