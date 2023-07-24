package org.javakid.Finder.services.security.auth;

import org.javakid.Finder.entity.Role;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.enums.ERole;
import org.javakid.Finder.mappers.UserMapper;
import org.javakid.Finder.payload.requests.SignUpRequest;
import org.javakid.Finder.services.crud.UserCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock private UserMapper mapper;
    @Mock private UserCrudService userCrudService;
    @InjectMocks private RegistrationService service;

    private User user;
    private User savedUser;
    private SignUpRequest request;

    @BeforeEach
    void setUp() {
        request = new SignUpRequest("email", "password", ERole.CANDIDATE);
        user = new User("email", "password", Set.of(new Role(1L, "CANDIDATE")));
        savedUser = new User(1L, "email", "password", Set.of(new Role(1L, "CANDIDATE")), null);
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(mapper);
        assertNotNull(userCrudService);
    }

    @Test
    void register() {
        Mockito.when(mapper.toEntity(request)).thenReturn(user);
        Mockito.when(userCrudService.saveUser(user)).thenReturn(savedUser);

        service.register(request);

        Mockito.verify(mapper, Mockito.times(1)).toEntity(request);
        Mockito.verify(userCrudService, Mockito.times(1)).saveUser(user);
        Mockito.verifyNoMoreInteractions(mapper);
        Mockito.verifyNoMoreInteractions(userCrudService);
    }
}