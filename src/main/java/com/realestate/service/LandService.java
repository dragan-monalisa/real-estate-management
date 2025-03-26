package com.realestate.service;

import com.realestate.constant.PropertyCategoryEnum;
import com.realestate.dto.request.LandRequest;
import com.realestate.dto.response.LandView;
import com.realestate.entity.Land;
import com.realestate.entity.User;
import com.realestate.mapper.ModelMapper;
import com.realestate.repository.LandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LandService {

    private final LandRepository repository;

    @PreAuthorize("hasAuthority('USER')")
    public void saveLand(User user, LandRequest request) {
        Land land = ModelMapper.map(request, Land.class);

        land.setCategory(PropertyCategoryEnum.LAND);
        land.setUser(user);

        repository.save(land);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void disableLand(Long id) {
        repository.disable(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<LandView> getMyLands(User user) {
        List<Land> lands = repository.getAllByUser(user);

        return ModelMapper.mapAll(lands, LandView.class);
    }

}
