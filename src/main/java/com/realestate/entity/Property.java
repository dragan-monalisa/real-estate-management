package com.realestate.entity;

import com.realestate.constant.PropertyCategoryEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("is_active <> false")
@SQLDelete(sql = "UPDATE hs.property SET is_active = false WHERE id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "hs", name = "property")
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Min(1)
    @Column(name = "area", nullable = false)
    private int area;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    private PropertyCategoryEnum category;

    // relationships
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            nullable = false,
            name = "address_id"
    )
    private Address address;

}
