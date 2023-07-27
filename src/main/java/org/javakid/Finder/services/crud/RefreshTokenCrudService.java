package org.javakid.Finder.services.crud;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.entity.RefreshToken;
import org.javakid.Finder.repositories.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenCrudService {

    private final RefreshTokenRepository repository;

    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return repository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenBySubject(String subject) {
        return repository.findBySubject(subject).orElseThrow(() -> {
            String msg = "Refresh token with subject " + subject + " not found";
            log.error(msg);
            return new EntityNotFoundException(msg);
        });
    }

    public boolean existsRefreshTokenBySubject(String subject) {
        return repository.existsBySubject(subject);
    }
}
