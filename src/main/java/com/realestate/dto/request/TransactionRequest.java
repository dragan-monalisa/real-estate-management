package com.realestate.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

    @NotBlank
    @Size(max = 64)
    private String customerEmail;

    @NotBlank
    @Size(max = 1024)
    private String contractTerms;

}
