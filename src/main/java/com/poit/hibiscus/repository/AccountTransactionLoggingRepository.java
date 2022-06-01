package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountTransactionLoggingRepository extends JpaRepository<Transactions.AccountTransaction, UUID> {

    @Transactional
    @Procedure(name = "log_account_transaction")
    void insert(Long fromId,
                Long toId,
                BigDecimal amount);
}
