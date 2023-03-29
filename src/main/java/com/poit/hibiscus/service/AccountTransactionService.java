package com.poit.hibiscus.service;

import com.poit.hibiscus.repository.model.AccountTransactionView;
import java.math.BigDecimal;
import java.util.List;

public interface AccountTransactionService {

    void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount)
        throws InterruptedException;

    List<AccountTransactionView> findUserAttachedTransactions(Long userId);
}
