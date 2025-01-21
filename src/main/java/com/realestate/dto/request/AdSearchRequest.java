package com.realestate.dto.request;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.PropertyCategoryEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdSearchRequest {

    @DecimalMin("0")
    private BigDecimal minPrice;

    @DecimalMin("0")
    private BigDecimal maxPrice;

    @Min(0)
    private int minArea;

    @Min(0)
    private int maxArea;

    private AddressRequest address;
    private AdCategoryEnum adCategory;
    private PropertyCategoryEnum propertyCategory;

}
