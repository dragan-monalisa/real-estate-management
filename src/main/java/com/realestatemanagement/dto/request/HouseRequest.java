package com.realestatemanagement.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseRequest {

    @NotNull
    @Min(0)
    private Integer buildYear;

    @NotNull
    @Min(0)
    private int floorsNumber;

    @NotNull
    @Min(0)
    private int roomsNumber;

    @NotNull
    @Min(0)
    private int bathroomsNumber;

}
