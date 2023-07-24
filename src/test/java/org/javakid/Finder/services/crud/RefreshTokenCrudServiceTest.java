package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import org.javakid.Finder.entity.RefreshToken;
import org.javakid.Finder.repositories.RefreshTokenRepository;
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
class RefreshTokenCrudServiceTest {

    @Mock private RefreshTokenRepository repository;
    @InjectMocks private RefreshTokenCrudService service;

    private String message;
    private String subject;
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        message = "Refresh token with subject " + subject + " not found";
        subject = "subject";
        refreshToken = new RefreshToken(1L, subject, "token");
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(repository);
    }

    @Test
    void saveRefreshToken() {
        Mockito.when(repository.save(refreshToken)).thenReturn(refreshToken);

        RefreshToken actual = service.saveRefreshToken(refreshToken);

        assertEquals(refreshToken.getId(), actual.getId());
        Mockito.verify(repository, Mockito.times(1)).save(refreshToken);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldGetRefreshTokenBySubjectWithoutException() {
        Mockito.when(repository.findBySubject(subject)).thenReturn(Optional.of(refreshToken));

        RefreshToken actual = service.getRefreshTokenBySubject(subject);

        assertDoesNotThrow(() -> new EntityNotFoundException(message));
        assertEquals(refreshToken.getSubject(), actual.getSubject());
        Mockito.verify(repository, Mockito.times(1)).findBySubject(subject);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldThrowExceptionWhenGetRefreshTokenBySubject() {
        Mockito.when(repository.findBySubject(subject)).thenThrow(new EntityNotFoundException(message));

        assertThatThrownBy(() -> service.getRefreshTokenBySubject(subject))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(message);
        Mockito.verify(repository, Mockito.times(1)).findBySubject(subject);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnTrueIfExistsRefreshTokenBySubject() {
        Mockito.when(repository.existsBySubject(subject)).thenReturn(true);

        boolean exists = service.existsRefreshTokenBySubject(subject);

        assertTrue(exists);
        Mockito.verify(repository, Mockito.times(1)).existsBySubject(subject);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFalseIfNotExistsRefreshTokenBySubject() {
        Mockito.when(repository.existsBySubject(subject)).thenReturn(false);

        boolean exists = service.existsRefreshTokenBySubject(subject);

        assertFalse(exists);
        Mockito.verify(repository, Mockito.times(1)).existsBySubject(subject);
        Mockito.verifyNoMoreInteractions(repository);
    }
}