package com.realestate.service;

import com.realestate.constant.ApartmentLayoutEnum;
import com.realestate.dto.request.AddressRequest;
import com.realestate.dto.request.ApartmentRequest;
import com.realestate.dto.response.ApartmentView;
import com.realestate.entity.Apartment;
import com.realestate.entity.User;
import com.realestate.repository.ApartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceTest {

    @InjectMocks
    private ApartmentService apartmentService;

    @Mock
    private ApartmentRepository apartmentRepository;

    @Captor
    private ArgumentCaptor<Apartment> apartmentArgCaptor;

    @Test
    void saveApartmentTest() {
        // given
        var request = new ApartmentRequest();
        request.setBuildYear(2020);
        request.setRoomsNumber(2);
        request.setBathroomsNumber(2);
        request.setLayout(ApartmentLayoutEnum.SEPARATE_ROOMS);
        request.setArea(70);
        request.setAddress(new AddressRequest());

        var user = new User();
        user.setId(1L);

        // when
        apartmentService.saveApartment(user, request);
        verify(apartmentRepository, times(1)).save(apartmentArgCaptor.capture());

        // then
        assertThat(apartmentArgCaptor.getValue()).satisfies(apartment -> {
            assertThat(apartment.getBuildYear()).isEqualTo(2020);
            assertThat(apartment.getRoomsNumber()).isEqualTo(2);
            assertThat(apartment.getBathroomsNumber()).isEqualTo(2);
            assertThat(apartment.getLayout()).isEqualTo(ApartmentLayoutEnum.SEPARATE_ROOMS);
            assertThat(apartment.getArea()).isEqualTo(70);
        });
    }

    @Test
    void disableApartmentTest() {
        //when
        apartmentService.disableApartment(1L);

        // then
        verify(apartmentRepository, times(1)).disable(1L);
    }

    @Test
    void getMyApartmentsTest() {
        // given
        var user = new User();
        user.setId(1L);

        var apartment = new Apartment();
        apartment.setId(1L);

        // when
        when(apartmentRepository.getAllByUser(user)).thenReturn(List.of(apartment));
        List<ApartmentView> result = apartmentService.getMyApartments(user);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(1L);
    }

}