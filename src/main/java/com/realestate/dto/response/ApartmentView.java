package com.realestate.dto.response;

import com.realestate.constant.ApartmentLayoutEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentView {

    private long id;
    private int buildYear;
    private int roomsNumber;
    private int bathroomsNumber;
    private int area;
    private ApartmentLayoutEnum layout;
    private AddressView address;

}
