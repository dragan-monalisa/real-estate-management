package com.realestate.repository;

import com.realestate.constant.UserRoleEnum;
import com.realestate.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    List<User> getUserByRole(UserRoleEnum realtor);

    Optional<User> findByEmailAndIsEnabledTrueAndIsLockedFalse(String email);

    Optional<User> findByEmailAndIsLockedFalse(String email);

    @Modifying
    @Query("UPDATE User " +
            "SET isEnabled = true, confirmedAt = CURRENT_TIMESTAMP, updatedAt = CURRENT_TIMESTAMP " +
            "WHERE id = :id")
    void enableUser(Long id);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.email = :email AND u.confirmedAt IS NULL")
    Optional<User> findUnconfirmedUser(String email);

    @Modifying
    @Query("UPDATE User " +
            "SET password = :password, updatedAt = CURRENT_TIMESTAMP " +
            "WHERE email = :email")
    void changePassword(String email, String password);

    default User getByEmail(String email) {
        return findByEmailAndIsEnabledTrueAndIsLockedFalse(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + email + " not found")
        );
    }

    default Optional<User> getOptionalByEmail(String email) {
        return findByEmailAndIsLockedFalse(email);
    }

    default Optional<User> getValidOptionalByEmail(String email) {
        return findByEmailAndIsEnabledTrueAndIsLockedFalse(email);
    }

}
