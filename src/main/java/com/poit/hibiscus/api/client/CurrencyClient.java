package com.poit.hibiscus.api.client;

import com.poit.hibiscus.api.client.model.Currency;
import reactor.core.publisher.Mono;

public interface CurrencyClient {

    Mono<Currency> getCurrency();
}
