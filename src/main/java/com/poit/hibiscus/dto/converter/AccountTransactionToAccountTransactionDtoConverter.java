package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.dto.AccountTransactionDto;
import com.poit.hibiscus.entity.Transactions;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTransactionToAccountTransactionDtoConverter
        implements Converter<Transactions.AccountTransaction, AccountTransactionDto> {
    private final ModelMapper modelMapper;

    @Override
    public AccountTransactionDto convert(Transactions.AccountTransaction source) {
        return modelMapper.map(source, AccountTransactionDto.class);
    }
}
