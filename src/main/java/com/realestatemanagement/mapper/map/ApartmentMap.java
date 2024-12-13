package com.realestatemanagement.mapper.map;

import com.realestatemanagement.dto.response.ApartmentView;
import com.realestatemanagement.dto.response.PropertyView;
import com.realestatemanagement.entity.Apartment;
import com.realestatemanagement.mapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class ApartmentMap extends PropertyMap<Apartment, PropertyView> {

    @Override
    protected void configure() {
        map().setCategory(source.getCategory());
        map().setArea(source.getUsableArea());
        map().setApartment(ModelMapper.map(source, ApartmentView.class));
    }

}