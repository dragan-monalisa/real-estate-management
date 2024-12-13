package com.realestatemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressView {

    private String county;
    private String city;
    private String streetName;
    private Integer streetNumber;

}
