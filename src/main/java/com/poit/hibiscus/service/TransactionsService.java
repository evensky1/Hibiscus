package com.poit.hibiscus.service;

import com.poit.hibiscus.repository.model.AccountTransactionView;
import com.poit.hibiscus.repository.model.CardTransactionView;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionsService {

    interface AccountTransactionService {
        void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) throws InterruptedException;
        List<AccountTransactionView> findUserAttachedTransactions(Long userId);
    }

    interface CardTransactionService {
        void insert(Long fromCardId, String toCardNumber, BigDecimal amount);
        List<CardTransactionView> findUserAttachedTransactions(Long userId);
    }
}
