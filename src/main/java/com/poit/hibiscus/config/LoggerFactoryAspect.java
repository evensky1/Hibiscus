package com.poit.hibiscus.config;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggerFactoryAspect extends AbstractLoggerFactoryAspect {
    private final CardLoggerOperation cardLoggerOperation;
    private final AccountLoggerOperation accountLoggerOperation;

    @AfterReturning(
            pointcut = TRANSACTION)
    public void transactionResolver(JoinPoint joinPoint, Long fromId, String toNumber, BigDecimal amount) throws NoSuchMethodException {
        var type = retrieveType(
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName());

        switch (type) {
            case ACCOUNT_TRANSFER -> accountTransactionResolver(fromId, toNumber, amount);
            case CARD_TRANSFER -> cardTransactionResolver(fromId, toNumber, amount);
        }
    }

    @Override
    protected void accountTransactionResolver(Long fromAccountId, String toAccountNumber, BigDecimal amount) {
        accountLoggerOperation.insert(fromAccountId, toAccountNumber, amount);
    }

    @Override
    protected void cardTransactionResolver(Long fromCardId, String toCardNumber, BigDecimal amount) {
        cardLoggerOperation.insert(fromCardId, toCardNumber, amount);
    }
}
