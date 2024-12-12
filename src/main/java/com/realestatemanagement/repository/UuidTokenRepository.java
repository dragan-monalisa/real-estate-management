package com.realestatemanagement.repository;

import com.realestatemanagement.entity.User;
import com.realestatemanagement.entity.UuidToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UuidTokenRepository extends JpaRepository<UuidToken, Long> {

    Optional<UuidToken> findByToken(String token);

    default UuidToken getByToken(String token) {
        return findByToken(token).orElseThrow(
                () -> new EntityNotFoundException("No valid token found"));
    }

    List<UuidToken> findAllByUser(User user);

}
