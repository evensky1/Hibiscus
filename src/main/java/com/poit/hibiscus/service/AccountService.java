package com.poit.hibiscus.service;

import com.poit.hibiscus.entity.Card;
import com.poit.hibiscus.entity.CardAccount;
import java.math.BigDecimal;

import java.util.Currency;
import java.util.List;

public interface AccountService {
    CardAccount createAccount(CardAccount cardAccount, Long userId);
    void deleteAccount(Long id);
    CardAccount findByIban(String iban);

    List<CardAccount> getAccountsByUserId(Long id);
    List<CardAccount> getAll();

    CardAccount addMoney(Long id, BigDecimal amount);

    Long findCardIdByCardNumber(String number);

    Currency findCurrencyById(Long id);
}
