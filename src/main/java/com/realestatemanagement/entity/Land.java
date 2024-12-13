package com.realestatemanagement.entity;

import com.realestatemanagement.constant.LandTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE hs.land SET is_active = false WHERE id = ?")
@Table(schema = "hs", name = "land")
public class Land extends Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "land_type", nullable = false, length = 32)
    private LandTypeEnum landType = LandTypeEnum.UNDEFINED;

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
