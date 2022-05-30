package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface CardTransactionRepository extends JpaRepository<Transactions.CardTransaction, UUID> {
    @Procedure(procedureName = "made_account_transaction")
    boolean madeAccountTransaction(long toAccountId,
                                   long fromAccountId,
                                   BigDecimal amount,
                                   String currencies);

    @Query(value = """
                SELECT account_id FROM cards WHERE number = :number
                    """, nativeQuery = true)
    Long findAccountIdByNumber(@Param("number") String number);

    @Query(value = """
                SELECT account_id FROM cards WHERE id = :id
                """, nativeQuery = true)
    Long findAccountIdById(@Param("id") Long id);
}
