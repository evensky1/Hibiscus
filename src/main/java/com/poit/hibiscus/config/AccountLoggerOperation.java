package com.poit.hibiscus.config;

import com.poit.hibiscus.repository.AccountTransactionLoggingRepository;
import com.poit.hibiscus.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AccountLoggerOperation extends AbstractLoggerFactoryAspect.AbstractLoggerOperation {
    private final AccountService accountService;
    private final AccountTransactionLoggingRepository accountTransactionLoggingRepository;

    @Override
    public void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) {
        var toAccountId = accountService.findCardIdByCardNumber(toAccountNumber);
        accountTransactionLoggingRepository.insert(fromAccountId, toAccountId, amount);
    }
}
