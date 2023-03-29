package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.api.domain.client.operation.CurrencyOperation;

import com.poit.hibiscus.config.Transaction;

import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.entity.Transactions.AccountTransaction;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.AccountTransactionRepository;
import com.poit.hibiscus.repository.model.AccountTransactionView;
import com.poit.hibiscus.service.AbstractQuotesService;
import com.poit.hibiscus.service.AccountService;
import com.poit.hibiscus.service.AccountTransactionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountTransactionServiceImpl
    extends AbstractQuotesService
    implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountService accountService;

    public AccountTransactionServiceImpl(CurrencyOperation currencyOperation,
        AccountTransactionRepository accountTransactionRepository,
        AccountService accountService) {
        super(currencyOperation);
        this.accountTransactionRepository = accountTransactionRepository;
        this.accountService = accountService;
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
        } catch (JpaSystemException | InterruptedException | NullPointerException jse) {
            throw new TransactionDeniedException("Transaction denied");
        }
    }

    @Override
    public List<AccountTransactionView> findUserAttachedTransactions(Long userId) {
        var accounts = accountService.getAccountsByUserId(userId);

        List<AccountTransaction> transactions = new ArrayList<>();

        accounts.forEach(ac ->
            transactions.addAll(accountTransactionRepository.findAllByFromAccountOrToAccount(ac, ac))
        );

        return transactions.stream()
                .map(t -> new AccountTransactionView(t.getFromAccount().getNumber(),
                                                     t.getToAccount().getNumber(),
                                                     t.getAmount(),
                                                     t.getCurrencyType(),
                                                     t.getBeingAt()))
                .toList();
    }
}
