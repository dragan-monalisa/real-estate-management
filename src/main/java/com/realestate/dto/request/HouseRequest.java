package com.realestate.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseRequest extends PropertyRequest {

    @Min(1900)
    private int buildYear;

    @Min(0)
    private int floorsNumber;

    @Min(1)
    private int roomsNumber;

    @Min(0)
    private int bathroomsNumber;

}
