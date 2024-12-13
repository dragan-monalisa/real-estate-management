package com.realestatemanagement.dto.request;

import com.realestatemanagement.constant.ApartmentPartitioningEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentRequest {

    @NotNull
    @Min(0)
    private Integer buildYear;

    @NotNull
    @Min(0)
    private int roomsNumber;

    @NotNull
    @Min(0)
    private int bathroomsNumber;

    @NotBlank
    private ApartmentPartitioningEnum partitioning;

}
