package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.dto.AccountTransactionDto;
import com.poit.hibiscus.entity.AccountTransaction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
@RequiredArgsConstructor
public class AccountTransactionToAccountTransactionDtoConverter
        implements Converter<AccountTransaction, AccountTransactionDto> {
    private final ModelMapper modelMapper;

    @Override
    public AccountTransactionDto convert(AccountTransaction source) {
        return modelMapper.map(source, AccountTransactionDto.class);
    }
}
