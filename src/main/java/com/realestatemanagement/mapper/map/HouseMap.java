package com.realestatemanagement.mapper.map;

import com.realestatemanagement.dto.response.HouseView;
import com.realestatemanagement.dto.response.PropertyView;
import com.realestatemanagement.entity.House;
import com.realestatemanagement.mapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class HouseMap extends PropertyMap<House, PropertyView> {

    @Override
    protected void configure() {
        map().setCategory(source.getCategory());
        map().setArea(source.getUsableArea());
        map().setHouse(ModelMapper.map(source, HouseView.class));
    }

}
