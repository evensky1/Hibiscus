package com.poit.hibiscus.service;

import com.poit.hibiscus.repository.model.CardTransactionView;
import java.math.BigDecimal;
import java.util.List;

public interface CardTransactionService {

    void insert(Long fromCardId, String toCardNumber, BigDecimal amount);

    List<CardTransactionView> findUserAttachedTransactions(Long userId);
}
