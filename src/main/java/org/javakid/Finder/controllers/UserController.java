package org.javakid.Finder.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto get(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        log.info("User with id {} has been received", id);
        return userDto;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public UserDto post(@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = userService.saveUser(userRequest);
        log.info("User with firstname {} has been saved", userRequest.getFirstName());
        return userDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteUserById(id);
        log.info("User with id {} has been deleted", id);
    }
}
