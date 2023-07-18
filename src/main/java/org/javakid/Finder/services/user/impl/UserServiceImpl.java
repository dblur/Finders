package org.javakid.Finder.services.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.services.crud.UserCrudService;
import org.javakid.Finder.services.user.UserService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;
    private final UserCrudService userCrudService;

    @Override
    public UserDto getUserById(Long id) {
        User user = userCrudService.getUserById(id);
        log.info("User entity is being mapped on UserDto");
        return mapper.toDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userCrudService.deleteUserById(id);
    }

    @Override
    public UserDto saveUser(UserRequest userRequest) {
        User user = mapper.toEntity(userRequest);
        log.info("UserRequest has been mapped on User");
        user = userCrudService.saveUser(user);
        log.info("User entity is being mapped on UserDto");
        return mapper.toDto(user);
    }
}
