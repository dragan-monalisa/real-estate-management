package com.realestate.dto.request;

import com.realestate.constant.ApartmentLayoutEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentRequest extends PropertyRequest {

    @Min(1900)
    private int buildYear;

    @Min(1)
    private int roomsNumber;

    @Min(1)
    private int bathroomsNumber;

    @NotNull
    private ApartmentLayoutEnum layout;

}
