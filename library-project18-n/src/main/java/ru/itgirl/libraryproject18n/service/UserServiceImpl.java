package ru.itgirl.libraryproject18n.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject18n.dto.UserDto;
import ru.itgirl.libraryproject18n.model.User;
import ru.itgirl.libraryproject18n.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    @Override
//    public UserDto getUserByRole(String role) {
//        User user = userRepository.findAUserByRole("role").orElseThrow();
//            return convertToDto(user);
//    }
    @Override
    public UserDto getUserByRole(String role) {
        log.info("Try to find a user by role", role);
        Optional<User> user = userRepository.findAUserByRole("role");
        if(user.isPresent()){
            UserDto userDto = convertToDto(user.get());
            log.info("User: {}", userDto.toString());
            return userDto;
        }else {
            log.error("User with role {} not found", role);
            throw new NoSuchElementException("No values present");
        }
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }
}