package com.poit.hibiscus.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "card_account")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CardAccount {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "money")
    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type")
    private CurrencyType currencyType;

    @Column(name = "iban")
    private String iban;

    @Column(name = "number")
    private String number;

    @OneToMany
    @JoinColumn(name = "account_id")
    @ToString.Exclude
    private Set<Card> cards = new HashSet<>();

    @Column(name = "person_id")
    private Long userId;
}
