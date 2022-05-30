package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountTransactionLoggingRepository extends JpaRepository<Transactions.AccountTransaction, UUID> {

    @Modifying
    @Transactional
    @Query(value = """
                INSERT INTO account_transaction(to_account_id, from_account_id, amount, currency)\040
                VALUES (:toAccountId, :fromAccountId, :amount, 'EUR')
                """, nativeQuery = true)
    void insert(@Param("fromAccountId") Long fromId,
                @Param("toAccountId") Long toId,
                @Param("amount") BigDecimal amount);
}
