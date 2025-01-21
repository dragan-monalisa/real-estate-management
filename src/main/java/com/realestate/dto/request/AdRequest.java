package com.realestate.dto.request;

import com.realestate.constant.AdCategoryEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdRequest {

    @NotBlank
    @Size(max = 64)
    private String title;

    @NotBlank
    @Size(max = 256)
    private String description;

    @NotNull
    private AdCategoryEnum category;

    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

}
