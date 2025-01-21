package com.realestate.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PropertyRequest {

    @Min(1)
    private int area;

    AddressRequest address;

}
