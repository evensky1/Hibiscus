package com.poit.hibiscus.config;

import com.poit.hibiscus.repository.CardTransactionLoggingRepository;
import com.poit.hibiscus.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class CardLoggerOperation extends AbstractLoggerFactoryAspect.AbstractLoggerOperation {
    private final CardService cardService;
    private final CardTransactionLoggingRepository cardTransactionLoggingRepository;

    @Override
    public void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount) {
        var toCardId = cardService.findCardIdByNumber(toAccountNumber);

        cardTransactionLoggingRepository.insert(fromAccountId, toCardId, amount);
    }
}