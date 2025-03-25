package com.realestate.repository;

import com.realestate.constant.UserRoleEnum;
import com.realestate.entity.User;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private User validUser;
    private User disabledUser;

    @BeforeEach
    void setup() {
        validUser = User.builder()
                .email("test@email.com")
                .password("test")
                .firstName("FirstName")
                .lastName("LastName")
                .role(UserRoleEnum.USER)
                .isEnabled(true)
                .build();

        var invalidUser = User.builder()
                .email("test1@email.com")
                .password("test1")
                .firstName("FirstName")
                .lastName("LastName")
                .isEnabled(false)
                .isLocked(true)
                .build();

        disabledUser = User.builder()
                .email("test2@email.com")
                .password("test")
                .firstName("FirstName")
                .lastName("LastName")
                .isEnabled(false)
                .confirmedAt(null)
                .build();

        userRepository.saveAll(List.of(validUser, invalidUser, disabledUser));
    }

    @Test
    void existByEmailTest() {
        // when
        boolean exists = userRepository.existsByEmail(validUser.getEmail());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void findByEmailAndIsEnabledTrueAndIsLockedFalseTest() {
        // when
        Optional<User> optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(validUser.getEmail());

        // then
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getEmail()).isEqualTo(validUser.getEmail());
    }

    @Test
    void enableUserTest() {
        Optional<User> optionalUser = userRepository.findUnconfirmedUser(disabledUser.getEmail());
        assertThat(optionalUser).isPresent();

        var user = optionalUser.get();

        assertThat(user.getIsEnabled()).isFalse();
        assertThat(user.getConfirmedAt()).isNull();

        LocalDateTime updatedAt = user.getUpdatedAt();

        userRepository.enableUser(user.getId());
        entityManager.flush();
        entityManager.clear();

        optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(user.getEmail());

        assertThat(optionalUser).isPresent();

        user = optionalUser.get();

        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getConfirmedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isAfter(updatedAt);
    }

    @Test
    void changePasswordTest() {
        Optional<User> optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(validUser.getEmail());
        assertThat(optionalUser).isPresent();

        var user = optionalUser.get();

        assertThat(user.getPassword()).isEqualTo("test");

        LocalDateTime updatedAt = user.getUpdatedAt();
        String newPassword = "newPassword";

        userRepository.changePassword(validUser.getEmail(), newPassword);
        entityManager.flush();
        entityManager.clear();

        optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(validUser.getEmail());
        assertThat(optionalUser).isPresent();

        user = optionalUser.get();

        assertThat(user.getPassword()).isEqualTo(newPassword);
        assertThat(user.getUpdatedAt()).isAfter(updatedAt);
    }

    @Test
    void getByEmailTest() {
        // when
        Throwable thrown = catchThrowable(() -> userRepository.getByEmail("random"));

        // then
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User with email random not found");
    }

    @Test
    void getUnconfirmedUserTest() {
        // when
        Throwable thrown = catchThrowable(() -> userRepository.getByEmail(disabledUser.getEmail()));

        // then
        assertThat(thrown).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User with email " + disabledUser.getEmail() + " not found");
    }

}
