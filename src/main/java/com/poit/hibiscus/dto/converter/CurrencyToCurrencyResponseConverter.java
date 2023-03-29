package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.api.client.dto.CurrencyResponse;
import com.poit.hibiscus.api.client.model.Currency;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyToCurrencyResponseConverter implements Converter<Currency, CurrencyResponse> {
    private final ModelMapper modelMapper;

    @Override
    public CurrencyResponse convert(Currency source) {
        return modelMapper.map(source, CurrencyResponse.class);
    }
}
