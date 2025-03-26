package com.realestate.service;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import com.realestate.constant.UserRoleEnum;
import com.realestate.dto.request.AdRequest;
import com.realestate.dto.request.AdSearchRequest;
import com.realestate.dto.response.AdView;
import com.realestate.entity.Ad;
import com.realestate.entity.Property;
import com.realestate.entity.User;
import com.realestate.exception.BusinessException;
import com.realestate.mapper.ModelMapper;
import com.realestate.repository.AdRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import com.realestate.specification.AdSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdService {

    private final AdRepository adRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('USER')")
    public void postAd(User user, AdRequest request, Long propertyId) {
        Property property = propertyRepository.getPropertyById(propertyId);
        Ad ad = ModelMapper.map(request, Ad.class);

        ad.setOwner(user);
        ad.setRealtor(getRealtorWithFewestActiveAds());
        ad.setProperty(property);

        adRepository.save(ad);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void disableAd(long id) {
        adRepository.disable(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    public List<AdView> getMyAds(User user, AdCategoryEnum category) {
        List<Ad> ads = adRepository.findALlByOwnerAndCategory(user, category);

        return ModelMapper.mapAll(ads, AdView.class);
    }

    @PreAuthorize("hasAuthority('REALTOR')")
    public void changeAdStatus(Long id, AdStatusEnum status) {
        Ad ad = adRepository.getAdById(id);
        ad.setStatus(status);

        if (AdStatusEnum.ACCEPTED.equals(status)) {
            ad.setIsActive(true);
        }

        adRepository.save(ad);
    }

    @PreAuthorize("hasAnyAuthority('REALTOR', 'ADMIN')")
    public List<AdView> getRealtorAds(User realtor, AdCategoryEnum category) {
        List<Ad> ads = adRepository.findAllByRealtorAndCategory(realtor, category);

        return ModelMapper.mapAll(ads, AdView.class);
    }

    public List<AdView> searchAds(AdSearchRequest request) {
        Specification<Ad> specification = AdSpecification.getAdsByFilters(request);
        List<Ad> ads = adRepository.findAll(specification);

        return ModelMapper.mapAll(ads, AdView.class);
    }

    private User getRealtorWithFewestActiveAds() {
        List<User> realtors = userRepository.getUserByRole(UserRoleEnum.REALTOR);

        return realtors.stream()
                .min(Comparator.comparingInt(adRepository::countRealtorAds))
                .orElseThrow(() -> new BusinessException("No realtors available"));
    }

}
