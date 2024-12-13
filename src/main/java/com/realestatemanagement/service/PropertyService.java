package com.realestatemanagement.service;

import com.realestatemanagement.constant.PropertyCategoryEnum;
import com.realestatemanagement.dto.request.PropertyRequest;
import com.realestatemanagement.dto.response.PropertyView;
import com.realestatemanagement.entity.*;
import com.realestatemanagement.mapper.ModelMapper;
import com.realestatemanagement.repository.ApartmentRepository;
import com.realestatemanagement.repository.HouseRepository;
import com.realestatemanagement.repository.LandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final LandRepository landRepository;
    private final ApartmentRepository apartmentRepository;
    private final HouseRepository houseRepository;

    @PreAuthorize("hasAuthority('USER')")
    public void saveProperty(PropertyRequest request, User user) {
        PropertyCategoryEnum category = request.getCategory();

        var address = Address.builder()
                .city(request.getAddress().getCity())
                .county(request.getAddress().getCounty())
                .streetName(request.getAddress().getStreetName())
                .streetNumber(request.getAddress().getStreetNumber())
                .build();

        switch (category) {
            case LAND:
                var land = Land.builder()
                        .landType(request.getLand().getLandType())
                        .user(user)
                        .address(address)
                        .build();
                land.setCategory(category);
                land.setUsableArea(request.getUsableArea());

                landRepository.save(land);
                break;

            case APARTMENT:
                var apartment = Apartment.builder()
                        .buildYear(request.getApartment().getBuildYear())
                        .roomsNumber(request.getApartment().getRoomsNumber())
                        .bathroomsNumber(request.getApartment().getBathroomsNumber())
                        .partitioning(request.getApartment().getPartitioning())
                        .user(user)
                        .address(address)
                        .build();
                apartment.setCategory(category);
                apartment.setUsableArea(request.getUsableArea());

                apartmentRepository.save(apartment);
                break;

            case HOUSE:
                var house = House.builder()
                        .buildYear(request.getHouse().getBuildYear())
                        .floorsNumber(request.getHouse().getFloorsNumber())
                        .roomsNumber(request.getHouse().getRoomsNumber())
                        .bathroomsNumber(request.getHouse().getBathroomsNumber())
                        .user(user)
                        .address(address)
                        .build();
                house.setCategory(category);
                house.setUsableArea(request.getUsableArea());

                houseRepository.save(house);
                break;

        }
    }

    @PreAuthorize("hasAuthority('USER')")
    public void deleteProperty(Long id, String category) {

        switch (PropertyCategoryEnum.getCategory(category)) {
            case LAND:
                landRepository.disable(id);
                break;

            case APARTMENT:
                apartmentRepository.disable(id);
                break;

            case HOUSE:
                houseRepository.disable(id);
                break;
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<PropertyView> getMyProperties(User user, String category) {

        return switch (PropertyCategoryEnum.getCategory(category)) {
            case LAND -> ModelMapper.mapAll(landRepository.findAllByUser(user), PropertyView.class);
            case APARTMENT -> ModelMapper.mapAll(apartmentRepository.findAllByUser(user), PropertyView.class);
            case HOUSE -> ModelMapper.mapAll(houseRepository.findAllByUser(user), PropertyView.class);
            default -> Collections.emptyList();
        };
    }

}
