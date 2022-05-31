package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.api.domain.client.operation.CurrencyOperation;
import com.poit.hibiscus.config.Transaction;
import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.entity.Transactions.CardTransaction;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.CardTransactionRepository;
import com.poit.hibiscus.repository.model.CardTransactionView;
import com.poit.hibiscus.service.AbstractQuotesService;
import com.poit.hibiscus.service.CardService;
import com.poit.hibiscus.service.TransactionsService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardTransactionServiceImpl extends AbstractQuotesService implements
    TransactionsService.CardTransactionService {

    private final CardTransactionRepository cardTransactionRepository;
    private final CardService cardService;

    public CardTransactionServiceImpl(CurrencyOperation currencyOperation,
                                      CardTransactionRepository cardTransactionRepository,
                                      CardService cardService) {
        super(currencyOperation);
        this.cardTransactionRepository = cardTransactionRepository;
        this.cardService = cardService;
    }

    @Override
    @HandleError
    @Transaction(type = TransactionType.CARD_TRANSFER)
    public void insert(Long fromCardId, String toCardNumber, BigDecimal amount) {
        Supplier<String, Long> toAccountSupplier = cardTransactionRepository::findAccountIdByNumber;
        Supplier<Long, Long> fromAccountSupplier = cardTransactionRepository::findAccountIdById;

        try {
            cardTransactionRepository.madeAccountTransaction(
                toAccountSupplier.getId(toCardNumber),
                fromAccountSupplier.getId(fromCardId),
                amount, getQuotesJSON());
        } catch (JpaSystemException | InterruptedException jse) {
            throw new TransactionDeniedException("Transaction denied");
        }
    }

    @Override
    public List<CardTransactionView> findUserAttachedTransactions(Long userId) {
        var cards = cardService.getUserAttachedCards(userId);

        List<CardTransaction> transactions = new ArrayList<>();

        cards.forEach(c -> {
                transactions.addAll(cardTransactionRepository.findAllByFromCard(c));
                transactions.addAll(cardTransactionRepository.findAllByToCard(c));
            }
        );

        return transactions.stream()
                .map(t -> new CardTransactionView(t.getFromCard().getNumber(),
                                                  t.getToCard().getNumber(),
                                                  t.getAmount(),
                                                  t.getCurrencyType(),
                                                  t.getBeingAt()))
                .toList();
    }
}
