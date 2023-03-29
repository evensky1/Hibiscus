package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface CardTransactionLoggingRepository extends JpaRepository<CardTransaction, UUID> {

    @Transactional
    @Procedure(procedureName = "log_card_transaction")
    boolean insert(Long fromId,
                   Long toId,
                   BigDecimal amount);
}
