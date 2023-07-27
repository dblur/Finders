package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.entity.Role;
import org.javakid.Finder.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleCrudService {

    private final RoleRepository repository;

    public Role getRoleByName(String name) {
        return repository.findByName(name).orElseThrow(() -> {
           String msg = "Role with name " + name + " not found";
           log.error(msg);
           return new EntityNotFoundException(msg);
        });
    }
}
