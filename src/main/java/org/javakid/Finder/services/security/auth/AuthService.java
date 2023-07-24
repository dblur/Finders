package org.javakid.Finder.services.security.auth;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.entity.RefreshToken;
import org.javakid.Finder.exceptions.AuthenticationException;
import org.javakid.Finder.payload.requests.SignInRequest;
import org.javakid.Finder.payload.responses.JwtResponse;
import org.javakid.Finder.services.crud.RefreshTokenCrudService;
import org.javakid.Finder.services.security.jwt.JwtProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenCrudService refreshTokenCrudService;

    @Transactional
    public JwtResponse login(@NonNull SignInRequest signInRequest) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getEmail());
        if (passwordEncoder.matches(signInRequest.getPassword(), userDetails.getPassword())) {
            log.info("login -> User password matched");
            final String accessToken = jwtProvider.generateAccessToken(userDetails);
            final String refreshToken = jwtProvider.generateRefreshToken(userDetails);
            RefreshToken refreshTokenEntity;
            if (refreshTokenCrudService.existsRefreshTokenBySubject(userDetails.getUsername())) {
                log.info("login -> Refresh token exists by subject {}", userDetails.getUsername());
                refreshTokenEntity = refreshTokenCrudService.getRefreshTokenBySubject(userDetails.getUsername());
                refreshTokenEntity.setToken(refreshToken);
                refreshTokenEntity = refreshTokenCrudService.saveRefreshToken(refreshTokenEntity);
                return new JwtResponse(accessToken, refreshTokenEntity.getToken());
            }
            log.info("login -> Refresh token doesn't exist by subject {}", userDetails.getUsername());
            refreshTokenEntity = new RefreshToken(userDetails.getUsername(), refreshToken);
            refreshTokenEntity = refreshTokenCrudService.saveRefreshToken(refreshTokenEntity);
            return new JwtResponse(accessToken, refreshTokenEntity.getToken());
        }
        log.error("Invalid username or password");
        throw new AuthenticationException("Invalid username or password");
    }

    @Transactional(readOnly = true)
    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            log.info("getAccessToken -> Refresh token is valid");
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            RefreshToken refreshTokenEntity = refreshTokenCrudService.getRefreshTokenBySubject(email);
            if (refreshTokenEntity.getToken().equals(refreshToken)) {
                log.info("getAccessToken -> Refresh token from client request equals to the one from database");
                final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                final String accessToken = jwtProvider.generateAccessToken(userDetails);
                return new JwtResponse(accessToken, null);
            }
        }
        log.error("getAccessToken -> Refresh token is not valid");
        throw  new AuthException("Invalid JWT Token");
    }

    @Transactional
    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            log.info("getAccessToken -> Refresh token is valid");
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            RefreshToken refreshTokenBySubject = refreshTokenCrudService.getRefreshTokenBySubject(email);
            if (refreshTokenBySubject.getToken().equals(refreshToken)) {
                log.info("refresh -> Refresh token from client request equals to the one from database");
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                final String accessToken = jwtProvider.generateAccessToken(userDetails);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDetails);
                RefreshToken refreshTokenEntity;
                if (refreshTokenCrudService.existsRefreshTokenBySubject(email)) {
                    log.info("refresh -> Refresh token exists by subject {}", email);
                    refreshTokenEntity = refreshTokenCrudService.getRefreshTokenBySubject(email);
                    refreshTokenEntity.setToken(newRefreshToken);
                    refreshTokenCrudService.saveRefreshToken(refreshTokenEntity);
                    return new JwtResponse(accessToken, newRefreshToken);
                }
                log.info("refresh -> Refresh token does not exist by subject {}", email);
                refreshTokenEntity = new RefreshToken(userDetails.getUsername(), newRefreshToken);
                refreshTokenCrudService.saveRefreshToken(refreshTokenEntity);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        log.error("refresh -> Refresh token is not valid");
        throw new AuthException("Invalid JWT Token");
    }
}
