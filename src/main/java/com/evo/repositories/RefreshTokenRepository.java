package com.evo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evo.models.RefreshToken;

import jakarta.transaction.Transactional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
    Optional<RefreshToken> findByToken(String token);

    @Query("SELECT rt FROM RefreshToken rt JOIN rt.user u WHERE u.email = :email")
    Optional<RefreshToken> findByUserEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken rt SET rt.token = :token WHERE rt.id = :id")
    void updateToken(Long id, String token);
}
