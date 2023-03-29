package com.poit.hibiscus.api.client.dto;

import com.poit.hibiscus.api.client.model.CurrencyType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponse {
    private Map<CurrencyType, BigDecimal> quotes = new EnumMap<>(CurrencyType.class);

    private Instant updatedAt = Instant.now();
}
