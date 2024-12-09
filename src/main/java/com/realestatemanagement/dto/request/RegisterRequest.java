package com.realestatemanagement.dto.request;

import com.realestatemanagement.constant.EmailRegex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    @Size(max = 32)
    private String firstName;

    @NotBlank
    @Size(max = 32)
    private String lastName;

    @NotBlank
    @Size(max = 64)
    private String email;

    @Email(
            regexp = EmailRegex.EXPRESSION,
            message = "email format is not valid"
    )
    @NotBlank
    @Size(max = 64)
    private String password;
}
