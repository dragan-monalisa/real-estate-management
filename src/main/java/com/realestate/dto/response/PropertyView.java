package com.realestate.dto.response;

import com.realestate.constant.PropertyCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyView {

    private long id;
    private int area;
    private PropertyCategoryEnum category;
    private AddressView address;

}
