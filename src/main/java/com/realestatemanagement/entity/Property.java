package com.realestatemanagement.entity;

import com.realestatemanagement.constant.PropertyCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_active <> false")
@SQLDelete(sql = "UPDATE hs.property SET is_active = false WHERE id = ?")
@Table(schema = "hs", name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 32)
    private PropertyCategoryEnum category;

    @NotNull
    @Min(1)
    @Column(name = "area", nullable = false)
    private Integer area;

    @Column(name = "build_year")
    private Integer buildYear;

    @Column(name = "floors_number")
    private Integer floorsNumber;

    @Column(name = "rooms_number")
    private Integer roomsNumber;

    @Column(name = "partitioning")
    private String partitioning;

    // relationships
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @OneToOne
    @JoinColumn(
            nullable = false,
            name = "address_id"
    )
    private Address address;
}
