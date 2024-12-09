package com.realestatemanagement.repository;

import com.realestatemanagement.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndIsLockedFalse(String email);

    Optional<User> findByEmailAndIsEnabledTrueAndIsLockedFalse(String email);

    default Optional<User> getOptionalByEmail(String email){
        return findByEmailAndIsLockedFalse(email);
    }

    @Transactional
    @Modifying
    @Query("UPDATE User " +
            "SET isEnabled = true, confirmedAt = CURRENT_TIMESTAMP, updatedAt = CURRENT_TIMESTAMP " +
            "WHERE id = :id")
    void enableUser(Long id);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.email = :email AND u.confirmedAt IS NULL")
    Optional<User> findUnconfirmedUser(String email);

    default User getByEmail(String email) {
        return findByEmailAndIsEnabledTrueAndIsLockedFalse(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

    default Optional<User> getValidOptionalByEmail(String email) {
        return findByEmailAndIsEnabledTrueAndIsLockedFalse(email);
    }

    @Transactional
    @Modifying
    @Query("UPDATE User " +
            "SET password = :password, updatedAt = CURRENT_TIMESTAMP " +
            "WHERE email = : email")
    void changePassword(String email, String password);
}
