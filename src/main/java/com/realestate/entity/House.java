package com.realestate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(schema = "hs", name = "house")
public class House extends Property {

    @Min(1900)
    @Column(name = "build_year", nullable = false)
    private int buildYear;

    @Min(0)
    @Column(name = "floors_number", nullable = false)
    private int floorsNumber;

    @Min(1)
    @Column(name = "rooms_number", nullable = false)
    private int roomsNumber;

    @Min(0)
    @Column(name = "bathrooms_number", nullable = false)
    private int bathroomsNumber;

}
