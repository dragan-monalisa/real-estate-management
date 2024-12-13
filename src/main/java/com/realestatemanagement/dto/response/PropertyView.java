package com.realestatemanagement.dto.response;

import com.realestatemanagement.constant.PropertyCategoryEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertyView {

    private PropertyCategoryEnum category;
    private Integer area;
    private LandView land;
    private HouseView house;
    private ApartmentView apartment;

}
