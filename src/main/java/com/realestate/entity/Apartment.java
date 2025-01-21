package com.realestate.entity;

import com.realestate.constant.ApartmentLayoutEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "hs", name = "apartment")
public class Apartment extends Property {

    @Min(1900)
    @Column(name = "build_year", nullable = false)
    private int buildYear;

    @Min(1)
    @Column(name = "rooms_number", nullable = false)
    private int roomsNumber;

    @Min(1)
    @Column(name = "bathrooms_number", nullable = false)
    private int bathroomsNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "partitioning", nullable = false, length = 32)
    private ApartmentLayoutEnum layout;

}
