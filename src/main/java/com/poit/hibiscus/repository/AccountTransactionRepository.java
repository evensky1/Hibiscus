package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.CardAccount;
import com.poit.hibiscus.entity.Transactions;
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

    List<Transactions.AccountTransaction> findAllByFromAccount(CardAccount fromAccount);

    List<Transactions.AccountTransaction> findAllByToAccount(CardAccount toAccount);
}
