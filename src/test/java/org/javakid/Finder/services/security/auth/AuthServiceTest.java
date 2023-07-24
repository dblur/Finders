package org.javakid.Finder.services.security.auth;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.javakid.Finder.entity.RefreshToken;
import org.javakid.Finder.exceptions.AuthenticationException;
import org.javakid.Finder.payload.requests.SignInRequest;
import org.javakid.Finder.payload.responses.JwtResponse;
import org.javakid.Finder.services.crud.RefreshTokenCrudService;
import org.javakid.Finder.services.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private JwtProvider jwtProvider;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserDetailsService userDetailsService;
    @Mock private RefreshTokenCrudService refreshTokenCrudService;
    @InjectMocks private AuthService service;

    private String message;
    private String username;
    private String authExcMsg;
    private String accessToken;
    private String refreshToken;
    private JwtResponse jwtResponse;
    private UserDetails userDetails;
    private SignInRequest signInRequest;
    private RefreshToken savedRefreshToken;
    private RefreshToken refreshTokenEntity;
    private RefreshToken newRefreshTokenEntity;
    private RefreshToken newSavedRefreshTokenEntity;

    @BeforeEach
    void setUp() {
        authExcMsg = "Invalid JWT Token";
        message = "Invalid username or password";
        username = "username";
        accessToken = "accessToken";
        refreshToken = "refreshToken";
        userDetails = new User(username, "password", Collections.singleton(new SimpleGrantedAuthority("ROLE")));
        refreshTokenEntity = new RefreshToken(1L, username, refreshToken);
        newRefreshTokenEntity = new RefreshToken(null, username, refreshToken);
        newSavedRefreshTokenEntity = new RefreshToken(1L, username, refreshToken);
        savedRefreshToken = new RefreshToken(1L, username, "newRefreshToken");
        signInRequest = new SignInRequest(username, "password");
        jwtResponse = new JwtResponse(accessToken, refreshToken);
    }

    @Test
    void shouldInjectAllMocks() {
        assertNotNull(jwtProvider);
        assertNotNull(passwordEncoder);
        assertNotNull(userDetailsService);
        assertNotNull(refreshTokenCrudService);
    }

    @Test
    void shouldLoginWithoutExceptionAndRefreshTokenExistsBySubject() {
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(passwordEncoder.matches("password", "password")).thenReturn(true);
        Mockito.when(jwtProvider.generateAccessToken(userDetails)).thenReturn(accessToken);
        Mockito.when(jwtProvider.generateRefreshToken(userDetails)).thenReturn(refreshToken);
        Mockito.when(refreshTokenCrudService.existsRefreshTokenBySubject(username)).thenReturn(true);
        Mockito.when(refreshTokenCrudService.getRefreshTokenBySubject(username)).thenReturn(refreshTokenEntity);
        Mockito.when(refreshTokenCrudService.saveRefreshToken(refreshTokenEntity)).thenReturn(newRefreshTokenEntity);

        JwtResponse actual = service.login(signInRequest);

        assertDoesNotThrow(() -> new AuthenticationException(message));
        assertEquals(jwtResponse.getAccessToken(), actual.getAccessToken());
        assertEquals(jwtResponse.getRefreshToken(), actual.getRefreshToken());
        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.times(1)).matches("password", "password");
        Mockito.verify(jwtProvider, Mockito.times(1)).generateAccessToken(userDetails);
        Mockito.verify(jwtProvider, Mockito.times(1)).generateRefreshToken(userDetails);
        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).existsRefreshTokenBySubject(username);
        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).getRefreshTokenBySubject(username);
        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).saveRefreshToken(refreshTokenEntity);
        Mockito.verifyNoMoreInteractions(userDetailsService);
        Mockito.verifyNoMoreInteractions(passwordEncoder);
        Mockito.verifyNoMoreInteractions(jwtProvider);
        Mockito.verifyNoMoreInteractions(refreshTokenCrudService);
    }

//    @Test
//    void shouldLoginWithoutExceptionAndRefreshTokenNotExistsBySubject() {
//        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//        Mockito.when(passwordEncoder.matches("password", "password")).thenReturn(true);
//        Mockito.when(jwtProvider.generateAccessToken(userDetails)).thenReturn(accessToken);
//        Mockito.when(jwtProvider.generateRefreshToken(userDetails)).thenReturn(refreshToken);
//        Mockito.when(refreshTokenCrudService.existsRefreshTokenBySubject(username)).thenReturn(false);
//        Mockito.when(refreshTokenCrudService.saveRefreshToken(newRefreshTokenEntity)).thenReturn(newRefreshTokenEntity);
//
//        JwtResponse actual = service.login(signInRequest);
//
//        assertEquals(jwtResponse.getAccessToken(), actual.getAccessToken());
//        assertEquals(jwtResponse.getRefreshToken(), actual.getRefreshToken());
//
//        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(username);
//        Mockito.verify(passwordEncoder, Mockito.times(1)).matches("password", "password");
//        Mockito.verify(jwtProvider, Mockito.times(1)).generateAccessToken(userDetails);
//        Mockito.verify(jwtProvider, Mockito.times(1)).generateRefreshToken(userDetails);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).existsRefreshTokenBySubject(username);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).saveRefreshToken(savedRefreshToken);
//        Mockito.verifyNoMoreInteractions(userDetailsService);
//        Mockito.verifyNoMoreInteractions(passwordEncoder);
//        Mockito.verifyNoMoreInteractions(jwtProvider);
//        Mockito.verifyNoMoreInteractions(refreshTokenCrudService);
//    }

    @Test
    void shouldThrowExceptionWhenPasswordMismatch() {
        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        Mockito.when(passwordEncoder.matches("password", "password")).thenReturn(false);

        assertThatThrownBy(() -> service.login(signInRequest))
                .isInstanceOf(AuthenticationException.class)
                .hasMessage(message);
        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(username);
        Mockito.verify(passwordEncoder, Mockito.times(1)).matches("password", "password");
        Mockito.verifyNoMoreInteractions(userDetailsService);
        Mockito.verifyNoMoreInteractions(passwordEncoder);
        Mockito.verifyNoInteractions(jwtProvider);
        Mockito.verifyNoInteractions(refreshTokenCrudService);
    }

//    @Test
//    void getAccessTokenWithoutExceptionAndRefreshTokenIsValid() throws AuthException {
//        Mockito.when(jwtProvider.validateRefreshToken(refreshToken)).thenReturn(true);
//        ArgumentCaptor<Claims> aCClaims = ArgumentCaptor.forClass(Claims.class);
//        Mockito.when(jwtProvider.getRefreshClaims(refreshToken)).thenReturn(aCClaims.capture());
//        Mockito.when(refreshTokenCrudService.getRefreshTokenBySubject(username)).thenReturn(refreshTokenEntity);
//        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//        Mockito.when(jwtProvider.generateAccessToken(userDetails)).thenReturn(accessToken);
//
//        JwtResponse actual = service.getAccessToken(refreshToken);
//
//        assertDoesNotThrow(() -> new AuthException(authExcMsg));
//        assertEquals(jwtResponse.getAccessToken(), actual.getAccessToken());
//
//        Mockito.verify(jwtProvider, Mockito.times(1)).validateRefreshToken(refreshToken);
//        Mockito.verify(jwtProvider, Mockito.times(1)).getRefreshClaims(refreshToken);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).getRefreshTokenBySubject(username);
//        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(username);
//        Mockito.verify(jwtProvider, Mockito.times(1)).generateAccessToken(userDetails);
//        Mockito.verifyNoMoreInteractions(jwtProvider);
//        Mockito.verifyNoMoreInteractions(refreshTokenCrudService);
//        Mockito.verifyNoMoreInteractions(userDetailsService);
//        Mockito.verifyNoInteractions(passwordEncoder);
//    }

    @Test
    void shouldThrowExceptionWhenGetAccessTokenAndRefreshTokenIsInvalid() {
        Mockito.when(jwtProvider.validateRefreshToken(refreshToken)).thenReturn(false);

        assertThatThrownBy(() -> service.getAccessToken(refreshToken))
                .isInstanceOf(AuthException.class)
                .hasMessage(authExcMsg);
        Mockito.verify(jwtProvider, Mockito.times(1)).validateRefreshToken(refreshToken);
        Mockito.verifyNoMoreInteractions(jwtProvider);
        Mockito.verifyNoInteractions(passwordEncoder);
        Mockito.verifyNoInteractions(userDetailsService);
        Mockito.verifyNoInteractions(refreshTokenCrudService);
    }

//    @Test
//    void refresh() throws AuthException {
//        Mockito.when(jwtProvider.validateRefreshToken(refreshToken)).thenReturn(true);
//        Mockito.when(jwtProvider.getRefreshClaims(refreshToken)).thenReturn(Mockito.any(Claims.class));
//        Mockito.when(refreshTokenCrudService.getRefreshTokenBySubject(username)).thenReturn(refreshTokenEntity);
//        Mockito.when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
//        Mockito.when(jwtProvider.generateAccessToken(userDetails)).thenReturn(accessToken);
//        Mockito.when(jwtProvider.generateRefreshToken(userDetails)).thenReturn(refreshToken);
//        Mockito.when(refreshTokenCrudService.existsRefreshTokenBySubject(username)).thenReturn(true);
//        Mockito.when(refreshTokenCrudService.saveRefreshToken(newRefreshTokenEntity)).thenReturn(newSavedRefreshTokenEntity);
//
//        JwtResponse actual = service.refresh(refreshToken);
//
//        assertEquals(jwtResponse.getAccessToken(), actual.getAccessToken());
//        assertEquals(jwtResponse.getRefreshToken(), actual.getRefreshToken());
//        Mockito.verify(jwtProvider, Mockito.times(1)).validateRefreshToken(refreshToken);
//        Mockito.verify(jwtProvider, Mockito.times(1)).getRefreshClaims(refreshToken);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).getRefreshTokenBySubject(username);
//        Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(username);
//        Mockito.verify(jwtProvider, Mockito.times(1)).generateAccessToken(userDetails);
//        Mockito.verify(jwtProvider, Mockito.times(1)).generateRefreshToken(userDetails);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).existsRefreshTokenBySubject(username);
//        Mockito.verify(refreshTokenCrudService, Mockito.times(1)).saveRefreshToken(newRefreshTokenEntity);
//        Mockito.verifyNoMoreInteractions(jwtProvider);
//        Mockito.verifyNoMoreInteractions(refreshTokenCrudService);
//        Mockito.verifyNoMoreInteractions(userDetailsService);
//        Mockito.verifyNoInteractions(passwordEncoder);
//    }
}