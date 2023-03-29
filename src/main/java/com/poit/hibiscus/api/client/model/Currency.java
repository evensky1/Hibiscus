package com.poit.hibiscus.api.client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {
    @Expose
    private CurrencyType source;

    @Expose
    private Map<CurrencyType, BigDecimal> quotes = new EnumMap<>(CurrencyType.class);

    @Expose(
        serialize = false,
        deserialize = false
    )
    private LocalDateTime currencyUpdatedAt = LocalDateTime.now();
}
