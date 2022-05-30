package com.poit.hibiscus.repository.model;

import com.poit.hibiscus.entity.CurrencyType;
import java.math.BigDecimal;
import java.time.Instant;

public record AccountTransactionView(
    String srcAccountNumber,
    String destAccountNumber,
    BigDecimal amount,
    CurrencyType currencyType,
    Instant beingAt
) {

}
