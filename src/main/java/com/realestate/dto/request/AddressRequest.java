package com.realestate.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {

    @NotBlank
    @Size(max = 56)
    private String county;

    @NotBlank
    @Size(max = 56)
    private String city;

    @NotBlank
    @Size(max = 56)
    private String streetName;

    @Min(1)
    private int streetNumber;

}
