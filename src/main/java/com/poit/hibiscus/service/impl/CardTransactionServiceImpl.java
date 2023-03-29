package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.config.Transaction;
import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.entity.Transactions.CardTransaction;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.CardTransactionRepository;
import com.poit.hibiscus.repository.model.CardTransactionView;
import com.poit.hibiscus.service.CardService;
import com.poit.hibiscus.service.CardTransactionService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CardTransactionServiceImpl implements CardTransactionService {

    private final CardTransactionRepository cardTransactionRepository;
    private final CardService cardService;
    private final QuotesService quotesService;

    @Override
    @HandleError
    @Transaction(type = TransactionType.CARD_TRANSFER)
    public void insert(Long fromCardId, String toCardNumber, BigDecimal amount) {
        var toAccountId = cardTransactionRepository.findAccountIdByNumber(toCardNumber);
        var fromAccountId = cardTransactionRepository.findAccountIdById(fromCardId);

        try {
            cardTransactionRepository.madeAccountTransaction(
                toAccountId, fromAccountId, amount, quotesService.getQuotesJSON());

        } catch (JpaSystemException | InterruptedException | NullPointerException jseException) {
            throw new TransactionDeniedException("Transaction denied");
        }
    }

    @Override
    public List<CardTransactionView> findUserAttachedTransactions(Long userId) {
        var cards = cardService.getUserAttachedCards(userId);

        List<CardTransaction> transactions = new ArrayList<>();

        cards.forEach(c -> {
            var cardTransactions = cardTransactionRepository
                .findAllByFromCardOrToCard(c, c);
            transactions.addAll(cardTransactions);
        });

        return transactions.stream()
            .map(t -> new CardTransactionView(t.getFromCard().getNumber(),
                                              t.getToCard().getNumber(),
                                              t.getAmount(),
                                              t.getCurrencyType(),
                                              t.getBeingAt()))
            .toList();
    }
}
