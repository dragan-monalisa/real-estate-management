package com.realestatemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "hs", name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 56)
    @Column(name = "county", nullable = false, length = 56)
    private String county;

    @NotBlank
    @Size(max = 56)
    @Column(name = "city", nullable = false, length = 56)
    private String city;

    @NotBlank
    @Size(max = 56)
    @Column(name = "street_name", nullable = false, length = 56)
    private String streetName;

    @NotNull
    @Min(0)
    @Column(name = "street_number", nullable = false)
    private Integer streetNumber;

}
