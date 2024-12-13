package com.realestatemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseView {

    private Long id;
    private Integer buildYear;
    private Integer floorsNumber;
    private Integer roomsNumber;
    private Integer bathroomsNumber;
    private AddressView address;

}
