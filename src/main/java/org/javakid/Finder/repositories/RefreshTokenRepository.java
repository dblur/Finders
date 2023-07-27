package org.javakid.Finder.repositories;

import org.javakid.Finder.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsBySubject(String subject);
    Optional<RefreshToken> findBySubject(String subject);
}
