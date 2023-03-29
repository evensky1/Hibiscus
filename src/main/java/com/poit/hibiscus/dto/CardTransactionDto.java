package com.poit.hibiscus.dto;

import java.math.BigDecimal;

public record CardTransactionDto(Long fromCardId,
                                 String toCardNumber,
                                 BigDecimal amount) {

}
