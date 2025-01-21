package com.realestate.entity;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE hs.ad SET is_active = false WHERE id = ?")
@Table(schema = "hs", name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotBlank
    @Size(max = 64)
    @Column(name = "title", nullable = false, length = 64)
    private String title;

    @NotBlank
    @Size(max = 256)
    @Column(name = "description", nullable = false, length = 256)
    private String description;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private AdStatusEnum status = AdStatusEnum.PENDING;

    @NotNull
    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 32)
    private AdCategoryEnum category;

    @NotNull
    @DecimalMin("0")
    @Column(name = "price", nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    // relationships
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "realtor_id", nullable = false)
    private User realtor;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}
