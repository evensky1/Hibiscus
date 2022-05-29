package com.poit.hibiscus.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "holdings")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Holding {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issuance_date")
    private LocalDateTime issuanceDate;

    @OneToOne
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "percentage")
    private float percentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type")
    private CurrencyType currencyType;
}
