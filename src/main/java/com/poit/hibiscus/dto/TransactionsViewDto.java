package com.poit.hibiscus.dto;

import com.poit.hibiscus.entity.CurrencyType;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionsViewDto {

    public record AccountTransactionViewDto(
        String srcAccountNumber,
        String destAccountNumber,
        BigDecimal amount,
        CurrencyType currencyType,
        Instant beingAt
    ) {

    }

    public record CardTransactionViewDto(
        String srcCardNumber,
        String destCardNumber,
        BigDecimal amount,
        CurrencyType currencyType,
        Instant beingAt
    ) {

    }
}
