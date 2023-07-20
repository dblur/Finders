package org.javakid.Finder.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.services.crud.impl.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserCrudService userCrudService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto get(@PathVariable Long id) {
        UserDto userDto = userCrudService.getUserDtoById(id);
        log.info("User with id {} has been received", id);
        return userDto;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public UserDto post(@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = userCrudService.saveUserRequest(userRequest);
        log.info("User with firstname {} has been saved", userRequest.getFirstName());
        return userDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userCrudService.deleteUserById(id);
        log.info("User with id {} has been deleted", id);
    }
}
