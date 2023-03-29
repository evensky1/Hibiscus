package com.poit.hibiscus.service;

import com.google.gson.Gson;
import com.poit.hibiscus.api.client.model.Currency;
import com.poit.hibiscus.api.client.operation.CurrencyOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class AbstractQuotesService {
    private final CurrencyOperation currencyOperation;

    protected String getQuotesJSON() throws InterruptedException {
        var currency = currencyOperation.activate();

        return new Gson().toJson(currency.block().getQuotes());
    }

    @FunctionalInterface
    protected interface Supplier<T, E> {
        E getId(T t);
    }
}
