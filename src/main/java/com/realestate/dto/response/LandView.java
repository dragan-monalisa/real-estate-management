package com.realestate.dto.response;

import com.realestate.constant.LandTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandView {

    private long id;
    private int area;
    private LandTypeEnum landType;
    private AddressView address;

}
