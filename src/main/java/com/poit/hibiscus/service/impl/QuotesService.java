package com.poit.hibiscus.service.impl;

import com.google.gson.Gson;
import com.poit.hibiscus.api.client.operation.CurrencyOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotesService {
    private final CurrencyOperation currencyOperation;

    protected String getQuotesJSON() throws InterruptedException {
        var currency = currencyOperation.activate();

        return new Gson().toJson(currency.block().getQuotes());
    }
}
