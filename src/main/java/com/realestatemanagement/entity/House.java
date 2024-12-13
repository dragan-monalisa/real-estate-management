package com.realestatemanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE hs.house SET is_active = false WHERE id = ?")
@Table(schema = "hs", name = "house")
public class House extends Property {

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
    @Column(name = "floors_number")
    private Integer floorsNumber;

    @NotNull
    @Min(0)
    @Column(name = "rooms_number")
    private Integer roomsNumber;

    @NotNull
    @Min(0)
    @Column(name = "bathrooms_number")
    private Integer bathroomsNumber;

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
