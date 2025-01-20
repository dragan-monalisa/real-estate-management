package com.realestate.repository;

import com.realestate.entity.User;
import com.realestate.entity.UuidToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UuidTokenRepository extends JpaRepository<UuidToken, Long> {

    List<UuidToken> findAllByUser(User user);

    Optional<UuidToken> findByToken(String token);

    default UuidToken getByToken(String token) {
        return findByToken(token).orElseThrow(
                () -> new EntityNotFoundException("No valid token found"));
    }

}
