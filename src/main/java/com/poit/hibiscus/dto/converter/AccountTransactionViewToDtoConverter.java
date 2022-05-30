package com.poit.hibiscus.dto.converter;

import com.poit.hibiscus.dto.TransactionsViewDto.AccountTransactionViewDto;
import com.poit.hibiscus.repository.model.AccountTransactionView;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTransactionViewToDtoConverter
    implements Converter<AccountTransactionView, AccountTransactionViewDto> {

    private final ModelMapper modelMapper;

    @Override
    public AccountTransactionViewDto convert(AccountTransactionView source) {
        return modelMapper.map(source, AccountTransactionViewDto.class);
    }
}
