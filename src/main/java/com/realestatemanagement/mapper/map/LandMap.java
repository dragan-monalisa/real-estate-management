package com.realestatemanagement.mapper.map;

import com.realestatemanagement.dto.response.LandView;
import com.realestatemanagement.dto.response.PropertyView;
import com.realestatemanagement.entity.Land;
import com.realestatemanagement.mapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class LandMap extends PropertyMap<Land, PropertyView> {

    @Override
    protected void configure() {
        map().setCategory(source.getCategory());
        map().setArea(source.getUsableArea());
        map().setLand(ModelMapper.map(source, LandView.class));
    }

}
