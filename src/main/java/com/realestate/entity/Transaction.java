package com.realestate.entity;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.PaymentStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "hs", name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private AdCategoryEnum type;

    @NotNull
    @Builder.Default
    @Column(name = "date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @NotNull
    @DecimalMin("0")
    @Column(name = "price", nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @NotNull
    @DecimalMin("0")
    @Column(name = "commission", nullable = false, precision = 14, scale = 2)
    private BigDecimal commission;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 32)
    private PaymentStatusEnum paymentStatus = PaymentStatusEnum.PENDING;

    // relationships
    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

}
