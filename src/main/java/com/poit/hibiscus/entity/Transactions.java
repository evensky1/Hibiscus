package com.poit.hibiscus.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Transactions {

    @Entity
    @Builder
    @Getter
    @DynamicInsert
    @Table(name = "account_transaction")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountTransaction {

        @Id
        private UUID id;

        @ManyToOne(cascade = {
                CascadeType.REFRESH,
                CascadeType.PERSIST
        }, fetch = FetchType.LAZY, optional = false
        ) @JoinColumn(name = "from_account_id", nullable = false)
        private CardAccount fromAccount;

        @ManyToOne(cascade = {
                CascadeType.REFRESH,
                CascadeType.PERSIST
        }, fetch = FetchType.LAZY, optional = false
        ) @JoinColumn(name = "to_account_id", nullable = false)
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

    @Entity
    @Builder
    @Getter
    @DynamicInsert
    @Table(name = "card_transaction")
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardTransaction {

        @Id
        private UUID id;

        @ManyToOne(cascade = {
                CascadeType.REFRESH,
                CascadeType.PERSIST
        }, fetch = FetchType.LAZY, optional = false
        ) @JoinColumn(name = "from_card_id", nullable = false)
        private Card fromCard;

        @ManyToOne(cascade = {
                CascadeType.REFRESH,
                CascadeType.PERSIST
        }, fetch = FetchType.LAZY, optional = false
        ) @JoinColumn(name = "to_card_id", nullable = false)
        private Card toCard;

        @Column(name = "amount", nullable = false)
        private BigDecimal amount;

        @Enumerated(EnumType.STRING)
        @Column(name = "currency", nullable = false)
        private CurrencyType currencyType;

        @Column(name = "being_at",
                columnDefinition = "timestamp default now()")
        private Instant beingAt;
    }
}
