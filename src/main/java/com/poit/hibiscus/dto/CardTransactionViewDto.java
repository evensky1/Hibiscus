package com.poit.hibiscus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poit.hibiscus.entity.CurrencyType;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardTransactionViewDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String srcCardNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String destCardNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal amount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CurrencyType currencyType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant beingAt;
}
