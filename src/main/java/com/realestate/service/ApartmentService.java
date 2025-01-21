package com.realestate.service;

import com.realestate.constant.PropertyCategoryEnum;
import com.realestate.dto.request.ApartmentRequest;
import com.realestate.dto.response.ApartmentView;
import com.realestate.entity.Apartment;
import com.realestate.entity.User;
import com.realestate.mapper.ModelMapper;
import com.realestate.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentRepository repository;

    @PreAuthorize("hasAnyAuthority('USER')")
    public void saveApartment(User user, ApartmentRequest request) {
        Apartment apartment = ModelMapper.map(request, Apartment.class);

        apartment.setCategory(PropertyCategoryEnum.APARTMENT);
        apartment.setUser(user);

        repository.save(apartment);
    }


    @PreAuthorize("hasAnyAuthority('USER')")
    public void disableApartment(Long id) {
        repository.disable(id);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    public List<ApartmentView> getMyApartments(User user) {
        List<Apartment> apartments = repository.getAllByUser(user);

        return ModelMapper.mapAll(apartments, ApartmentView.class);
    }

}
