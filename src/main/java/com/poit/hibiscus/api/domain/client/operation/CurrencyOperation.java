package com.poit.hibiscus.api.domain.client.operation;

import com.poit.hibiscus.api.domain.client.CurrencyClient;
import com.poit.hibiscus.api.domain.client.model.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class CurrencyOperation {

    private final CurrencyClient currencyClient;

    public Mono<Currency> activate() {
        return currencyClient.getCurrency();
    }
}
