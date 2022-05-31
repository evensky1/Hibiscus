package com.poit.hibiscus.repository.model;

import com.poit.hibiscus.entity.CurrencyType;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionView {

    private String srcAccountNumber;
    private String destAccountNumber;
    private BigDecimal amount;
    private CurrencyType currencyType;
    private Instant beingAt;
}
