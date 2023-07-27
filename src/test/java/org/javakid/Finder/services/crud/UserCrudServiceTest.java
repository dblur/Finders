package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import org.javakid.Finder.dto.ProfileDto;
import org.javakid.Finder.dto.RoleDto;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.Profile;
import org.javakid.Finder.entity.Role;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.repositories.UserRepository;
import org.javakid.Finder.services.crud.UserCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserCrudServiceTest {

    @Mock private UserMapper mapper;
    @Mock private UserRepository repository;
    @InjectMocks private UserCrudService userCrudService;

    private Long id;
    private User user;
    private UserDto userDto;
    String entityNotFoundMsg;
    String illegalEntityArgumentMsg;
    String illegalIdArgumentMsg;

    @BeforeEach
    void setUp() {
        id = 1L;
        user = new User(
                id, "email", "password",
                Set.of(new Role()), new Profile()
        );
        userDto = new UserDto(
                id, "email", "password",
                Set.of(new RoleDto()), new ProfileDto()
        );
        entityNotFoundMsg = "User with id " + id + " not found";
        illegalEntityArgumentMsg = "User entity must not be null";
        illegalIdArgumentMsg = "Id must not be null";
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(mapper);
        assertNotNull(repository);
    }

    @Test
    void shouldGetUserByIdWithoutException() {
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(user));
        User actual = userCrudService.getUserById(id);

        assertDoesNotThrow(() -> new EntityNotFoundException(entityNotFoundMsg));
        assertEquals(id, actual.getId());
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenGetUserById() {
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userCrudService.getUserById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(entityNotFoundMsg);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetUserDtoById() {
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = userCrudService.getUserDtoById(id);

        assertEquals(userDto, actual);
        Mockito.verify(mapper, Mockito.times(1)).toDto(user);
        Mockito.verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldSaveUserWithoutException() {
        Mockito.when(repository.save(user)).thenReturn(user);
        User actual = userCrudService.saveUser(user);

        assertDoesNotThrow(() -> new IllegalArgumentException(illegalEntityArgumentMsg));
        assertEquals(user, actual);
        Mockito.verify(repository, Mockito.times(1)).save(user);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldDeleteUserById() {
        userCrudService.deleteUserById(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
        Mockito.verifyNoMoreInteractions(repository);
        Mockito.verifyNoInteractions(mapper);
    }
}