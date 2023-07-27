package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserMapper mapper;
    private final UserRepository repository;

    public User getUserById(@NonNull Long id) {
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

    public User saveUser(@NonNull User user) {
        return repository.save(user);
    }

    public void deleteUserById(@NonNull Long id) {
        repository.deleteById(id);
    }

    public boolean existsUserByEmail(@NonNull String email) {
        return repository.existsByEmail(email);
    }
}
