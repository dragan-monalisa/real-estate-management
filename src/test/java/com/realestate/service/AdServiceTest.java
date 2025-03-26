package com.realestate.service;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import com.realestate.constant.UserRoleEnum;
import com.realestate.dto.request.AdRequest;
import com.realestate.dto.response.AdView;
import com.realestate.entity.Ad;
import com.realestate.entity.House;
import com.realestate.entity.User;
import com.realestate.repository.AdRepository;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @InjectMocks
    private AdService adService;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<Ad> adArgCaptor;

    @Test
    void postAdTest_PropertyNotFound() {

        // when
        when(propertyRepository.getPropertyById(anyLong())).thenThrow(new EntityNotFoundException("Property with id: 1 not found"));

        // then
        assertThatThrownBy(() -> adService.postAd(new User(), new AdRequest(), 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Property with id: 1 not found");
    }

    @Test
    void postAdTest_OK() {

        // given
        var request = new AdRequest();
        request.setTitle("title");
        request.setDescription("description");
        request.setCategory(AdCategoryEnum.SALE);
        request.setPrice(BigDecimal.valueOf(10));

        var realtor = new User();
        realtor.setId(100L);
        realtor.setRole(UserRoleEnum.REALTOR);

        var property = new House();
        property.setId(1L);

        when(userRepository.getUserByRole(UserRoleEnum.REALTOR)).thenReturn(List.of(realtor));
        when(propertyRepository.getPropertyById(1L)).thenReturn(property);

        // when
        adService.postAd(new User(), request, 1L);

        // then
        verify(propertyRepository).getPropertyById(1L);
        verify(adRepository).save(adArgCaptor.capture());

        assertThat(adArgCaptor.getValue()).satisfies(ad -> {
            assertThat(ad.getProperty().getId()).isEqualTo(1L);
            assertThat(ad.getTitle()).isEqualTo("title");
            assertThat(ad.getDescription()).isEqualTo("description");
            assertThat(ad.getCategory()).isEqualTo(AdCategoryEnum.SALE);
            assertThat(ad.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(10));
        });
    }

    @Test
    void disableAdTest() {

        // when
        adService.disableAd(1L);

        // then
        verify(adRepository).disable(1L);
    }

    @Test
    void getMyAdsTest() {

        // given
        var ad = new Ad();
        ad.setId(1L);
        ad.setCategory(AdCategoryEnum.SALE);

        var user = new User();
        user.setId(1L);

        when(adRepository.findALlByOwnerAndCategory(user, AdCategoryEnum.SALE)).thenReturn(List.of(ad));

        // when
        List<AdView> result = adService.getMyAds(user, AdCategoryEnum.SALE);

        // then
        verify(adRepository).findALlByOwnerAndCategory(user, AdCategoryEnum.SALE);
        assertThat(result).hasSize(1);
    }

    @Test
    void changeStatusTest() {

        // given
        var ad = new Ad();
        ad.setId(1L);

        when(adRepository.getAdById(ad.getId())).thenReturn(ad);

        // when
        adService.changeAdStatus(ad.getId(), AdStatusEnum.ACCEPTED);

        // then
        verify(adRepository).save(ad);

        assertThat(ad.getStatus()).isEqualTo(AdStatusEnum.ACCEPTED);
        assertThat(ad.getIsActive()).isTrue();
    }

    @Test
    void getRealtorAdsTest() {

        // given
        var ad = new Ad();
        ad.setId(1L);
        ad.setCategory(AdCategoryEnum.SALE);

        var realtor = new User();
        realtor.setId(1L);

        when(adRepository.findAllByRealtorAndCategory(realtor, AdCategoryEnum.SALE)).thenReturn(List.of(ad));

        // when
        List<AdView> result = adService.getRealtorAds(realtor, AdCategoryEnum.SALE);

        // then
        verify(adRepository).findAllByRealtorAndCategory(realtor, AdCategoryEnum.SALE);
        assertThat(result).hasSize(1);
    }

}
