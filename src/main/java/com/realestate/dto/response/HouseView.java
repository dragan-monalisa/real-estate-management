package com.realestate.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseView {

    private long id;
    private int buildYear;
    private int floorsNumber;
    private int roomsNumber;
    private int bathroomsNumber;
    private int area;
    private AddressView address;

}
