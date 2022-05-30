package com.poit.hibiscus.repository;

import com.poit.hibiscus.entity.CardAccount;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<CardAccount, Long> {
    Optional<CardAccount> findByIban(String iban);

    @Modifying
    @Transactional
    @Query("UPDATE CardAccount account SET account.money = account.money + :amount WHERE account.id = :id")
    void updateMoney(@Param("id") Long id, @Param("amount") BigDecimal amount);

    @Query(value = """
                SELECT id FROM card_accounts WHERE number = :number
                """, nativeQuery = true)
    Long findAccountIdByAccountNumber(@Param("number") String number);

    @Query(value = """
                SELECT currency FROM card_accounts WHERE id = :id
                    """, nativeQuery = true)
    Currency findToAccountCurrencyById(@Param("id") Long id);
}
