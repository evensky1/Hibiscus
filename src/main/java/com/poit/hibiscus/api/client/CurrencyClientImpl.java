package com.poit.hibiscus.api.client;

import com.poit.hibiscus.api.client.model.Currency;
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
                        uriBuilder.path(ClientProperties.ENDPOINT)
                                .queryParam("access_key", ClientProperties.ACCESS_KEY)
                                .queryParam("currencies", ClientProperties.CURRENCIES)
                                .build()
                )
                .retrieve()
                .bodyToMono(Currency.class);
    }
}
