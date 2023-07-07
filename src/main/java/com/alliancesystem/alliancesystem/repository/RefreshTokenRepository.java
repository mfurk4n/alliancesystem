package com.alliancesystem.alliancesystem.repository;

import com.alliancesystem.alliancesystem.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserId(String userId);

}
