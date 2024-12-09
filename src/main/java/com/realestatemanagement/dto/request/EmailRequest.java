package com.realestatemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class EmailRequest {

    @NotBlank
    @Size(max = 64)
    private String email;
}