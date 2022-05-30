package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.api.domain.client.operation.CurrencyOperation;

import com.poit.hibiscus.config.Transaction;

import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.AccountTransactionRepository;
import com.poit.hibiscus.service.AbstractQuotesService;
import com.poit.hibiscus.service.TransactionsService;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountTransactionServiceImpl extends AbstractQuotesService implements TransactionsService.AccountTransactionService {
    private final AccountTransactionRepository accountTransactionRepository;

    public AccountTransactionServiceImpl(CurrencyOperation currencyOperation,
                                         AccountTransactionRepository accountTransactionRepository) {
        super(currencyOperation);
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    @HandleError
    @Transaction(type = TransactionType.ACCOUNT_TRANSFER)
    public void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) {
        Supplier<String, Long> supplier = accountTransactionRepository::findAccountTransactionIdByNumber;

        try {
            accountTransactionRepository.madeAccountTransaction(
                    supplier.getId(toAccountNumber), fromAccountId,
                    amount, getQuotesJSON());
        } catch (JpaSystemException | InterruptedException jse) {
            throw new TransactionDeniedException("Transaction denied");
        }
    }
}
