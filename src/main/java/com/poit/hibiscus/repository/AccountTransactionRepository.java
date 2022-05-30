package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import com.poit.hibiscus.repository.model.AccountTransactionView;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountTransactionRepository extends JpaRepository<Transactions.AccountTransaction, UUID> {

    @Procedure(procedureName = "made_account_transaction")
    boolean madeAccountTransaction(long toAccountId,
                                   long fromAccountId,
                                   BigDecimal amount,
                                   String currencies);

    @Query(value = """
                SELECT id FROM card_accounts WHERE number = :number
                    """, nativeQuery = true)
    Long findAccountTransactionIdByNumber(@Param("number") String number);

    @Query(value = """
                SELECT src_ac.number, dest_ac.number, amount, currency, being_at FROM account_transaction
                    JOIN card_accounts src_ac ON src_ac.id = account_transaction.from_account_id
                    JOIN card_accounts dest_ac ON dest_ac.id = account_transaction.to_account_id
                WHERE to_account_id = :accountId OR from_account_id = :accountID;
        """, nativeQuery = true)
    List<AccountTransactionView> findAllByAccountId(@Param("accountId") Long accountId);
}
