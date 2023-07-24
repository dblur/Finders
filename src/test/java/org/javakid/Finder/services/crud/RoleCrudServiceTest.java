package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import org.javakid.Finder.entity.Role;
import org.javakid.Finder.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoleCrudServiceTest {

    @Mock private RoleRepository repository;
    @InjectMocks private RoleCrudService service;

    private Role role;
    private String name;
    private String message;

    @BeforeEach
    void setUp() {
        name = "name";
        role = new Role(1L, name);
        message = "Role with name " + name + " not found";
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(repository);
    }

    @Test
    void shouldGetRoleByNameWithoutException() {
        Mockito.when(repository.findByName(name)).thenReturn(Optional.of(role));

        Role actual = service.getRoleByName(name);

        assertDoesNotThrow(() -> new EntityNotFoundException(message));
        assertEquals(role.getName(), actual.getName());
        Mockito.verify(repository, Mockito.times(1)).findByName(name);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenGetRoleByName() {
        Mockito.when(repository.findByName(name)).thenThrow(new EntityNotFoundException(message));

        assertThatThrownBy(() -> service.getRoleByName(name))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);
        Mockito.verify(repository, Mockito.times(1)).findByName(name);
        Mockito.verifyNoMoreInteractions(repository);
    }
}