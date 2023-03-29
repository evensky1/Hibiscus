package com.poit.hibiscus.controller;

import com.poit.hibiscus.dto.AccountDto;
import com.poit.hibiscus.entity.CardAccount;
import com.poit.hibiscus.service.AccountService;
import com.poit.hibiscus.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ConversionService conversionService;
    private final UserService userService;

    @PostMapping("new")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto createAccount(
        @RequestBody AccountDto accountDto,
        @AuthenticationPrincipal UserDetails userDetails) {

        var currentUser = userService.findUserByEmail(userDetails.getUsername());
        var cardAccount = conversionService.convert(accountDto, CardAccount.class);
        var newAccount = accountService.createAccount(cardAccount, currentUser.getId());

        return conversionService.convert(newAccount, AccountDto.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAccounts() {
        return accountService.getAll().stream()
                .map(a -> conversionService.convert(a, AccountDto.class))
                .toList();
    }

    @GetMapping("user-attached")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountDto> getAttachedAccounts(
        @AuthenticationPrincipal UserDetails userDetails) {
        var accounts = accountService.getAccountsByUserId(
                userService.findUserByEmail(userDetails.getUsername()).getId());

        return accounts.stream()
                .map(a -> conversionService.convert(a, AccountDto.class))
                .toList();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable("id") Long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("money/{id}/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto addMoney(
        @PathVariable("id") Long id,
        @PathVariable("amount") BigDecimal amount
    ) {
        var currentAccount = accountService.addMoney(id, amount);

        return conversionService.convert(currentAccount, AccountDto.class);
    }
}
