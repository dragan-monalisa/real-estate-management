package com.realestate.service;

import com.realestate.constant.PropertyCategoryEnum;
import com.realestate.dto.request.HouseRequest;
import com.realestate.dto.response.HouseView;
import com.realestate.entity.House;
import com.realestate.entity.User;
import com.realestate.mapper.ModelMapper;
import com.realestate.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository repository;

    @PreAuthorize("hasAuthority('USER')")
    public void saveHouse(User user, HouseRequest request) {
        House house = ModelMapper.map(request, House.class);

        house.setCategory(PropertyCategoryEnum.HOUSE);
        house.setUser(user);

        repository.save(house);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void disableHouse(Long id) {
        repository.disable(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<HouseView> getMyHouses(User user) {
        List<House> houses = repository.getAllByUser(user);

        return ModelMapper.mapAll(houses, HouseView.class);
    }

}
