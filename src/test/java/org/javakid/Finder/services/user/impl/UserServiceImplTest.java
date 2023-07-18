package org.javakid.Finder.services.user.impl;

import org.javakid.Finder.dto.CompanyDto;
import org.javakid.Finder.dto.UserDto;
import org.javakid.Finder.entity.Company;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.enums.Role;
import org.javakid.Finder.enums.Sex;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.CompanyRequest;
import org.javakid.Finder.payload.UserRequest;
import org.javakid.Finder.services.crud.UserCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserMapper mapper;
    @Mock private UserCrudService userCrudService;
    @InjectMocks private UserServiceImpl userService;

    private Long id;
    private User user;
    private UserDto userDto;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        id = 1L;
        userDto = new UserDto(
                1L, "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new CompanyDto(
                1L, "name", "email",
                "phone", "city", "country", "scope")
        );
        user = new User(
                id, "name", "surname", 20,
                Sex.FEMALE, Role.ROLE_RECRUITER, "email",
                "phone", "city", "country",
                "experience", new Company(
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
    }

    @Test
    void shouldAllMocksBeNotNull() {
        assertNotNull(mapper);
        assertNotNull(userCrudService);
    }

    @Test
    void getUserById() {
        Mockito.when(userCrudService.getUserById(id)).thenReturn(user);
        Mockito.when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = userService.getUserById(id);

        assertEquals(userDto.getId(), actual.getId());
        Mockito.verify(userCrudService, Mockito.times(1)).getUserById(id);
        Mockito.verify(mapper, Mockito.times(1)).toDto(user);
        Mockito.verifyNoMoreInteractions(userCrudService);
        Mockito.verifyNoMoreInteractions(mapper);
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(id);

        Mockito.verify(userCrudService, Mockito.times(1)).deleteUserById(id);
        Mockito.verifyNoMoreInteractions(userCrudService);
        Mockito.verifyNoInteractions(mapper);
    }

    @Test
    void saveUser() {
        Mockito.when(mapper.toEntity(userRequest)).thenReturn(user);
        Mockito.when(userCrudService.saveUser(user)).thenReturn(user);
        Mockito.when(mapper.toDto(user)).thenReturn(userDto);

        UserDto actual = userService.saveUser(userRequest);

        assertEquals(userDto, actual);
        Mockito.verify(mapper, Mockito.times(1)).toEntity(userRequest);
        Mockito.verify(userCrudService, Mockito.times(1)).saveUser(user);
        Mockito.verify(mapper, Mockito.times(1)).toDto(user);
        Mockito.verifyNoMoreInteractions(mapper);
        Mockito.verifyNoMoreInteractions(userCrudService);
    }
}