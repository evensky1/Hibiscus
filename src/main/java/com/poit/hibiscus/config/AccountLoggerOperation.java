package com.poit.hibiscus.config;

import com.poit.hibiscus.entity.Card;
import com.poit.hibiscus.entity.CardAccount;
import com.poit.hibiscus.entity.Transactions;
import com.poit.hibiscus.repository.AccountRepository;
import com.poit.hibiscus.repository.AccountTransactionLoggingRepository;
import com.poit.hibiscus.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
