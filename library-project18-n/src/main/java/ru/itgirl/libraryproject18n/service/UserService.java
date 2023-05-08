package ru.itgirl.libraryproject18n.service;

import ru.itgirl.libraryproject18n.dto.UserDto;

public interface UserService {
    UserDto getUserByRole(String role);
}
