package com.realestatemanagement.entity;

import com.realestatemanagement.constant.ApartmentPartitioningEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE hs.apartment SET is_active = false WHERE id = ?")
@Table(schema = "hs", name = "apartment")
public class Apartment extends Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(0)
    @Column(name = "build_year")
    private Integer buildYear;

    @NotNull
    @Min(0)
    @Column(name = "rooms_number")
    private Integer roomsNumber;

    @NotNull
    @Min(0)
    @Column(name = "bathrooms_number")
    private Integer bathroomsNumber;

    @NotBlank
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "partitioning", nullable = false, length = 32)
    private ApartmentPartitioningEnum partitioning = ApartmentPartitioningEnum.UNDEFINED;

    // relationships
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            nullable = false,
            name = "address_id"
    )
    private Address address;

}
