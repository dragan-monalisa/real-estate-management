package com.realestatemanagement.dto.response;

import com.realestatemanagement.constant.ApartmentPartitioningEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentView {

    private Long id;
    private Integer buildYear;
    private Integer roomsNumber;
    private Integer bathroomsNumber;
    private ApartmentPartitioningEnum partitioning;
    private AddressView address;

}
