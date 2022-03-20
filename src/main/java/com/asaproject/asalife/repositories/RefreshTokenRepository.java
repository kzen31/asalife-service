package com.asaproject.asalife.repositories;

import com.asaproject.asalife.domains.entities.RefreshToken;
import com.asaproject.asalife.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);

    RefreshToken findByToken(String token);

    void deleteByTokenEquals(String token);

    RefreshToken findFirstByUser(User user);
}
