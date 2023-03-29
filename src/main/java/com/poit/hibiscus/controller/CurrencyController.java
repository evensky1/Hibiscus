package com.poit.hibiscus.controller;

import com.poit.hibiscus.api.client.model.Currency;
import com.poit.hibiscus.api.client.operation.CurrencyOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/currency")
public class CurrencyController {
    private final CurrencyOperation currencyOperation;

    @GetMapping
    public Mono<Currency> getCurrency() {
        return currencyOperation.activate();
    }
}
