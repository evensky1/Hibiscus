package com.poit.hibiscus.service;

import com.poit.hibiscus.entity.Transactions;

import java.math.BigDecimal;

public interface TransactionsService {

    interface AccountTransactionService {
        void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) throws InterruptedException;
    }

    interface CardTransactionService {
        void insert(Long fromCardId, String toCardNumber, BigDecimal amount);
    }
}
