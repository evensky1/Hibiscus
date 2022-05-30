package com.poit.hibiscus.config;

import java.math.BigDecimal;
import java.util.Arrays;

public abstract class AbstractLoggerFactoryAspect {

    public static final String TRANSACTION = "@annotation(com.poit.hibiscus.config.Transaction) && args(fromId, toNumber, amount)";

    protected abstract void accountTransactionResolver(Long fromAccountId, String toAccountNumber, BigDecimal amount);

    protected abstract void cardTransactionResolver(Long fromCardId, String toCardNumber, BigDecimal amount);

    protected TransactionType retrieveType(Class<?> clazz, String methodName) throws NoSuchMethodException {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method
                        .getName()
                        .contains(methodName))
                .findFirst()
                .map(method -> method
                        .getAnnotation(Transaction.class)
                        .type())
                .orElseThrow(RuntimeException::new);
    }

    public abstract static class AbstractLoggerOperation {
        public abstract void insert(Long fromAccountId, String toAccountNumber, BigDecimal amount);
    }
}
