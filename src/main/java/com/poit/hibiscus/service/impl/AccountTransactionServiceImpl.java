package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.config.Transaction;

import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.entity.AccountTransaction;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.AccountTransactionRepository;
import com.poit.hibiscus.repository.model.AccountTransactionView;
import com.poit.hibiscus.service.AccountService;
import com.poit.hibiscus.service.AccountTransactionService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountService accountService;
    private final QuotesService quotesService;

    @Override
    @HandleError
    @Transaction(type = TransactionType.ACCOUNT_TRANSFER)
    public void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) {
        var toAccountId =
            accountTransactionRepository.findAccountTransactionIdByNumber(toAccountNumber);

        try {
            accountTransactionRepository.madeAccountTransaction(
                toAccountId, fromAccountId, amount, quotesService.getQuotesJSON());
        } catch (JpaSystemException | InterruptedException | NullPointerException jse) {
            throw new TransactionDeniedException("Transaction denied");
        }
    }

    @Override
    public List<AccountTransactionView> findUserAttachedTransactions(Long userId) {
        var accounts = accountService.getAccountsByUserId(userId);

        List<AccountTransaction> transactions = new ArrayList<>();

        accounts.forEach(ac -> {
            var accountTransactions = accountTransactionRepository
                .findAllByFromAccountOrToAccount(ac, ac);
            transactions.addAll(accountTransactions);
        });

        return transactions.stream()
                .map(t -> new AccountTransactionView(t.getFromAccount().getNumber(),
                                                     t.getToAccount().getNumber(),
                                                     t.getAmount(),
                                                     t.getCurrencyType(),
                                                     t.getBeingAt()))
                .toList();
    }
}
