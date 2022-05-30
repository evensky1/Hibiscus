package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import com.poit.hibiscus.repository.model.CardTransactionView;
import java.util.List;
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

    @Query(value = """
                SELECT src_c.number, dest_c.number, amount, currency, being_at FROM card_transaction
                    JOIN cards src_c ON src_c.id = card_transaction.from_card_id
                    JOIN cards dest_c ON dest_c.id = card_transaction.to_card_id
                WHERE to_card_id = :cardId OR from_card_id = :cardId;
            """, nativeQuery = true)
    List<CardTransactionView> findAllByCardId(@Param("cardId") Long cardId);
}
