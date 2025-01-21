package com.realestate.constant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRoleEnumTest {

    @Test
    void getValidRoleTest() {
        assertThat(UserRoleEnum.USER).isEqualTo(UserRoleEnum.valueOf("USER"));
        assertThat(UserRoleEnum.REALTOR).isEqualTo(UserRoleEnum.valueOf("REALTOR"));
        assertThat(UserRoleEnum.ADMIN).isEqualTo(UserRoleEnum.valueOf("ADMIN"));
    }

}
