package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.dto.AccountTransactionDto;
import com.poit.hibiscus.entity.AccountTransaction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTransactionDtoToAccountTransactionConverter
        implements Converter<AccountTransactionDto, AccountTransaction> {
    private final ModelMapper modelMapper;

    @Override
    public AccountTransaction convert(AccountTransactionDto source) {
        return modelMapper.map(source, AccountTransaction.class);
    }
}
