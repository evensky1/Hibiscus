package com.poit.hibiscus.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@DynamicInsert
@Table(name = "account_transaction")
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransaction {

    @Id
    private UUID id;
    @ManyToOne(cascade = {
        CascadeType.REFRESH,
        CascadeType.PERSIST
    }, fetch = FetchType.LAZY, optional = false
    )
    @JoinColumn(name = "from_account_id", nullable = false)
    private CardAccount fromAccount;
    @ManyToOne(cascade = {
        CascadeType.REFRESH,
        CascadeType.PERSIST
    }, fetch = FetchType.LAZY, optional = false
    )
    @JoinColumn(name = "to_account_id", nullable = false)
    private CardAccount toAccount;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currencyType;
    @Column(name = "being_at",
        columnDefinition = "timestamp default now()")
    private Instant beingAt;
}
