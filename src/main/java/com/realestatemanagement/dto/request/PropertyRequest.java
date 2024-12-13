package com.realestatemanagement.dto.request;

import com.realestatemanagement.constant.PropertyCategoryEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyRequest {

    @NotNull
    private PropertyCategoryEnum category;

    @NotNull
    @Min(0)
    private Integer usableArea;

    private HouseRequest house;
    private ApartmentRequest apartment;
    private LandRequest land;
    private AddressRequest address;

}
