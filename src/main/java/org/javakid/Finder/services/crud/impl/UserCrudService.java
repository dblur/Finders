package org.javakid.Finder.services.crud.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    String msg = "User with id " + id + " not found";
                    log.error(msg);
                    return new EntityNotFoundException(msg);
                });
    }

    public UserDto getUserDtoById(Long id) {
        User user = getUserById(id);
        return mapper.toDto(user);
    }

    public UserDto saveUserRequest(UserRequest userRequest) {
        User user = mapper.toEntity(userRequest);
        log.info("UserRequest has been mapped on User");
        user = saveUser(user);
        log.info("User entity is being mapped on UserDto");
        return mapper.toDto(user);
    }

    public User saveUser(User user) {
        if (Objects.isNull(user)) {
            String msg = "User entity must not be null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return repository.save(user);
    }

    public void deleteUserById(Long id) {
        if (Objects.isNull(id)) {
            String msg = "Id must not be null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        repository.deleteById(id);
    }
}
