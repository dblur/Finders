package org.javakid.Finder.services.security.auth;

import jakarta.persistence.EntityNotFoundException;
import org.javakid.Finder.entity.Profile;
import org.javakid.Finder.entity.Role;
import org.javakid.Finder.entity.User;
import org.javakid.Finder.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock private UserRepository repository;
    @InjectMocks private UserDetailsServiceImpl service;

    private User user;
    private String message;
    private String username;

    @BeforeEach
    void setUp() {
        message = "Invalid username or password";
        username = "username";
        user = new User(1L, "email", "password", Set.of(new Role()), new Profile());
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(repository);
    }

    @Test
    void shouldLoadUserByUsernameWithoutException() {
        Mockito.when(repository.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetails actual = service.loadUserByUsername(username);

        assertDoesNotThrow(() -> new EntityNotFoundException(message));
        assertEquals(user.getUsername(), actual.getUsername());
        Mockito.verify(repository, Mockito.times(1)).findByEmail(username);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenLoadUserByUsername() {
        Mockito.when(repository.findByEmail(username)).thenThrow(new UsernameNotFoundException(message));

        assertThatThrownBy(() -> service.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(message);
        Mockito.verify(repository, Mockito.times(1)).findByEmail(username);
        Mockito.verifyNoMoreInteractions(repository);
    }
}