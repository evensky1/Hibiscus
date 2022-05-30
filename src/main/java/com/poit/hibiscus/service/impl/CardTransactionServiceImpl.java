package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.api.domain.client.operation.CurrencyOperation;
import com.poit.hibiscus.config.Transaction;
import com.poit.hibiscus.config.TransactionType;
import com.poit.hibiscus.entity.Transactions;
import com.poit.hibiscus.error.factory.configuration.HandleError;
import com.poit.hibiscus.error.factory.model.TransactionDeniedException;
import com.poit.hibiscus.repository.CardTransactionRepository;
import com.poit.hibiscus.service.AbstractQuotesService;
import com.poit.hibiscus.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardTransactionServiceImpl extends AbstractQuotesService implements TransactionsService.CardTransactionService {
    private final CardTransactionRepository cardTransactionRepository;

    public CardTransactionServiceImpl(CurrencyOperation currencyOperation,
                                      CardTransactionRepository cardTransactionRepository) {
        super(currencyOperation);
        this.cardTransactionRepository = cardTransactionRepository;
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
}
