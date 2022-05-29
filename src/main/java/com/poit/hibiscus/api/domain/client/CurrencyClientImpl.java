package com.poit.hibiscus.api.domain.client;

import com.poit.hibiscus.api.domain.client.model.Currency;
import com.poit.hibiscus.config.ClientProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CurrencyClientImpl implements CurrencyClient {
    private final WebClient webClient;

    public CurrencyClientImpl(@Qualifier("web-client-custom-bean") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Currency> getCurrency() {
        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/live")
                                .queryParam("access_key", "3cb2148e7f1e0c00f2e85dc26d283925")
                                .build()
                )
                .retrieve()
                .bodyToMono(Currency.class);
    }
}
