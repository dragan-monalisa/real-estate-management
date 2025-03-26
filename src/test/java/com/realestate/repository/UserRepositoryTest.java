package com.realestate.repository;

import com.realestate.constant.UserRoleEnum;
import com.realestate.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private User validUser;
    private User disabledUser;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

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

        // when
        userRepository.enableUser(disabledUser.getId());

        Optional<User> optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(disabledUser.getEmail());

        // then
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().isEnabled()).isTrue();
    }

    @Test
    void changePasswordTest() {

        // when
        userRepository.changePassword(validUser.getEmail(), "newPassword");
        entityManager.clear();

        Optional<User> optionalUser = userRepository.findByEmailAndIsEnabledTrueAndIsLockedFalse(validUser.getEmail());

        // then
        assertThat(optionalUser).isPresent();
        assertThat(optionalUser.get().getPassword()).isEqualTo("newPassword");
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
