package com.realestatemanagement.mapper.map;

import com.realestatemanagement.dto.response.AddressView;
import com.realestatemanagement.entity.Address;
import org.modelmapper.PropertyMap;

public class AddressMap extends PropertyMap<Address, AddressView> {

    @Override
    protected void configure() {
        map().setCounty(source.getCounty());
        map().setCity(source.getCity());
        map().setStreetName(source.getStreetName());
        map().setStreetNumber(source.getStreetNumber());
    }
    
}
