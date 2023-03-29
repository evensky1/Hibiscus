package com.poit.hibiscus.dto;

import java.math.BigDecimal;

public record AccountTransactionDto(Long fromAccountId,
                                    String toAccountNumber,
                                    BigDecimal amount) {

}
