package com.realestatemanagement.repository;

import com.realestatemanagement.entity.JwtToken;
import com.realestatemanagement.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE JwtToken " +
            "SET isExpired = true, isRevoked = true " +
            "WHERE user = :user")
    void deleteAllByUser(User user);
}
