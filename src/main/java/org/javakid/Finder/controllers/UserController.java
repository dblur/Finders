package org.javakid.Finder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.services.crud.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Users")
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserCrudService userCrudService;

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get user by id")
    public UserDto get(@NotNull @Min(1) @PathVariable Long id) {
        UserDto userDto = userCrudService.getUserDtoById(id);
        log.info("User with id {} has been received", id);
        return userDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@NotNull @Min(1) @PathVariable Long id) {
        userCrudService.deleteUserById(id);
        log.info("User with id {} has been deleted", id);
    }
}
