package org.javakid.Finder.services.crud.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.repositories.UserRepository;
import org.javakid.Finder.services.crud.UserCrudService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository repository;

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    String msg = "User with id " + id + " not found";
                    log.error(msg);
                    return new EntityNotFoundException(msg);
                });
    }

    @Override
    public User saveUser(User user) {
        if (Objects.isNull(user)) {
            String msg = "User entity must not be null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return repository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        if (Objects.isNull(id)) {
            String msg = "Id must not be null";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        repository.deleteById(id);
    }
}
