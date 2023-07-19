package org.javakid.Finder.services.crud.impl;

import jakarta.persistence.EntityNotFoundException;
import org.javakid.Finder.dto.CompanyDto;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.Company;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.enums.Sex;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.CompanyRequest;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
    private UserRequest userRequest;
    String entityNotFoundMsg;
    String illegalEntityArgumentMsg;
    String illegalIdArgumentMsg;

    @BeforeEach
    void setUp() {
        id = 1L;
        user = new User(
                id, "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new Company(
                        1L, "name", "email",
                "phone", "city", "country", "scope")
        );
        userDto = new UserDto(
                id, "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new CompanyDto(
                1L, "name", "email",
                "phone", "city", "country", "scope")
        );
        userRequest = new UserRequest(
                "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new CompanyRequest(
                "name", "email",
                "phone", "city", "country", "scope")
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
        Mockito.verify(repository).findById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenGetUserById() {
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userCrudService.getUserById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(entityNotFoundMsg);
        Mockito.verify(repository).findById(id);
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
        Mockito.verify(repository).save(user);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenSaveUser() {
        assertThatThrownBy(() -> userCrudService.saveUser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(illegalEntityArgumentMsg);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldSaveUserRequest() {
        Mockito.when(mapper.toEntity(userRequest)).thenReturn(user);
        Mockito.when(repository.save(user)).thenReturn(user);
        Mockito.when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = userCrudService.saveUserRequest(userRequest);

        assertEquals(userDto, actual);
        Mockito.verify(mapper, Mockito.times(1)).toEntity(userRequest);
        Mockito.verify(mapper, Mockito.times(1)).toDto(user);
        Mockito.verifyNoMoreInteractions(mapper);
    }

    @Test
    void shouldDeleteUserByIdWithoutException() {
        userCrudService.deleteUserById(id);

        assertDoesNotThrow(() -> new IllegalArgumentException(illegalIdArgumentMsg));
        Mockito.verify(repository).deleteById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenDeleteById() {
        assertThatThrownBy(() -> userCrudService.deleteUserById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(illegalIdArgumentMsg);
        Mockito.verifyNoMoreInteractions(repository);
    }
}