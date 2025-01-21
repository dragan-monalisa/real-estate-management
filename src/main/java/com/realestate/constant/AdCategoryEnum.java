package com.realestate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum AdCategoryEnum {

    RENT("Rental property contract", new BigDecimal("0.5")),
    SALE("Sale property contract", new BigDecimal("0.03"));

    private final String contractTitle;
    private final BigDecimal commissionRate;

}
