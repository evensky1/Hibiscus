package com.poit.hibiscus.service.impl;

import com.poit.hibiscus.entity.CardAccount;
import com.poit.hibiscus.repository.AccountRepository;
import com.poit.hibiscus.service.AccountService;

import java.util.Collections;
import java.util.Currency;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public CardAccount createAccount(CardAccount cardAccount, Long userId) {

        long accountNum = (long) (1000_0000_0000_0000L + Math.random() * (Long.MAX_VALUE / 1000L));
        int randomBalanceNum = (int) (1000 + Math.random() * 9999);

        String iban = "BY" + "24" + "1234" + randomBalanceNum + accountNum;

        var newCardAccount = CardAccount.builder()
                .currencyType(cardAccount.getCurrencyType())
                .money(BigDecimal.ZERO)
                .number(String.valueOf(accountNum))
                .iban(iban)
                .build();

        return accountRepository.save(newCardAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public CardAccount findByIban(String iban) {
        return accountRepository.findByIban(iban).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CardAccount> getAccountsByUserId(Long id) {
        return Collections.singletonList(new CardAccount());
    }

    @Override
    public List<CardAccount> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public CardAccount addMoney(Long id, BigDecimal amount) {
        accountRepository.updateMoney(id, amount);

        return accountRepository.getById(id);
    }
    @Override
    public Long findCardIdByCardNumber(String number) {
        return accountRepository.findAccountIdByAccountNumber(number);
    }

    @Override
    public Currency findCurrencyById(Long id) {
        return accountRepository.findToAccountCurrencyById(id);
    }
}
