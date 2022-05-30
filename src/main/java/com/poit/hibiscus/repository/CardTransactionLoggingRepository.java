package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface CardTransactionLoggingRepository extends JpaRepository<Transactions.CardTransaction, UUID> {

    @Modifying
    @Transactional
    @Query(value = """
                INSERT INTO card_transaction(to_card_id, from_card_id, amount, currency)\040
                VALUES (:toCardId, :fromCardId, :amount, 'EUR')
                """, nativeQuery = true)
    void insert(@Param("fromCardId") Long fromId,
                @Param("toCardId") Long toId,
                @Param("amount") BigDecimal amount);
}
