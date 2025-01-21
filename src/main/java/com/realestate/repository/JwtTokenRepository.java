package com.realestate.repository;

import com.realestate.entity.JwtToken;
import com.realestate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE JwtToken " +
            "SET isExpired = true, isRevoked = true " +
            "WHERE user = :user")
    void deleteAllByUser(User user);
    
}
