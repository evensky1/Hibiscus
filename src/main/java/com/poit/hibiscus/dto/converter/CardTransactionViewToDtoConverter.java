package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.dto.CardTransactionViewDto;
import com.poit.hibiscus.repository.model.CardTransactionView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardTransactionViewToDtoConverter
    implements Converter<CardTransactionView, CardTransactionViewDto> {

    private final ModelMapper modelMapper;

    @Override
    public CardTransactionViewDto convert(CardTransactionView source) {
        return modelMapper.map(source, CardTransactionViewDto.class);
    }
}
