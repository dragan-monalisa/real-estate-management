package com.realestatemanagement.dto.response;

import com.realestatemanagement.constant.LandTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LandView {

    private Long id;
    private LandTypeEnum landType;
    private AddressView address;

}
